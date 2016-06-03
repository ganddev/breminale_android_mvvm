package de.ahlfeld.breminale.core.repositories.realm.mapper;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.core.repositories.Mapper;
import de.ahlfeld.breminale.core.repositories.realm.modelRealm.LocationRealm;
import io.realm.Realm;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationToLocationRealm implements Mapper<Location, LocationRealm> {

    private final Realm realm;

    public LocationToLocationRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public LocationRealm map(@NonNull Location location) {
        return null;
    }

    @Override
    public LocationRealm copy(@NonNull Location location, @NonNull LocationRealm locationRealm) {
        return null;
    }
}
