package com.edisonmoreno.cronjob.mongo.CronJob;

import com.edisonmoreno.cronjob.model.CronJob;
import com.edisonmoreno.cronjob.model.CronJobMaterialize;
import com.edisonmoreno.cronjob.model.repository.CronJobRepository;
import com.edisonmoreno.cronjob.mongo.helper.AdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CronJobRepositoryAdapter extends AdapterOperations<CronJob, CronJobDocument, String, CronJobMongoRepository> implements CronJobRepository {

    public CronJobRepositoryAdapter(CronJobMongoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CronJob.class));
    }

    @Override
    public void save(CronJobMaterialize cronJobMaterialize) {
        CronJobDocument document = CronJobDocument.builder()
                .cronJobId(cronJobMaterialize.getCronJobId())
                .name(cronJobMaterialize.getName())
                .url(cronJobMaterialize.getUrl())
                .cronExpression(cronJobMaterialize.getCronExpression())
                .timeout(cronJobMaterialize.getTimeout())
                .retry(cronJobMaterialize.getRetry())
                .email(cronJobMaterialize.getEmail())
                .build();

        repository.save(document)
                .doOnSuccess(cronJobDocument -> log.info("CronJobRepositoryAdapter.save: {}", cronJobDocument.toString()))
                .subscribe();
    }
}
