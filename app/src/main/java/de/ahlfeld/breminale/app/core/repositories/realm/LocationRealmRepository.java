package de.ahlfeld.breminale.app.core.repositories.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Location;
import de.ahlfeld.breminale.app.core.repositories.Mapper;
import de.ahlfeld.breminale.app.core.repositories.Repository;
import de.ahlfeld.breminale.app.core.repositories.Specification;
import de.ahlfeld.breminale.app.core.repositories.realm.mapper.LocationRealmToLocation;
import de.ahlfeld.breminale.app.core.repositories.realm.mapper.LocationToLocationRealm;
import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.LocationRealm;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.LocationByIdSpecification;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.RealmSpecification;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public class LocationRealmRepository implements Repository<Location> {

    private final Mapper<Location, LocationRealm> toLocationRealm;
    private final RealmConfiguration realmConfiguration;


    public LocationRealmRepository(@NonNull Context context) {
        this.realmConfiguration = new RealmConfiguration.Builder()
                .build();
        final Realm realm = Realm.getInstance(realmConfiguration);

        this.toLocationRealm = new LocationToLocationRealm(realm);

    }

    @Override
    public Flowable<Location> getById(@NonNull Integer id) {
        return query(new LocationByIdSpecification(id)).flatMap(Flowable::fromIterable);
    }

    @Override
    public Flowable<String> add(Location item) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toLocationRealm.map(item)));
        realm.close();
        return Flowable.just(String.valueOf(item.getId()));
    }

    @Override
    public Flowable<Integer> add(Iterable<Location> items) {
        int size = 0;

        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(realmParam -> {
            for (de.ahlfeld.breminale.app.core.domain.domain.Location location : items) {
                realm.copyToRealmOrUpdate(toLocationRealm.map(location));
            }
        });
        realm.close();

        if (items instanceof Collection<?>) {
            size = ((Collection<?>) items).size();
        }

        return Flowable.just(size);
    }

    @Override
    public Flowable<Location> update(@NonNull Location item) {
        final Realm realm = Realm.getDefaultInstance();
        LocationRealm locationRealm = realm.where(LocationRealm.class).equalTo("id", item.getId()).findFirst();

        if (locationRealm != null) {
            realm.executeTransaction(realmParam -> {
                realm.copyToRealmOrUpdate(toLocationRealm.copy(item, locationRealm));
            });
        } else {
            add(item);
        }
        realm.close();

        return Flowable.just(item);
    }

    @Override
    public Flowable<Integer> remove(@NonNull Location item) {
        final Realm realm = Realm.getDefaultInstance();
        LocationRealm locationRealm = realm.where(LocationRealm.class).equalTo("id", item.getId()).findFirst();
        if (locationRealm != null) {
            realm.executeTransaction(realmParam -> locationRealm.deleteFromRealm());

            // if locationrealm.isValid() is false, it is because the realm object was deleted
            return Flowable.just(locationRealm.isValid() ? 0 : 1);
        }
        realm.close();

        return Flowable.just(0);
    }

    @Override
    public Flowable<Integer> remove(Specification specification) {
        Realm realm = Realm.getDefaultInstance();

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final LocationRealm locationRealm = (LocationRealm) realmSpecification.toPlantRealm(realm);

        realm.executeTransaction(realmParam -> locationRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Flowable.just(locationRealm.isValid() ? 0 : 1);
    }

    @Override
    public Flowable<List<Location>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final Realm realm = Realm.getDefaultInstance();
        final Flowable<RealmResults<LocationRealm>> realmResults = realmSpecification.toFlowableRealmResults(realm);
        final LocationRealmToLocation mapper = new LocationRealmToLocation();

        // convert Observable<RealmResults<LocationRealm>> into Flowable<List<Location>>
        return realmResults.map(new Function<RealmResults<LocationRealm>, List<Location>>() {
            @Override
            public List<Location> apply(RealmResults<LocationRealm> locationRealms) throws Exception {
                List<Location> locations = new ArrayList<>();
                for (LocationRealm locationRealm : locationRealms) {
                    locations.add(mapper.map(locationRealm));
                }
                return locations;
            }
        });
    }

    public void removeAll() {

    }
}
