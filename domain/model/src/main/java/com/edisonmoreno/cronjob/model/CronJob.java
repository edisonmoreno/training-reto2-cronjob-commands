package com.edisonmoreno.cronjob.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CronJob {
    private String cronJobId;
    private String name;
    private String url;
    private String cronExpression;
    private String timeout;
    private String retry;
    private String email;
    private Integer totalSuccessful;
    private Integer totalFailed;
}
