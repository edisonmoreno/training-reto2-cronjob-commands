package com.edisonmoreno.cronjob.model.aggregate;

import com.edisonmoreno.cronjob.model.base.ChangeEventSubscriber;
import com.edisonmoreno.cronjob.model.base.DomainEvent;
import com.edisonmoreno.cronjob.model.base.EventChange;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final String id;
    private final ChangeEventSubscriber changeEventSubscriber;

    protected AggregateRoot(String id) {
        this.id = id;
        this.changeEventSubscriber = new ChangeEventSubscriber();
    }

    protected final void subscribe(EventChange eventChange) {
        changeEventSubscriber.subscribe(eventChange);
    }

    protected ChangeEventSubscriber.ChangeApply appendChange(DomainEvent event) {
        event.setAggregateId(id);
        return changeEventSubscriber.appendChange(event);
    }

    protected void applyEvent(DomainEvent domainEvent) {
        changeEventSubscriber.applyEvent(domainEvent);
    }

    public List<DomainEvent> getUncommittedChanges() {
        return new ArrayList<>(changeEventSubscriber.getChanges()); // List.copyOf(changeEventSubscriber.getChanges());
    }
}
