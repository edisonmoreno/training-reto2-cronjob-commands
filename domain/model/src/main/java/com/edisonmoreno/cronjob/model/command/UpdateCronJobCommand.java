package com.edisonmoreno.cronjob.model.command;

import com.edisonmoreno.cronjob.model.ExecutionData;
import com.edisonmoreno.cronjob.model.base.Command;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateCronJobCommand extends Command {
    private String cronJobId;
    private Set<ExecutionData> executions;
}
