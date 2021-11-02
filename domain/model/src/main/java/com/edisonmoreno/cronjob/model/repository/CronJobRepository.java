package com.edisonmoreno.cronjob.model.repository;

import com.edisonmoreno.cronjob.model.CronJobMaterialize;

public interface CronJobRepository {
    void save(CronJobMaterialize cronJobMaterialize);

    void saveExecution(CronJobMaterialize cronJobMaterialize);
}
