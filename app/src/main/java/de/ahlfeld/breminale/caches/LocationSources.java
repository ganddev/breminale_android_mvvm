package de.ahlfeld.breminale.caches;

import java.util.List;

import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class LocationSources {

    public Observable<Location> memory(Integer locationId) {
        Observable<Location> observable = Realm.getDefaultInstance().where(Location.class).equalTo("id", locationId).findFirstAsync().asObservable();
        return observable;
    }

    public Observable<Location> network(Integer locationId) {
        BreminaleService service = BreminaleService.Factory.create();
        Observable<Location> call = service.getLocation(locationId);
        //TODO store object in database
        return call;
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
                final Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(locations);
                realm.commitTransaction();
                realm.close();
                return locations;
            }
        });
    }


}
