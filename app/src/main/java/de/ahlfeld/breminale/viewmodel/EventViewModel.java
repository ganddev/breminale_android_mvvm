package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;

import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.models.Location;
import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public EventViewModel(Context context, Event event, DataListener dataListener) {
        this.event = event;
        this.dataListener = dataListener;
        this.locationName = new ObservableField<>("No location");
        this.isCompact = new ObservableBoolean(true);
        this.context = context;
        getLocationName();

        isFavorit = new ObservableBoolean(event.getFavorit());
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

    public void getLocationName() {
        LocationSources locationSources = new LocationSources();
        Observable<Location> call = Observable.concat(locationSources.memory(this.event.getLocationId()), locationSources.network(this.event.getLocationId())).first(new Func1<Location, Boolean>() {
            @Override
            public Boolean call(Location location) {
                return location != null;
            }
        });
        locationSubscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) {
                            dataListener.onLocationChanged(location);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading location", e);
                    }

                    @Override
                    public void onNext(Location location) {
                        EventViewModel.this.location = location;
                        locationName.set(location.getName());
                    }
                });
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
        isFavorit.set(!this.event.getFavorit());
        this.event.setFavorit(!this.event.getFavorit());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(this.event);
        realm.commitTransaction();
        realm.close();
    }

    public String imageUrl() {
        return this.event.imageUrl();
    }

    public interface DataListener {
        void onLocationChanged(Location location);
    }
}
