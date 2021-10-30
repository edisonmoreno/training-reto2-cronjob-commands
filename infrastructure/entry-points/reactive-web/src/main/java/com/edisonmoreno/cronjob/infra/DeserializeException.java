package com.edisonmoreno.cronjob.infra;

public class DeserializeException extends RuntimeException {
    public DeserializeException(Throwable cause) {
        super(cause);
    }
}