package de.ahlfeld.breminale.app.core.repositories.restAPI;

import android.support.annotation.NonNull;

import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.core.repositories.Repository;
import de.ahlfeld.breminale.app.core.repositories.Specification;
import de.ahlfeld.breminale.app.core.repositories.network.ServiceFactory;
import de.ahlfeld.breminale.app.core.repositories.restAPI.services.EventRestAPI;
import io.reactivex.Flowable;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public class RestEventAPIRepository implements Repository<Event> {

    private EventRestAPI api;

    public RestEventAPIRepository(){
        api = ServiceFactory.createRetrofitService(EventRestAPI.class);
    }

    @Override
    public Flowable<Event> getById(@NonNull Integer id) {
        return api.getEvent(id);
    }

    @Override
    public Flowable<String> add(Event item) {
        return null;
    }

    @Override
    public Flowable<Integer> add(Iterable<Event> items) {
        return null;
    }

    @Override
    public Flowable<Event> update(Event item) {
        return null;
    }

    @Override
    public Flowable<Integer> remove(Event item) {
        return null;
    }

    @Override
    public Flowable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public Flowable<List<Event>> query(Specification specification) {
        return api.getEvents();
    }
}
