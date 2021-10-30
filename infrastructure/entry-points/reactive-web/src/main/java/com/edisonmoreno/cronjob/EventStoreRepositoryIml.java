package com.edisonmoreno.cronjob;

import com.edisonmoreno.cronjob.infra.EventStoreRepository;
import com.edisonmoreno.cronjob.infra.StoredEvent;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class EventStoreRepositoryIml implements EventStoreRepository {
    @Override
    public List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
        return Collections.emptyList();
    }

    @Override
    public void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent) {

    }
}
