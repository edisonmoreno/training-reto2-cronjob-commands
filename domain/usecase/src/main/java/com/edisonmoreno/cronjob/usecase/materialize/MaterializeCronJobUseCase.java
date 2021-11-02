package com.edisonmoreno.cronjob.usecase.materialize;

import com.edisonmoreno.cronjob.model.CronJobMaterialize;
import com.edisonmoreno.cronjob.model.repository.CronJobRepository;

import java.util.logging.Logger;

public class MaterializeCronJobUseCase {
    final Logger logger = Logger.getLogger("usecase.CreateCronJobUseCase");
    private final CronJobRepository repository;

    public MaterializeCronJobUseCase(CronJobRepository repository) {
        this.repository = repository;
    }

    public void save(CronJobMaterialize cronJobMaterialize) {
        logger.info(String.format("MaterializeCronJobUseCase.save(%s)", cronJobMaterialize));
        repository.save(cronJobMaterialize);
    }

    public void saveExecution(CronJobMaterialize cronJobMaterialize) {
        logger.info(String.format("MaterializeCronJobUseCase.saveExecution(%s)", cronJobMaterialize));
        repository.saveExecution(cronJobMaterialize);
    }
}
