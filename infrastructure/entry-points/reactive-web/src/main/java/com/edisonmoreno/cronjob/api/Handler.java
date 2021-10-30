package com.edisonmoreno.cronjob.api;

import com.edisonmoreno.cronjob.infra.MessageService;
import com.edisonmoreno.cronjob.model.command.CreateCronJobCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {
    private final MessageService messageService;

    //private  final UseCase useCase;
    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().body("", String.class);
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().body("", String.class);
    }

    public Mono<ServerResponse> CronJobCreate(ServerRequest serverRequest) {
        Mono<CreateCronJobCommand> commandMono = serverRequest.bodyToMono(CreateCronJobCommand.class);
        return commandMono
                .doOnNext(messageService::send)
                .flatMap(createCronJobCommand -> ServerResponse
                        .ok()
                        .body(Mono.just(createCronJobCommand.getName()), String.class))
                .doOnError(error -> log.error("ERROR: {}", error.getMessage()))
                ;
    }
}

