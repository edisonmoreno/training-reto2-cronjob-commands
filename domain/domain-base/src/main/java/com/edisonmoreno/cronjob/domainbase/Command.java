package com.edisonmoreno.cronjob.domainbase;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class Command {
    private String type;
    private Instant instant;
}
