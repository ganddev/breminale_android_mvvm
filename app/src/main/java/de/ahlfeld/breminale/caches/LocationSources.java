package de.ahlfeld.breminale.caches;

import java.util.ArrayList;
import java.util.List;

import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;

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
        List<Location> locations = new ArrayList<>(Realm.getDefaultInstance().where(Location.class).findAll());
        Observable<List<Location>> observable = Observable.just(locations);
        return observable;
    }

    public Observable<List<Location>> network() {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getLocations().subscribe(new Subscriber<List<Location>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Location> locations) {
                Realm realm = Realm.getDefaultInstance();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(locations);
                realm.commitTransaction();
            }
        });
    }


}
