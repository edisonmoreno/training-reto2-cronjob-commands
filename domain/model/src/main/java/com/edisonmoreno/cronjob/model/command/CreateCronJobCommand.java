package com.edisonmoreno.cronjob.model.command;

import com.edisonmoreno.cronjob.model.base.Command;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCronJobCommand extends Command {
    private String cronJobId;
    private String name;
    private String url;
    private String cronExpression;
    private String timeout;
    private String retry;
    private String email;
}
