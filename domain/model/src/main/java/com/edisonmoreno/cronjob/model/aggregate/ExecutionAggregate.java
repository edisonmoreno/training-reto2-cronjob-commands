package com.edisonmoreno.cronjob.model.aggregate;

import java.time.Instant;

public class ExecutionAggregate {
    private final String id;
    private final String state;
    private final long duration;
    private final Instant date;
    private final String httpCode;

    public ExecutionAggregate(String id, String state, long duration, Instant date, String httpCode) {
        this.id = id;
        this.state = state;
        this.duration = duration;
        this.date = date;
        this.httpCode = httpCode;
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

    public String httpCode() {
        return httpCode;
    }
}
