package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.core.repositories.realm.LocationRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.specifications.LocationSpecification;
import rx.Subscription;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class MapViewModel implements ViewModel {


    private static final String TAG = MapViewModel.class.getSimpleName();
    private Context context;
    private DataListener dataListener;

    private Subscription subscription;

    private List<Location> locations;

    public MapViewModel(@NonNull Context context, @NonNull DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;

        loadLocations();
    }

    private void loadLocations() {
        LocationRealmRepository realmRepository = new LocationRealmRepository(this.context);
        LocationSpecification specification = new LocationSpecification();
        subscription = realmRepository.query(specification).subscribe(locationsFromDB -> dataListener.onLocationsChanged(locationsFromDB));
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void destroy() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
        context = null;
    }

    public interface DataListener {
        void onLocationsChanged(List<Location> locations);
    }
}
