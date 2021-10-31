package com.edisonmoreno.cronjob.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExecutionData {
    private String state;
    private long duration;

}
