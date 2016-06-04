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
        Location location = new Location();
        location.setId(locationRealm.getId());
        copy(locationRealm, location);
        return location;
    }

    @Override
    public Location copy(@NonNull LocationRealm locationRealm, @NonNull Location location) {
        location.setName(locationRealm.getName());
        location.setDescription(locationRealm.getDescription());
        location.setDeleted(locationRealm.getDeleted());
        location.setLatitude(locationRealm.getLatitude());
        location.setLongitude(locationRealm.getLongitude());
        location.setOriginalImageUrl(locationRealm.getOriginalImageUrl());
        location.setMediumImageUrl(locationRealm.getMediumImageUrl());
        location.setThumbImageUrl(locationRealm.getThumbImageUrl());
        return location;
    }
}
