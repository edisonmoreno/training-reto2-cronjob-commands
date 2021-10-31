package com.edisonmoreno.cronjob.materialize;

import com.edisonmoreno.cronjob.model.CronJobMaterialize;
import com.edisonmoreno.cronjob.model.event.CronJobCreated;
import com.edisonmoreno.cronjob.usecase.materialize.MaterializeCronJobUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MaterializeCronJobUseCaseHandler {
    private final MaterializeCronJobUseCase materializeCronJobUseCase;

    public MaterializeCronJobUseCaseHandler(MaterializeCronJobUseCase materializeCronJobUseCase) {
        this.materializeCronJobUseCase = materializeCronJobUseCase;
    }

    @EventListener({CronJobCreated.class})
    public void consumeCronJobCreated(CronJobCreated event) {
        log.info("handler.consumeCronJobCreated: {}", event);
        CronJobMaterialize materialize = CronJobMaterialize.builder()
                .cronJobId(event.getId())
                .name(event.getName())
                .url(event.getUrl())
                .cronExpression(event.getCronExpression())
                .timeout(event.getTimeout())
                .retry(event.getRetry())
                .email(event.getEmail())
                .build();
        materializeCronJobUseCase.save(materialize);
    }
}
