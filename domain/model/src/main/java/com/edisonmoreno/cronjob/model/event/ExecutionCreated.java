package com.edisonmoreno.cronjob.model.event;

import com.edisonmoreno.cronjob.model.base.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExecutionCreated extends DomainEvent {
    private Instant instant;
    private String state;
    private long duration;

    public ExecutionCreated(Instant instant, String state, long duration) {
        super("ms-commands.cronjob.execution");
        this.instant = instant;
        this.state = state;
        this.duration = duration;
    }
}
