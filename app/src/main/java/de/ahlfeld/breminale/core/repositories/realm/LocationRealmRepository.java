package de.ahlfeld.breminale.core.repositories.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.core.repositories.Mapper;
import de.ahlfeld.breminale.core.repositories.Repository;
import de.ahlfeld.breminale.core.repositories.Specification;
import de.ahlfeld.breminale.core.repositories.realm.mapper.LocationRealmToLocation;
import de.ahlfeld.breminale.core.repositories.realm.mapper.LocationToLocationRealm;
import de.ahlfeld.breminale.core.repositories.realm.modelRealm.LocationRealm;
import de.ahlfeld.breminale.core.repositories.realm.specifications.LocationByIdSpecification;
import de.ahlfeld.breminale.core.repositories.realm.specifications.RealmSpecification;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public class LocationRealmRepository implements Repository<Location> {

    private final Mapper<LocationRealm, Location> toLocation;
    private final Mapper<Location, LocationRealm> toLocationRealm;
    private final RealmConfiguration realmConfiguration;


    public LocationRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .build();
        final Realm realm = Realm.getInstance(realmConfiguration);

        this.toLocation = new LocationRealmToLocation();
        this.toLocationRealm = new LocationToLocationRealm(realm);

    }

    @Override
    public Observable<Location> getById(@NonNull String id) {
        return query(new LocationByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<String> add(Location item) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toLocationRealm.map(item)));
        realm.close();
        return Observable.just(String.valueOf(item.getId()));
    }

    @Override
    public Observable<Integer> add(Iterable<Location> items) {
        int size = 0;

        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(realmParam -> {
            for (de.ahlfeld.breminale.core.domain.domain.Location location : items) {
                realm.copyToRealmOrUpdate(toLocationRealm.map(location));
            }
        });
        realm.close();

        if (items instanceof Collection<?>) {
            size = ((Collection<?>) items).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Location> update(@NonNull Location item) {
        final Realm realm = Realm.getDefaultInstance();
        LocationRealm locationRealm = realm.where(LocationRealm.class).equalTo("id", item.getId()).findFirst();

        //TODO
        //realm.executeTransaction(realmParam -> realm.copyToRealmOrUpdate());

        realm.close();

        return Observable.just(item);
    }

    @Override
    public Observable<Integer> remove(@NonNull Location item) {
        final Realm realm = Realm.getDefaultInstance();
        LocationRealm locationRealm = realm.where(LocationRealm.class).equalTo("id", item.getId()).findFirst();
        realm.executeTransaction(realmParam -> locationRealm.deleteFromRealm());
        realm.close();
        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(locationRealm.isValid() ? 0 : 1);
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        Realm realm = Realm.getDefaultInstance();

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final LocationRealm locationRealm = (LocationRealm) realmSpecification.toPlantRealm(realm);

        realm.executeTransaction(realmParam -> locationRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(locationRealm.isValid() ? 0 : 1);
    }

    @Override
    public Observable<List<Location>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final Realm realm = Realm.getDefaultInstance();
        final Observable<RealmResults<LocationRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<PlantRealm>> into Observable<List<Plant>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(locationRealm -> toLocation.map(locationRealm))
                        .toList());
    }

    public void removeAll() {

    }
}
