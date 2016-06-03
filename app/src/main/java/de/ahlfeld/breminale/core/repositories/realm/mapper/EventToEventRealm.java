package de.ahlfeld.breminale.core.repositories.realm.mapper;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.Mapper;
import de.ahlfeld.breminale.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventToEventRealm implements Mapper<Event, EventRealm> {

    private final Realm realm;

    public EventToEventRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public EventRealm map(@NonNull Event event) {
        EventRealm eventRealm = realm.createObject(EventRealm.class);
        eventRealm.setId(event.getId());
        copy(event, eventRealm);
        return eventRealm;
    }

    @Override
    public EventRealm copy(@NonNull Event event, @NonNull EventRealm eventRealm) {

        eventRealm.setName(event.getName());
        eventRealm.setDescription(event.getDescription());
        eventRealm.setStartTime(event.getStartTime());
        eventRealm.setLocationId(event.getLocationId());
        eventRealm.setSoundcloudUrl(event.getSoundcloudUrl());
        eventRealm.setSoundcloudUserId(event.getSoundcloudUserId());
        eventRealm.setImageUrl(event.imageUrl());
        eventRealm.setFavorit(event.getFavorit());
        eventRealm.setDeleted(event.getDeleted());

        return eventRealm;
    }
}
