package com.edisonmoreno.cronjob.handler;

import com.edisonmoreno.cronjob.model.base.Command;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.command.CreateCronJobCommand;
import com.edisonmoreno.cronjob.usecase.CreateCronJobUseCase;
import com.edisonmoreno.cronjob.usecase.base.UseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CreateCronJobUseCaseHandler extends AbstractUseCaseHandler implements UseCaseHandler {
    private final CreateCronJobUseCase createCronJobUseCase;

    public CreateCronJobUseCaseHandler(CreateCronJobUseCase createCronJobUseCase) {
        this.createCronJobUseCase = createCronJobUseCase;
    }

    @Override
    @EventListener({CreateCronJobCommand.class})
    public void consume(Command command) {
        log.info("CreateCronJobUseCaseHandler.consume: {}", command);
        CreateCronJobCommand createCommand = (CreateCronJobCommand) command;
        List<DomainEvent> events = createCronJobUseCase.apply(command);
        persistentEvent("CronJob", createCommand.getCronJobId(), events);

    }

}
