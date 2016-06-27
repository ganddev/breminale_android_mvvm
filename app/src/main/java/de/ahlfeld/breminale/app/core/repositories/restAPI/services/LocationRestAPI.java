package de.ahlfeld.breminale.app.core.repositories.restAPI.services;

import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Location;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public interface LocationRestAPI {

    @GET("locations.json")
    Observable<List<Location>> getLocations();

    @GET("locations/{id}.json")
    Observable<Location> getLocation(@Path("id") int locationId);

}
