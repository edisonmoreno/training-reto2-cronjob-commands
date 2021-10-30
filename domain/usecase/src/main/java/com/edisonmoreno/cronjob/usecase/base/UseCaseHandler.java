package com.edisonmoreno.cronjob.usecase.base;

import com.edisonmoreno.cronjob.model.base.Command;

public interface UseCaseHandler {
    void consume(Command command);
}
