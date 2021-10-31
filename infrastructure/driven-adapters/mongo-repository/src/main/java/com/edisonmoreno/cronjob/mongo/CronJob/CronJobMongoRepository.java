package com.edisonmoreno.cronjob.mongo.CronJob;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface CronJobMongoRepository extends ReactiveMongoRepository<CronJobDocument, String>, ReactiveQueryByExampleExecutor<CronJobDocument> {
}
