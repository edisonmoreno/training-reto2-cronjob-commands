package com.edisonmoreno.cronjob.model.event;

import com.edisonmoreno.cronjob.model.base.DomainEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CronJobCreated extends DomainEvent {
    private String name;
    private String url;
    private String cronExpression;
    private String timeout;
    private String retry;
    private String email;
    private Integer totalSuccessful;
    private Integer totalFailed;

    public CronJobCreated(String name, String url, String cronExpression, String timeout, String retry, String email, Integer totalSuccessful, Integer totalFailed) {
        super("ms-commands.event.cronjob.created");
        this.name = name;
        this.url = url;
        this.cronExpression = cronExpression;
        this.timeout = timeout;
        this.retry = retry;
        this.email = email;
        this.totalSuccessful = totalSuccessful;
        this.totalFailed = totalFailed;
    }
}
