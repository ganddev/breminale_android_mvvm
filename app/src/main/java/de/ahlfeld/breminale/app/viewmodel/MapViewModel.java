package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

import de.ahlfeld.breminale.app.core.domain.domain.Location;
import de.ahlfeld.breminale.app.core.repositories.realm.LocationRealmRepository;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.LocationByNameSpecification;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.LocationSpecification;
import rx.Subscription;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class MapViewModel implements ViewModel {

    private WeakReference<Context> contextReference;
    private DataListener dataListener;

    public ObservableInt detailVisibility;

    public ObservableField<String> locationName;

    private Subscription subscription;
    private Location location;
    private NavigateListener navigateListener;

    public MapViewModel(@NonNull Context context, @NonNull DataListener dataListener, @NonNull NavigateListener navigateListener) {
        this.contextReference = new WeakReference<>(context);
        this.dataListener = dataListener;
        this.navigateListener = navigateListener;
        detailVisibility = new ObservableInt(View.INVISIBLE);
        locationName = new ObservableField<>();
        loadLocations();
    }

    public void loadLocations() {
        LocationRealmRepository realmRepository = new LocationRealmRepository(this.contextReference.get().getApplicationContext());
        LocationSpecification specification = new LocationSpecification();
        subscription = realmRepository.query(specification).subscribe(locationsFromDB -> dataListener.onLocationsChanged(locationsFromDB));
    }


    @Override
    public void destroy() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
        contextReference = null;
        dataListener = null;
        navigateListener = null;
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

    public void searchForLocationByName(String query) {
        LocationRealmRepository realmRepository = new LocationRealmRepository(this.contextReference.get().getApplicationContext());
        LocationByNameSpecification specification = new LocationByNameSpecification(query);
        subscription = realmRepository.query(specification).subscribe(locationsFromDB -> dataListener.onLocationsChanged(locationsFromDB));
    }

    public interface DataListener {
        void onLocationsChanged(List<Location> locations);
    }

    public interface NavigateListener{
        void navigateTo(@NonNull Location location);
    }
}
