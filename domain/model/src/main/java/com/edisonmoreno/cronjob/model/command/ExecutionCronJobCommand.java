package com.edisonmoreno.cronjob.model.command;

import com.edisonmoreno.cronjob.model.base.Command;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExecutionCronJobCommand extends Command {
    private String cronJobId;
    private String state;
    private long duration;
    private Instant date;
    private String httpCode;
}
