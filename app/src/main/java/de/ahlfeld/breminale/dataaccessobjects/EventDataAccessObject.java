package de.ahlfeld.breminale.dataaccessobjects;

import java.util.List;

import de.ahlfeld.breminale.models.Event;
import io.realm.Realm;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class EventDataAccessObject implements IEvent {

    private Realm realm;

    public EventDataAccessObject() {
        realm = Realm.getDefaultInstance();
    }

    public Event getEvent(Integer eventId) {
        return realm.where(Event.class).equalTo("id", eventId).findFirstAsync();
    }

    public List<Event> getAllEvents() {
        return realm.where(Event.class).findAllAsync();
    }
}
