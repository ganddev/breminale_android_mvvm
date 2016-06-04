package de.ahlfeld.breminale.core.repositories.restAPI.services;

import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public interface EventRestAPI {

    @GET("events.json")
    Observable<List<Event>> getEvents();

    @GET("events/{id}.json")
    Observable<Event> getEvent(@Path("id") int eventId);

}
