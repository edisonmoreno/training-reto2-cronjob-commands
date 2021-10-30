package com.edisonmoreno.cronjob.usecase;

import com.edisonmoreno.cronjob.model.CronJob;
import com.edisonmoreno.cronjob.model.aggregate.CronJobAggregate;
import com.edisonmoreno.cronjob.model.base.Command;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.command.CreateCronJobCommand;
import com.edisonmoreno.cronjob.usecase.base.UseCase;

import java.util.List;

public class CreateCronJobUseCase extends UseCase {

    @Override
    public List<DomainEvent> apply(Command command) {
        System.out.println("command.apply: " + command);
        CreateCronJobCommand createCommand = (CreateCronJobCommand) command;
        CronJobAggregate aggregate = new CronJobAggregate(CronJob.builder()
                .cronJobId(createCommand.getCronJobId())
                .name(createCommand.getName())
                .email(createCommand.getEmail())
                .retry(createCommand.getRetry())
                .timeout(createCommand.getTimeout())
                .cronExpression(createCommand.getCronExpression())
                .url(createCommand.getUrl())
                .build());

        return aggregate.getUncommittedChanges();
    }
}
