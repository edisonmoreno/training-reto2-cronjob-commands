package com.edisonmoreno.cronjob.common;


import com.edisonmoreno.cronjob.model.EventStore;

public final class StoredEventSerializer extends AbstractSerializer {

    private static StoredEventSerializer eventSerializer;

    private StoredEventSerializer() {
        super();
    }


    public static synchronized StoredEventSerializer instance() {
        if (StoredEventSerializer.eventSerializer == null) {
            StoredEventSerializer.eventSerializer = new StoredEventSerializer();
        }
        return StoredEventSerializer.eventSerializer;
    }


    public EventStore deserialize(String aSerialization, Class<EventStore> aType) {
        return gson.fromJson(aSerialization, aType);
    }


    public String serialize(EventStore object) {
        return gson.toJson(object);
    }

}