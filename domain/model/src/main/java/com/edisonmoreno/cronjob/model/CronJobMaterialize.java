package com.edisonmoreno.cronjob.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CronJobMaterialize {
    private String cronJobId;
    private String name;
    private String url;
    private String cronExpression;
    private String timeout;
    private String retry;
    private String email;
    private Set<ExecutionData> executions;
}
