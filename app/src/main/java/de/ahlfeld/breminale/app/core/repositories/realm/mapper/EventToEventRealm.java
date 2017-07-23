package de.ahlfeld.breminale.app.core.repositories.realm.mapper;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.core.repositories.Mapper;
import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventToEventRealm implements Mapper<Event, EventRealm> {

    private final Realm realm;

    public EventToEventRealm(@NonNull Realm realm) {
        this.realm = realm;
    }

    @Override
    public EventRealm map(@NonNull Event event) {
        EventRealm eventRealm = new EventRealm();
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
        eventRealm.setDeleted(event.getDeleted());
        return eventRealm;
    }
}
