package de.ahlfeld.breminale.models;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public interface BreminaleService {

    @GET("locations")
    Observable<List<Location>> getLocations();

    @GET("locations/{id}")
    Observable<Location> getLocation(@Path("id") int locationId);

    class Factory {
        public static BreminaleService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://serene-ocean-3356.herokuapp.com/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(BreminaleService.class);
        }
    }
}
