package de.ahlfeld.breminale.core.repositories.realm.mapper;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.realm.modelRealm.EventRealm;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventRealmToEvent implements de.ahlfeld.breminale.core.repositories.Mapper<EventRealm, Event> {
    @Override
    public Event map(@NonNull EventRealm eventRealm) {
        Event event = new Event();
        event.setId(eventRealm.getId());
        copy(eventRealm, event);
        return event;
    }

    @Override
    public Event copy(@NonNull EventRealm eventRealm,@NonNull Event event) {
        event.setName(eventRealm.getName());
        event.setDescription(eventRealm.getDescription());
        event.setFavorit(eventRealm.isFavorit());
        event.setLocationId(eventRealm.getLocationId());
        event.setDeleted(eventRealm.isDeleted());
        event.setStartTime(eventRealm.getStartTime());
        event.imageUrl(eventRealm.getImageUrl());
        event.setSoundcloudUserId(eventRealm.getSoundcloudUserId());
        event.setSoundcloudUrl(eventRealm.getSoundcloudUrl());
        return event;
    }
}
