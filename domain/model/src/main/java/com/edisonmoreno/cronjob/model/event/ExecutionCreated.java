package com.edisonmoreno.cronjob.model.event;

import com.edisonmoreno.cronjob.model.base.DomainEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecutionCreated extends DomainEvent {
    private String state;
    private long duration;

    public ExecutionCreated(String state, long duration) {
        super("ms-commands.cronjob.execution");
        this.state = state;
        this.duration = duration;
    }
}


