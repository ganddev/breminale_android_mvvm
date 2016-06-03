package de.ahlfeld.breminale.core.repositories.realm.mapper;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.core.repositories.realm.modelRealm.LocationRealm;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationRealmToLocation implements de.ahlfeld.breminale.core.repositories.Mapper<LocationRealm,Location> {
    @Override
    public Location map(@NonNull LocationRealm locationRealm) {
        return null;
    }

    @Override
    public Location copy(@NonNull LocationRealm locationRealm, @NonNull Location location) {
        return null;
    }
}
