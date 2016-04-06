package de.ahlfeld.breminale.dataaccessobjects;

import java.util.List;

import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class LocationDataAccessObject implements ILocation{

    private Realm realm;

    public LocationDataAccessObject() {
        realm = Realm.getDefaultInstance();
    }


    public Observable<Location> getLocation(Integer locationId) {
        return realm.where(Location.class).equalTo("id", locationId).findFirstAsync();
    }

    public List<Location> getAllLocations() {
        return realm.where(Location.class).findAllAsync();
    }
}
