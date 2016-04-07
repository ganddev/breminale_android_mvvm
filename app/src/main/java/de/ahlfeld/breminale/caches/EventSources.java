package de.ahlfeld.breminale.caches;

import java.util.List;

import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Event;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class EventSources implements IPersist<Event>{

    public Observable<Event> memory(Integer eventId) {
        return Realm.getDefaultInstance().where(Event.class).equalTo("id", eventId).findFirstAsync().asObservable();
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

    public Observable<Event> network(Integer eventId) {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getEvent(eventId).map(new Func1<Event, Event>() {
            @Override
            public Event call(Event event) {
                persistObject(event);
                return event;
            }
        });
    }

    public Observable<List<Event>> network() {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getEvents().map(new Func1<List<Event>, List<Event>>() {
            @Override
            public List<Event> call(List<Event> events) {
                persistObjects(events);
                return events;
            }
        });
    }

    @Override
    public void persistObject(Event object) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void persistObjects(List<Event> objects) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
        realm.close();
    }
}
