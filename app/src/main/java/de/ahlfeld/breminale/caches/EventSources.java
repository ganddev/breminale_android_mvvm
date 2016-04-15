package de.ahlfeld.breminale.caches;

import android.util.Log;

import java.util.List;

import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class EventSources implements IPersist<Event> {

    private static final String TAG = EventSources.class.getSimpleName();

    public Observable<Event> memory(Integer eventId) {
        return Realm.getDefaultInstance().where(Event.class).equalTo("id", eventId).findFirst().asObservable();
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
        final BreminaleService service = BreminaleService.Factory.create();
        return service.getEvents().flatMap(new Func1<List<Event>, Observable<List<Event>>>() {
            @Override
            public Observable<List<Event>> call(List<Event> events) {
                for(final Event event : events) {
                    service.getLocation(event.getLocationId()).map(new Func1<Location, Event>() {
                        @Override
                        public Event call(Location location) {
                            Log.d(TAG, location.toString());
                            event.setLocation(location);
                            return event;
                        }
                    });
                }
                return Observable.just(events);
            }
        });


        /*map(new Func1<List<Event>, List<Event>>() {
            @Override
            public List<Event> call(List<Event> events) {
                getLocationsForEvents(events);
                persistObjects(events);
                return events;
            }
        });*/
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

    public void getLocationsForEvents(List<Event> events) {
        LocationSources sources = new LocationSources();
        for (final Event event : events) {
            Observable<Location> call = Observable.concat(sources.memory(event.getLocationId()), sources.network(event.getLocationId())).first(new Func1<Location, Boolean>() {
                @Override
                public Boolean call(Location location) {
                    return location != null;
                }
            });
            Subscription subscription = call.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Subscriber<Location>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Error loading locations", e);
                        }

                        @Override
                        public void onNext(Location location) {
                            Log.d(TAG, "location loaded: " + location.toString());
                            event.setLocation(location);
                            persistObject(event);
                        }
                    });
        }
    }
}
