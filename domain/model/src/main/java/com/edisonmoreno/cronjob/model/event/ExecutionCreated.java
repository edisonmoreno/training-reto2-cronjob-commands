package com.edisonmoreno.cronjob.model.event;

import com.edisonmoreno.cronjob.model.base.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExecutionCreated extends DomainEvent {
    private String state;
    private long duration;
    private Instant date;

    public ExecutionCreated(String state, long duration, Instant date) {
        super("ms-commands.event.cronjob.execution");
        this.state = state;
        this.duration = duration;
        this.date = date;
    }
}


