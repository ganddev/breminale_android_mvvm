package de.ahlfeld.breminale.caches;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public class LocationCache {


    private LocationSources sources;

    public LocationCache() {
        sources = new LocationSources();
    }

   /* @Override
    public Observable<Location> getLocation(Integer locationId) {
        Observable<Location> source = Observable.concat(sources.memory(locationId), sources.network(locationId)

        ).first(data -> data != null && data.isUpToDate());
    }*/
}
