package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.LocationRealmRepository;
import rx.Subscription;

/**
 * Created by bjornahlfeld on 05.04.16.
 */
public class EventViewModel implements ViewModel {


    private Context context;
    private Location location;

    private Event event;
    private Subscription locationSubscription;

    public ObservableField<String> locationName;

    public ObservableBoolean isCompact;

    private DataListener dataListener;
    private String TAG = EventViewModel.class.getSimpleName();

    public ObservableBoolean isFavorit;

    public EventViewModel(@NonNull Context context, @NonNull Event event, @NonNull DataListener dataListener) {
        this.event = event;
        this.dataListener = dataListener;
        this.locationName = new ObservableField<>("No location");
        this.isCompact = new ObservableBoolean(true);
        this.context = context;

        isFavorit = new ObservableBoolean(event.getFavorit());

        loadLocation();
    }

    private void loadLocation() {
        LocationRealmRepository realmRepository = new LocationRealmRepository(context);
        locationSubscription = realmRepository.getById(event.getLocationId()).subscribe(locationFromDB -> {
            if(dataListener != null) {
                dataListener.onLocationChanged(locationFromDB);
            }
            locationName.set(locationFromDB.getName());
        });
    }

    @Override
    public void destroy() {
        if (locationSubscription != null && !locationSubscription.isUnsubscribed()) {
            locationSubscription.unsubscribe();
        }
        locationSubscription = null;
        context = null;
    }

    public boolean showSoundcloudFragment() {
        try {
            return Long.parseLong(this.event.getSoundcloudUserId()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE, d. MMMM / HH:mm ");
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(event.getStartTime()));
        sb.append("Uhr");
        sb.append(" /");
        return sb.toString();
    }

    public void onExpandClick(View view) {
        Log.d(TAG, "onexpand click");
        this.isCompact.set(!this.isCompact.get());
    }

    public String name() {
        return this.event.getName();
    }

    public String description() {
        return this.event.getDescription();
    }


    public void onFabClick(View view) {
        EventRealmRepository repository = new EventRealmRepository(context);
        event.setFavorit(!event.getFavorit());
        repository.saveEventAsFavorit(event);
        isFavorit.set(event.getFavorit());
    }

    public String imageUrl() {
        return this.event.imageUrl();
    }

    public interface DataListener {
        void onLocationChanged(Location location);
    }
}
