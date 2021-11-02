package com.edisonmoreno.cronjob.mongo.EventStore;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

public interface EventStoreMongoRepository extends ReactiveMongoRepository<EventStoreDocument, String>, ReactiveQueryByExampleExecutor<EventStoreDocument> {
    Flux<EventStoreDocument> findByAggregateId(String aggregateRootId);
}
