package com.edisonmoreno.cronjob.materialize;

import com.edisonmoreno.cronjob.model.CronJobMaterialize;
import com.edisonmoreno.cronjob.model.Execution;
import com.edisonmoreno.cronjob.model.event.CronJobCreated;
import com.edisonmoreno.cronjob.model.event.ExecutionCreated;
import com.edisonmoreno.cronjob.usecase.materialize.MaterializeCronJobUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class MaterializeCronJobUseCaseHandler {
    private final MaterializeCronJobUseCase materializeCronJobUseCase;

    public MaterializeCronJobUseCaseHandler(MaterializeCronJobUseCase materializeCronJobUseCase) {
        this.materializeCronJobUseCase = materializeCronJobUseCase;
    }

    @EventListener({CronJobCreated.class})
    public void consumeCronJobCreated(CronJobCreated event) {
        log.info("MaterializeCronJobUseCaseHandler.consumeCronJobCreated: {}", event);
        CronJobMaterialize materialize = CronJobMaterialize.builder()
                .cronJobId(event.getAggregateId())
                .name(event.getName())
                .url(event.getUrl())
                .cronExpression(event.getCronExpression())
                .timeout(event.getTimeout())
                .retry(event.getRetry())
                .email(event.getEmail())
                .build();
        materializeCronJobUseCase.save(materialize);
    }

    @EventListener({ExecutionCreated.class})
    public void consumeExecutionCreated(ExecutionCreated event) {
        log.info("MaterializeCronJobUseCaseHandler.consumeExecutionCreated: {}", event);
        Set<Execution> executions = new HashSet<>();
        executions.add(Execution.builder()
                .state(event.getState())
                .duration(event.getDuration())
                .date(event.getDate())
                .build());

        CronJobMaterialize materialize = CronJobMaterialize.builder()
                .cronJobId(event.getAggregateId())
                .executions(executions)
                .build();
        materializeCronJobUseCase.saveExecution(materialize);
    }
}
