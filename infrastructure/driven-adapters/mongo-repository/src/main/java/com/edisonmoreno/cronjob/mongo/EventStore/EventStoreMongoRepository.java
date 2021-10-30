package com.edisonmoreno.cronjob.mongo.EventStore;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface EventStoreMongoRepository extends ReactiveMongoRepository<EventStoreDocument, String>, ReactiveQueryByExampleExecutor<EventStoreDocument> {
}
