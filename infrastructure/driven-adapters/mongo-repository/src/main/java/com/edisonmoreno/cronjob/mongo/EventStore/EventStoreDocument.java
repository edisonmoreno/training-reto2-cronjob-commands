package com.edisonmoreno.cronjob.mongo.EventStore;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@Document
public class EventStoreDocument {
    @Id
    private String id;
    @Indexed
    private String aggregateId;
    @Indexed
    private Date occurredOn;
    private String typeName;
    private String eventBody;


    @Override
    public String toString() {
        return "EventStoreDocument{" +
                "id='" + id + '\'' +
                ", aggregateId='" + aggregateId + '\'' +
                ", occurredOn=" + occurredOn +
                ", typeName='" + typeName + '\'' +
                ", eventBody='" + eventBody + '\'' +
                '}';
    }
}