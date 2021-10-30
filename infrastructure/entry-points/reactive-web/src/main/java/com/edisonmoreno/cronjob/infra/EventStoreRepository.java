package com.edisonmoreno.cronjob.infra;

import com.edisonmoreno.cronjob.model.base.DomainEvent;

import java.util.List;



public interface EventStoreRepository {

    List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId);


    void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent);

}