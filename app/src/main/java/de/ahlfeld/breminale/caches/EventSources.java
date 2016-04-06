package de.ahlfeld.breminale.caches;

import java.util.Collections;
import java.util.List;

import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Event;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class EventSources {

    public Observable<Event> memory(Integer eventId) {
        return Realm.getDefaultInstance().where(Event.class).equalTo("id", eventId).findFirstAsync().asObservable();
    }

    public Observable<List<Event>> memory() {
        Observable<List<Event>> observable = Realm.getDefaultInstance().where(Event.class).findAllAsync().asObservable();
        return observable;
    }

    public Observable<Event> network(Integer eventId) {
        BreminaleService service = BreminaleService.Factory.create();
        Observable<Event> call = service.getEvent(eventId);
        //TODO store object in database
        return call;
    }

    public Observable<List<Event>> network() {
        BreminaleService service = BreminaleService.Factory.create();
        Observable<List<Event>> call = service.getEvents();
        //TODO store object in database;

        return call;
    }
}
