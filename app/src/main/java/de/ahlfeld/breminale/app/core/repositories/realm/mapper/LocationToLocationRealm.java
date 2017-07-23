package de.ahlfeld.breminale.app.core.repositories.realm.mapper;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.domain.domain.Location;
import de.ahlfeld.breminale.app.core.repositories.Mapper;
import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.LocationRealm;
import io.realm.Realm;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationToLocationRealm implements Mapper<Location, LocationRealm> {

    private final Realm realm;

    public LocationToLocationRealm(@NonNull Realm realm) {
        this.realm = realm;
    }

    @Override
    public LocationRealm map(@NonNull Location location) {
        LocationRealm locationRealm = new LocationRealm();
        locationRealm.setId(location.getId());
        copy(location, locationRealm);
        return locationRealm;
    }

    @Override
    public LocationRealm copy(@NonNull Location location, @NonNull LocationRealm locationRealm) {
        locationRealm.setName(location.getName());
        locationRealm.setDescription(location.getDescription());
        locationRealm.setLatitude(location.getLatitude());
        locationRealm.setLongitude(location.getLongitude());
        locationRealm.setOriginalImageUrl(location.getOriginalImageUrl());
        locationRealm.setMediumImageUrl(location.getMediumImageUrl());
        locationRealm.setThumbImageUrl(location.getThumbImageUrl());
        locationRealm.setDeleted(location.getDeleted());
        return locationRealm;
    }
}
