package com.edisonmoreno.cronjob.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExecutionData {
    private Instant instant;
    private String state;
    private long duration;

}
