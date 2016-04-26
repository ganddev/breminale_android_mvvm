package de.ahlfeld.breminale.caches;


import java.util.List;

import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.networking.BreminaleService;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;

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
        return Observable.just(realm.copyFromRealm(realm.where(Event.class).findAll())).filter(new Func1<List<Event>, Boolean>() {
            @Override
            public Boolean call(List<Event> events) {
                return !events.isEmpty();
            }
        });
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
        return service.getEvents().map(new Func1<List<Event>, List<Event>>() {
            @Override
            public List<Event> call(List<Event> events) {
                return persistObjects(events);
            }
        });
    }

    @Override
    public Event persistObject(final Event object) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
        return object;
    }

    @Override
    public List<Event> persistObjects(List<Event> objects) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
        realm.close();
        return objects;
    }
}
