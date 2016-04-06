package de.ahlfeld.breminale.caches;

import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;

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
}
