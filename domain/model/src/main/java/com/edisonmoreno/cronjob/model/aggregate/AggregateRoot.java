package com.edisonmoreno.cronjob.model.aggregate;

import com.edisonmoreno.cronjob.model.base.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final String id;
    private final List<DomainEvent> changes = new ArrayList<>();

    protected AggregateRoot(String id) {
        this.id = id;
    }

    protected void appendChange(DomainEvent event) {
        event.setAggregateId(id);
        changes.add(event);
    }

    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changes);
    }
}
