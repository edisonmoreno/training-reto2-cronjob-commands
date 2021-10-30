package com.edisonmoreno.cronjob.handler;

import com.edisonmoreno.cronjob.infra.EventSerializer;
import com.edisonmoreno.cronjob.infra.EventStoreRepository;
import com.edisonmoreno.cronjob.infra.MessageService;
import com.edisonmoreno.cronjob.infra.StoredEvent;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public abstract class AbstractUseCaseHandler {
    @Autowired
    private EventStoreRepository repository;
    @Autowired
    private MessageService messageService;

    public void persistentEvent(String aggregateName, String aggregateRootId, List<DomainEvent> events) {
        events.stream().map(domainEvent -> {
                    String eventBody = EventSerializer.instance().serialize(domainEvent);
                    return new StoredEvent(domainEvent.getClass().getTypeName(), new Date(), eventBody);
                })
                .forEach(storedEvent -> repository.saveEvent(aggregateName, aggregateRootId, storedEvent));
        events.forEach(messageService::send);
    }
}