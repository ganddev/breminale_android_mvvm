package de.ahlfeld.breminale.app.core.repositories.restAPI.services;

import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Event;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public interface EventRestAPI {

    @GET("events.json")
    Flowable<List<Event>> getEvents();

    @GET("events/{id}.json")
    Flowable<Event> getEvent(@Path("id") int eventId);

}
