package de.ahlfeld.breminale.app.core.repositories.restAPI.services;

import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Location;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public interface LocationRestAPI {

    @GET("locations.json")
    Flowable<List<Location>> getLocations();

    @GET("locations/{id}.json")
    Flowable<Location> getLocation(@Path("id") int locationId);

}
