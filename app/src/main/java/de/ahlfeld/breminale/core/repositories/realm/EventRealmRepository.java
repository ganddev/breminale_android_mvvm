package de.ahlfeld.breminale.core.repositories.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.Mapper;
import de.ahlfeld.breminale.core.repositories.Repository;
import de.ahlfeld.breminale.core.repositories.Specification;
import de.ahlfeld.breminale.core.repositories.realm.mapper.EventRealmToEvent;
import de.ahlfeld.breminale.core.repositories.realm.mapper.EventToEventRealm;
import de.ahlfeld.breminale.core.repositories.realm.mapper.EventToEventRealmFavorit;
import de.ahlfeld.breminale.core.repositories.realm.modelRealm.EventRealm;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventByIdSpecification;
import de.ahlfeld.breminale.core.repositories.realm.specifications.RealmSpecification;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public class EventRealmRepository implements Repository<Event> {

    private final Mapper<EventRealm, Event> toEvent;
    private final Mapper<Event, EventRealm> toEventRealm;
    private final RealmConfiguration realmConfiguration;
    private final Mapper<Event, EventRealm> toEventRealmFavorit;

    public EventRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context).build();
        final Realm realm = Realm.getInstance(realmConfiguration);

        this.toEvent = new EventRealmToEvent();
        this.toEventRealm = new EventToEventRealm(realm);
        this.toEventRealmFavorit = new EventToEventRealmFavorit(realm);
    }

    @Override
    public Observable<Event> getById(@NonNull Integer id) {
        return query(new EventByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<String> add(@NonNull final Event item) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toEventRealm.map(item)));
        realm.close();
        return Observable.just(String.valueOf(item.getId()));
    }

    @Override
    public Observable<Integer> add(@NonNull Iterable<Event> items) {

        int size = 0;

        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(realmParam -> {
            for(Event event : items){
                realm.copyToRealmOrUpdate(toEventRealm.map(event));
            }
        });
        realm.close();

        if(items instanceof Collection<?>) {
            size = ((Collection<?>) items).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Event> update(@NonNull final Event item) {
        final Realm realm = Realm.getDefaultInstance();
        EventRealm eventRealm = realm.where(EventRealm.class).equalTo("id", item.getId()).findFirst();

        if(eventRealm != null) {
            realm.executeTransaction(realmParam -> {
                realmParam.copyToRealmOrUpdate(toEventRealm.copy(item, eventRealm));
            });
        } else {
            add(item);
        }
        realm.close();

        return Observable.just(item);
    }

    @Override
    public Observable<Integer> remove(@NonNull final Event item) {
        final Realm realm = Realm.getDefaultInstance();
        EventRealm eventRealm = realm.where(EventRealm.class).equalTo("id", item.getId()).findFirst();
        if(eventRealm != null) {
            realm.executeTransaction(realmParam -> eventRealm.deleteFromRealm());
            return Observable.just(eventRealm.isValid() ? 0 : 1);
        }
        realm.close();
        // if eventRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(0);
    }

    @Override
    public Observable<Integer> remove(@NonNull Specification specification) {
        Realm realm = Realm.getDefaultInstance();

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final EventRealm eventRealm = (EventRealm) realmSpecification.toPlantRealm(realm);

        realm.executeTransaction(realmParam -> eventRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(eventRealm.isValid() ? 0 : 1);
    }


    @Override
    public Observable<List<Event>> query(@NonNull final Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final Realm realm = Realm.getDefaultInstance();
        final Observable<RealmResults<EventRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<PlantRealm>> into Observable<List<Plant>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(eventRealm -> toEvent.map(eventRealm))
                        .toList());

    }

    public void removeAll() {
        // Delete all
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }

    public Observable<Event> saveEventAsFavorit(Event item) {
        final Realm realm = Realm.getDefaultInstance();
        EventRealm eventRealm = realm.where(EventRealm.class).equalTo("id", item.getId()).findFirst();

        if(eventRealm != null) {
            realm.executeTransaction(realmParam -> {
                realmParam.copyToRealmOrUpdate(toEventRealmFavorit.copy(item, eventRealm));
            });
        }
        realm.close();

        return Observable.just(item);
    }
}
