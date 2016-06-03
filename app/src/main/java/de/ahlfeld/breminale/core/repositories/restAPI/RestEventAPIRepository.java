package de.ahlfeld.breminale.core.repositories.restAPI;

import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.Repository;
import de.ahlfeld.breminale.core.repositories.Specification;
import de.ahlfeld.breminale.core.repositories.network.ServiceFactory;
import de.ahlfeld.breminale.core.repositories.restAPI.services.EventRestAPI;
import rx.Observable;

/**
 * Created by bjornahlfeld on 31.05.16.
 */
public class RestEventAPIRepository implements Repository<Event> {

    private EventRestAPI api;

    public RestEventAPIRepository(){
        api = ServiceFactory.createRetrofitService(EventRestAPI.class);
    }

    @Override
    public Observable<Event> getById(String id) {
        return api.getEvent(Integer.valueOf(id));
    }

    @Override
    public Observable<String> add(Event item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Event> items) {
        return null;
    }

    @Override
    public Observable<Event> update(Event item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(Event item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public Observable<List<Event>> query(Specification specification) {
        return api.getEvents();
    }
}
