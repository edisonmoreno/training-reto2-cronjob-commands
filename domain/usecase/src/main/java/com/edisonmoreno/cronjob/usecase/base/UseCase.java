package com.edisonmoreno.cronjob.usecase.base;

import com.edisonmoreno.cronjob.model.base.Command;
import com.edisonmoreno.cronjob.model.base.DomainEvent;

import java.util.List;
import java.util.function.Function;

public abstract class UseCase implements Function<Command, List<DomainEvent>> {

    @Override
    public List<DomainEvent> apply(Command command) {
        return this.apply(command);
    }
}
