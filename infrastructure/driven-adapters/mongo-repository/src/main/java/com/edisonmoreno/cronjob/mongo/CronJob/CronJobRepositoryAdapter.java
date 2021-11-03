package com.edisonmoreno.cronjob.mongo.CronJob;

import com.edisonmoreno.cronjob.model.CronJob;
import com.edisonmoreno.cronjob.model.CronJobMaterialize;
import com.edisonmoreno.cronjob.model.Execution;
import com.edisonmoreno.cronjob.model.repository.CronJobRepository;
import com.edisonmoreno.cronjob.mongo.helper.AdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
public class CronJobRepositoryAdapter extends AdapterOperations<CronJob, CronJobDocument, String, CronJobMongoRepository> implements CronJobRepository {

    public CronJobRepositoryAdapter(CronJobMongoRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, CronJob.class));
    }

    @Override
    public void save(CronJobMaterialize cronJobMaterialize) {
        CronJobDocument document = CronJobDocument.builder()
                .id(cronJobMaterialize.getCronJobId())
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

    @Override
    @Transactional
    public void saveExecution(CronJobMaterialize cronJobMaterialize) {
        repository.findById(cronJobMaterialize.getCronJobId())
                .map(cronJobDocument -> {
                    Set<Execution> updateExecutions = new HashSet<>(Optional.ofNullable(cronJobDocument.getExecutions()).orElse(Collections.emptySet()));
                    updateExecutions.addAll(cronJobMaterialize.getExecutions());
                    return cronJobDocument.toBuilder()
                            .executions(updateExecutions)
                            .build();
                })
                .map(cronJobDocument -> cronJobDocument.toBuilder()
                        .totalSuccessful((int) cronJobDocument.getExecutions().stream().filter(execution -> execution.getState().equals("SUCCESS")).count())
                        .totalFailed((int) cronJobDocument.getExecutions().stream().filter(execution -> execution.getState().equals("FAILED")).count())
                        .build())
                .flatMap(repository::save)
                .doOnSuccess(cronJobDocument -> log.info("CronJobRepositoryAdapter.saveExecution: {}", cronJobDocument.toString()))
                .subscribe();
    }
}
