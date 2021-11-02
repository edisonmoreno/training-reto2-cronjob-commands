package com.edisonmoreno.cronjob.handler;

import com.edisonmoreno.cronjob.common.EventSerializer;
import com.edisonmoreno.cronjob.infra.MessageService;
import com.edisonmoreno.cronjob.model.EventStore;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.repository.EventStoreRepository;
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
                    return new EventStore(domainEvent.getClass().getTypeName(), new Date(), eventBody);
                })
                .forEach(eventStore -> repository.saveEvent(aggregateName, aggregateRootId, eventStore));
        events.forEach(messageService::send);
    }
}