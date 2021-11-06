package com.edisonmoreno.cronjob.usecase;

import com.edisonmoreno.cronjob.model.Execution;
import com.edisonmoreno.cronjob.model.aggregate.CronJobAggregate;
import com.edisonmoreno.cronjob.model.base.Command;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.command.ExecutionCronJobCommand;
import com.edisonmoreno.cronjob.model.repository.EventStoreRepository;
import com.edisonmoreno.cronjob.usecase.base.UseCase;

import java.util.List;
import java.util.logging.Logger;

public class ExecutionCronJobUseCase extends UseCase {
    final Logger logger = Logger.getLogger("usecase.CreateCronJobUseCase");
    private final EventStoreRepository repository;

    public ExecutionCronJobUseCase(EventStoreRepository eventStoreRepository) {
        this.repository = eventStoreRepository;
    }

    @Override
    public List<DomainEvent> apply(Command command) {
        logger.info(String.format("ExecutionCronJobUseCase.apply(%s)", command));
        ExecutionCronJobCommand executionCommand = (ExecutionCronJobCommand) command;
        CronJobAggregate cronJob = CronJobAggregate
                .from(executionCommand.getCronJobId(), repository.getEventsBy("CronJob", executionCommand.getCronJobId()));
        cronJob.addExecution(Execution.builder()
                .duration(executionCommand.getDuration())
                .state(executionCommand.getState())
                .date(executionCommand.getDate())
                .httpCode(executionCommand.getHttpCode())
                .build());
        return cronJob.getUncommittedChanges();
    }
}