package com.edisonmoreno.cronjob.mongo.CronJob;

import com.edisonmoreno.cronjob.model.Execution;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@Document
public class CronJobDocument {
    @Id
    private String id;
    private String name;
    private String url;
    private String cronExpression;
    private String timeout;
    private String retry;
    private String email;
    private Integer totalSuccessful;
    private Integer totalFailed;
    private Set<Execution> executions;
}

