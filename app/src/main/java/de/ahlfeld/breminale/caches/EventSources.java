package de.ahlfeld.breminale.caches;


import android.support.annotation.NonNull;

import java.util.List;

import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.networking.BreminaleService;
import io.realm.Realm;
import rx.Observable;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class EventSources implements IPersist<Event> {

    private static final String TAG = EventSources.class.getSimpleName();

    private Event memory = null;

    public Observable<Event> memory(Integer eventId) {
        Observable<Event> observable = Observable.create(subscriber -> {
           subscriber.onNext(memory);
        });
        return observable;
    }

    public Observable<List<Event>> memory() {
        final Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(Event.class).equalTo("deleted", false).findAll().sort("startTime"))).filter(events -> !events.isEmpty());
    }

    public Observable<Event> network(final Integer eventId) {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getEvent(eventId).doOnNext(event -> {
            memory = event;
            persistObject(event);
        });
    }


    /**
     * Load events from api
    */
    public Observable<List<Event>> network() {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getEvents().map(events -> persistObjects(events));
    }

    @Override
    public Event persistObject(final Event object) {
        return createUpdateOrDelete(object);
    }

    @NonNull
    private Event createUpdateOrDelete(Event object) {
        final Realm realm = Realm.getDefaultInstance();
        Event eventToUpdate = realm.where(Event.class).equalTo("id",object.getId()).findFirst();
        realm.beginTransaction();
        if(eventToUpdate != null) {
            if(object.getDeleted()) {
                eventToUpdate.deleteFromRealm();
            } else {
                eventToUpdate.setName(object.getName());
                eventToUpdate.setDescription(object.getDescription());
                eventToUpdate.setLocationId(object.getLocationId());
                eventToUpdate.setSoundcloudUrl(object.getSoundcloudUrl());
                eventToUpdate.setSoundcloudUserId(object.getSoundcloudUserId());
                eventToUpdate.setStartTime(object.getStartTime());
                eventToUpdate.imageUrl(object.imageUrl());
            }
        } else {
            if(!object.getDeleted()) {
                realm.copyToRealm(object);
            }
        }
        realm.commitTransaction();
        realm.close();
        return object;
    }

    @Override
    public List<Event> persistObjects(List<Event> objects) {
        for(Event event : objects) {
            createUpdateOrDelete(event);
        }
        return objects;
    }
}
