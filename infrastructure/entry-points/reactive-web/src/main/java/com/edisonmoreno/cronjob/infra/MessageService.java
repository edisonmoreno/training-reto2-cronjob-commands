package com.edisonmoreno.cronjob.infra;

import com.edisonmoreno.cronjob.helper.CommandSerializer;
import com.edisonmoreno.cronjob.model.base.Command;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


@Slf4j
@Component
public class MessageService {
    private static final String EXCHANGE = "executor";
    private static final String EXECUTOR_QUEUE = "executor.queue";
    private static final String EVENT_QUEUE = "event.queue";
    private Channel channel;
    private final ApplicationEventPublisher eventbus;

    public MessageService(ApplicationEventPublisher eventbus) throws IOException, TimeoutException {
        this.eventbus = eventbus;

        rabbitMQConnect();
        onApplicationStart();
    }

    public Connection rabbitMQConnect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //ToDo: AutoConfig YAML
        factory.setUsername("nevmcfbv");
        factory.setPassword("daYHl_79T6qtbusaAt78cSSs5jgspldE");
        factory.setVirtualHost("nevmcfbv");
        factory.setHost("jaguar.rmq.cloudamqp.com");
        factory.setPort(5672);
        return factory.newConnection();
    }

    public void onApplicationStart() throws IOException, TimeoutException {
        Connection connection = rabbitMQConnect();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC, true);

        //for command
        channel.queueDeclare(EXECUTOR_QUEUE, true, false, false, null);
        channel.queueBind(EXECUTOR_QUEUE, EXCHANGE, "executor-command");
        channel.basicConsume(EXECUTOR_QUEUE, true, setupReceivingForCommand());

        //for event
        channel.queueDeclare(EVENT_QUEUE, true, false, false, null);
        channel.queueBind(EVENT_QUEUE, EXCHANGE, "trigger-event");
        channel.basicConsume(EVENT_QUEUE, true, setupReceivingForEvent());
    }

    private Consumer setupReceivingForCommand() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                log.info("MessageService.setupReceivingForCommand(): {}", message);
                try {
                    Command command = CommandSerializer.instance()
                            .deserialize(message, Class.forName(properties.getContentType()));

                    eventbus.publishEvent(command);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Consumer setupReceivingForEvent() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                log.info("MessageService.setupReceivingForEvent(): {}", message);
                try {
                    DomainEvent event = EventSerializer.instance()
                            .deserialize(message, Class.forName(properties.getContentType()));

                    eventbus.publishEvent(event); // bus.publish(event.getType(), event);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void send(Command command) {
        log.info("MessageService.Send(command)");
        try {
            String message = CommandSerializer.instance().serialize(command);
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().contentType(command.getClass().getTypeName()).build();
            channel.basicPublish(EXCHANGE, "executor-command", props, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void send(DomainEvent event) {
        log.info("MessageService.Send(event)");
        try {
            String message = EventSerializer.instance().serialize(event);
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().contentType(event.getClass().getTypeName()).build();
            channel.basicPublish(EXCHANGE, "trigger-event", props, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
