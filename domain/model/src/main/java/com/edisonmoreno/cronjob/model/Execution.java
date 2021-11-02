package com.edisonmoreno.cronjob.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class Execution {
    private String state;
    private long duration;
    private Instant date;
}
