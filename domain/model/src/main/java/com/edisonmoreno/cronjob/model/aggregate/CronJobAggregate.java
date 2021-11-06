package com.edisonmoreno.cronjob.model.aggregate;

import com.edisonmoreno.cronjob.model.CronJob;
import com.edisonmoreno.cronjob.model.Execution;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.base.EventChange;
import com.edisonmoreno.cronjob.model.event.CronJobCreated;
import com.edisonmoreno.cronjob.model.event.ExecutionCreated;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class CronJobAggregate extends AggregateRoot implements EventChange {
    private CronJob cronJob;
    private Map<String, ExecutionAggregate> executions;

    public CronJobAggregate(CronJob cronJob) {
        super(cronJob.getCronJobId());
        appendChange(new CronJobCreated(cronJob.getName(), cronJob.getUrl(), cronJob.getCronExpression(),
                cronJob.getTimeout(), cronJob.getRetry(), cronJob.getEmail())).apply();
    }

    private CronJobAggregate(String id) {
        super(id);
        subscribe(this);
        listener((CronJobCreated event) -> {
            this.setCronJob(CronJob.builder()
                    .cronJobId(event.getId())
                    .name(event.getName())
                    .url(event.getUrl())
                    .cronExpression(event.getCronExpression())
                    .timeout(event.getTimeout())
                    .retry(event.getRetry())
                    .email(event.getEmail())
                    .build());

            this.executions = new HashMap<>();
        });
        listener((ExecutionCreated event) -> {
            executions.put(event.getId(), new ExecutionAggregate(event.getId(), event.getState(), event.getDuration(), event.getDate(), event.getHttpCode()));
        });
    }

    public void addExecution(Execution execution) {
        appendChange(new ExecutionCreated(execution.getState(), execution.getDuration(), execution.getDate(), execution.getHttpCode())).apply();
    }

    public static CronJobAggregate from(String cronJobId, List<DomainEvent> events) {
        CronJobAggregate aggregate = new CronJobAggregate(cronJobId);
        events.forEach(aggregate::applyEvent);
        return aggregate;
    }
}
