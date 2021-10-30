package com.edisonmoreno.cronjob.model.aggregate;

import com.edisonmoreno.cronjob.model.CronJob;
import com.edisonmoreno.cronjob.model.ExecutionData;
import com.edisonmoreno.cronjob.model.base.EventChange;
import com.edisonmoreno.cronjob.model.event.CronJobCreated;
import com.edisonmoreno.cronjob.model.event.ExecutionCreated;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CronJobAggregate extends AggregateRoot implements EventChange {
    private CronJob cronJob;
    private Set<ExecutionData> executions;
    //ToDo: AggregateId, cronJobId, Instant (Command)
    //ToDo: addExecutions

    public CronJobAggregate(CronJob cronJob) {
        super(cronJob.getCronJobId());
        appendChange(new CronJobCreated(cronJob.getName(), cronJob.getUrl(), cronJob.getCronExpression(),
                cronJob.getTimeout(), cronJob.getRetry(), cronJob.getEmail()));
    }

    public void addExecutions() {
        executions.forEach(executionData -> {
            appendChange(new ExecutionCreated(executionData.getInstant(), executionData.getState(),
                    executionData.getDuration()));
        });
    }
}
