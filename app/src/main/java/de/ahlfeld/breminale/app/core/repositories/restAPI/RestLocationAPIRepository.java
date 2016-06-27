package de.ahlfeld.breminale.app.core.repositories.restAPI;

import android.support.annotation.NonNull;

import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Location;
import de.ahlfeld.breminale.app.core.repositories.Repository;
import de.ahlfeld.breminale.app.core.repositories.Specification;
import de.ahlfeld.breminale.app.core.repositories.network.ServiceFactory;
import de.ahlfeld.breminale.app.core.repositories.restAPI.services.LocationRestAPI;
import rx.Observable;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public class RestLocationAPIRepository implements Repository<Location> {

    private final LocationRestAPI api;

    public RestLocationAPIRepository() {
        api = ServiceFactory.createRetrofitService(LocationRestAPI.class);
    }

    @Override
    public Observable<Location> getById(@NonNull Integer id) {
        return api.getLocation(id);
    }

    @Override
    public Observable<String> add(Location item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Location> items) {
        return null;
    }

    @Override
    public Observable<Location> update(Location item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(Location item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public Observable<List<Location>> query(Specification specification) {
        return api.getLocations();
    }
}
