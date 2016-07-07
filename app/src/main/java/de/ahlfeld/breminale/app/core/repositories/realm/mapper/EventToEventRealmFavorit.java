package de.ahlfeld.breminale.app.core.repositories.realm.mapper;

import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.core.repositories.Mapper;
import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;

/**
 * Created by bjornahlfeld on 04.06.16.
 */
public class EventToEventRealmFavorit implements Mapper<Event, EventRealm> {

    public EventToEventRealmFavorit() {
    }

    @Override
    public EventRealm map(Event event) {
        return null;
    }

    @Override
    public EventRealm copy(Event event, EventRealm eventRealm) {
        eventRealm.setName(event.getName());
        eventRealm.setFavorit(event.isFavorit());
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
