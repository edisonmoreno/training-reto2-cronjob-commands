package com.edisonmoreno.cronjob.model.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command {
    private String type;
}
