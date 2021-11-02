package com.edisonmoreno.cronjob.model.aggregate;

import java.time.Instant;

public class ExecutionAggregate {
    private final String id;
    private final String state;
    private final long duration;
    private final Instant date;

    public ExecutionAggregate(String id, String state, long duration, Instant date) {
        this.id = id;
        this.state = state;
        this.duration = duration;
        this.date = date;
    }

    public String id() {
        return id;
    }

    public String state() {
        return state;
    }

    public long duration() {
        return duration;
    }

    public Instant date() {
        return date;
    }
}
