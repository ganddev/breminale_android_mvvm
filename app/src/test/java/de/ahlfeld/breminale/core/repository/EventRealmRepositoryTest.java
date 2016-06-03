package de.ahlfeld.breminale.core.repository;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Date;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventByIdSpecification;
import de.ahlfeld.breminale.core.repositories.realm.specifications.RealmSpecification;
import rx.Observable;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventRealmRepositoryTest extends AndroidTestCase {

    private static final String TAG = EventRealmRepositoryTest.class.getSimpleName();

    private EventRealmRepository repository;
    private final Integer ID = 1;
    private final String NAME = "mango";
    private Context context;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = new MockContext();

        repository = new EventRealmRepository(context);
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {

        // setup
        Event event = createEvent(ID, NAME);
        repository.add(event);

        // verify
        repository.getById(String.valueOf(ID)).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistOneEvent() {

        Event plant = createEvent(ID, NAME);

        repository.add(plant);

        repository.getById(String.valueOf(ID)).subscribe(eventFromDB -> Assert.assertEquals(eventFromDB.getName(), NAME));
    }

    public void testPersistEvents() {
        // setup
        // create three events
        ArrayList<Event> plants = createEvents();

        // when
        Observable<Integer> result = repository.add(plants);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOneEventRealm() {

        // setup
        final String NEW_NAME = "mango2";

        Event event = createEvent(ID, NAME);

        repository.add(event);

        // when
        event.setName(NEW_NAME);
        repository.update(event);

        // assertions
        repository.getById(String.valueOf(ID)).subscribe(eventFromDB -> Assert.assertEquals(NEW_NAME, eventFromDB.getName()));
    }

    public void testRemoveOneEvent() {

        // setup
        Event plant = createEvent(ID, NAME);

        repository.add(plant);

        repository.getById(String.valueOf(ID)).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));

        // when
        Observable<Integer> result = repository.remove(plant);

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }

    public void testRemoveOneEventBySpecification() {

        // setup
        Event plant = createEvent(ID, NAME);

        repository.add(plant);

        repository.getById(String.valueOf(ID)).subscribe(eventFromDB -> Assert.assertEquals(eventFromDB.getName(), NAME));

        RealmSpecification realmSpecification = new EventByIdSpecification(String.valueOf(ID));

        // when
        Observable<Integer> result = repository.remove(realmSpecification);

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }

    private ArrayList<Event> createEvents() {
        ArrayList<Event> plants = new ArrayList<>();

        Event eventOne = createEvent(1, "mango");
        Event eventTwo = createEvent(2, "pera");
        Event eventThree = createEvent(3, "naranja");

        plants.add(eventOne);
        plants.add(eventTwo);
        plants.add(eventThree);

        return plants;
    }

    private Event createEvent(Integer id, String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setDescription("Test Description");
        event.setStartTime(new Date());
        event.setLocationId(1);
        return event;
    }


}
