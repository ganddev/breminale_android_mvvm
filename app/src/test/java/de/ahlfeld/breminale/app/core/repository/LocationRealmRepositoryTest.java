package de.ahlfeld.breminale.app.core.repository;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import de.ahlfeld.breminale.app.core.repositories.realm.LocationRealmRepository;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationRealmRepositoryTest extends AndroidTestCase {

    private LocationRealmRepository repository;
    private Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = new MockContext();

        setContext(context);

        repository = new LocationRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }


}
