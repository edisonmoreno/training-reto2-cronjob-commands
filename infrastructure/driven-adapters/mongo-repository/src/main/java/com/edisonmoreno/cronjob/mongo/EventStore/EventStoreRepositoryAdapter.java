package com.edisonmoreno.cronjob.mongo.EventStore;

import com.edisonmoreno.cronjob.common.EventSerializer;
import com.edisonmoreno.cronjob.model.EventStore;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.repository.EventStoreRepository;
import com.edisonmoreno.cronjob.mongo.helper.AdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class EventStoreRepositoryAdapter extends AdapterOperations<EventStore, EventStoreDocument, String, EventStoreMongoRepository> implements EventStoreRepository {
    public EventStoreRepositoryAdapter(EventStoreMongoRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, EventStore.class));
    }

    @Override
    public List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
        List<DomainEvent> events = new ArrayList<>();
        repository.findByAggregateId(aggregateRootId)
                .map(document -> {
                    String eventBody = document.getEventBody();
                    try {
                        return (DomainEvent) EventSerializer
                                .instance()
                                .deserialize(eventBody, Class.forName(document.getTypeName()));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toIterable()
                .forEach(events::add);
        return events;
    }

    @Override
    public void saveEvent(String aggregateName, String aggregateRootId, EventStore eventStore) {
        EventStoreDocument document = EventStoreDocument.builder()
                .id(UUID.randomUUID().toString())
                .aggregateId(aggregateRootId)
                .occurredOn(eventStore.getOccurredOn())
                .typeName(eventStore.getTypeName())
                .eventBody(eventStore.getEventBody())
                .build();

        //Todo: Separate in other database
        repository.save(document)
                .doOnSuccess(eventStoreDocument -> log.info("EventStoreRepositoryAdapter.saveEvent: {}", eventStoreDocument.toString()))
                .subscribe();
    }
}
