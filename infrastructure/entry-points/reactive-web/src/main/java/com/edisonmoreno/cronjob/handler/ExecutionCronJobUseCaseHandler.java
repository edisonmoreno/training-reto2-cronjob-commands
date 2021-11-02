package com.edisonmoreno.cronjob.handler;

import com.edisonmoreno.cronjob.model.base.Command;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.command.ExecutionCronJobCommand;
import com.edisonmoreno.cronjob.usecase.ExecutionCronJobUseCase;
import com.edisonmoreno.cronjob.usecase.base.UseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ExecutionCronJobUseCaseHandler extends AbstractUseCaseHandler implements UseCaseHandler {
    private final ExecutionCronJobUseCase executionCronJobUseCase;

    public ExecutionCronJobUseCaseHandler(ExecutionCronJobUseCase executionCronJobUseCase) {
        this.executionCronJobUseCase = executionCronJobUseCase;
    }

    @Override
    @EventListener({ExecutionCronJobCommand.class})
    public void consume(Command command) {
        log.info("ExecutionCronJobUseCaseHandler.consume: {}", command);
        ExecutionCronJobCommand executionCommand = (ExecutionCronJobCommand) command;
        List<DomainEvent> events = executionCronJobUseCase.apply(command);
        persistentEvent("CronJob", executionCommand.getCronJobId(), events);

    }
}
