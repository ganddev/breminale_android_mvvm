package de.ahlfeld.breminale.caches;

import java.util.List;

import de.ahlfeld.breminale.models.Location;
import de.ahlfeld.breminale.networking.BreminaleService;
import io.realm.Realm;
import rx.Observable;

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
        return service.getLocation(locationId).map(location -> persistObject(location));
    }

    public Observable<List<Location>> memory() {
        final Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(Location.class).equalTo("deleted",false).findAll())).filter(locations -> !locations.isEmpty());
    }

    public Observable<List<Location>> network() {
        BreminaleService service = BreminaleService.Factory.create();
        return service.getLocations().map(locations -> persistObjects(locations));
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

    public Location createUpdateOrDelete(Location object) {
        final Realm realm = Realm.getDefaultInstance();
        Location locationToUpdate = realm.where(Location.class).equalTo("id",object.getId()).findFirst();
        realm.beginTransaction();
        if(locationToUpdate != null) {
            if(object.getDeleted()) {
                locationToUpdate.deleteFromRealm();
            } else {
                locationToUpdate.setName(object.getName());
                locationToUpdate.setDescription(object.getDescription());
                locationToUpdate.setLatitude(object.getLatitude());
                locationToUpdate.setLongitude(object.getLongitude());
                locationToUpdate.setOriginalImageUrl(object.getOriginalImageUrl());
                locationToUpdate.setMediumImageUrl(object.getMediumImageUrl());
                locationToUpdate.setThumbImageUrl(object.getThumbImageUrl());
            }
        } else {
            if(!object.getDeleted()) {
                realm.copyToRealm(object);
            }
        }
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
