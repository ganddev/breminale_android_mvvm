package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

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

    public ObservableInt detailVisibility;

    public ObservableField<String> locationName;

    private Subscription subscription;
    private Location location;
    private NavigateListener navigateListener;

    public MapViewModel(@NonNull Context context, @NonNull DataListener dataListener, @NonNull NavigateListener navigateListener) {
        this.context = context;
        this.dataListener = dataListener;
        this.navigateListener = navigateListener;
        detailVisibility = new ObservableInt(View.INVISIBLE);
        locationName = new ObservableField<>();
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

    public void onMarkerClick(Location location) {
        this.location = location;
        detailVisibility.set(View.VISIBLE);
        locationName.set(location.getName());
    }

    public void onNavigateToClick(View view) {
        if(navigateListener != null) {
            navigateListener.navigateTo(location);
        }
    }
    public void onMapClick() {
        detailVisibility.set(View.INVISIBLE);
    }

    public interface DataListener {
        void onLocationsChanged(List<Location> locations);
    }

    public interface NavigateListener{
        void navigateTo(@NonNull Location location);
    }
}
