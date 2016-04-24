package de.ahlfeld.breminale.caches;

import java.util.List;

import de.ahlfeld.breminale.networking.BreminaleService;
import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class LocationSources implements IPersist<Location>{

    public Observable<Location> memory(Integer locationId) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(Location.class).equalTo("id", locationId).findFirst()));
    }

    public Observable<Location> network(Integer locationId) {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getLocation(locationId).map(new Func1<Location, Location>() {
            @Override
            public Location call(Location location) {
                return persistObject(location);
            }
        });
    }

    public Observable<List<Location>> memory() {
        final Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(Location.class).findAll())).filter(new Func1<List<Location>, Boolean>() {
            @Override
            public Boolean call(List<Location> locations) {
                return !locations.isEmpty();
            }
        });
    }

    public Observable<List<Location>> network() {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getLocations().map(new Func1<List<Location>, List<Location>>() {
            @Override
            public List<Location> call(List<Location> locations) {
                return persistObjects(locations);
            }
        });
    }

    @Override
    public Location persistObject(Location object) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
        return object;
    }

    @Override
    public List<Location> persistObjects(List<Location> objects) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
        realm.close();
        return objects;
    }
}
