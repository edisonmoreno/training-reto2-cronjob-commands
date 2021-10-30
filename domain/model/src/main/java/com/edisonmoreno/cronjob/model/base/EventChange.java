package com.edisonmoreno.cronjob.model.base;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public interface EventChange {
    Set<Consumer<? super DomainEvent>> behaviors = new HashSet<>();

    default void listener(Consumer<? extends DomainEvent> changeEvent) {
        behaviors.add((Consumer<? super DomainEvent>) changeEvent);
    }
}
