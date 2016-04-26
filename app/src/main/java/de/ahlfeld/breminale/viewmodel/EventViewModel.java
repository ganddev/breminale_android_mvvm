package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;

import java.text.SimpleDateFormat;

import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.models.Location;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 05.04.16.
 */
public class EventViewModel implements ViewModel{


    private Location location;
    private Context context;

    private Event event;
    private Subscription locationSubscription;

    public ObservableField<String> locationName;

    private DataListener dataListener;
    private String TAG = EventViewModel.class.getSimpleName();

    public EventViewModel(Context context, Event event, DataListener dataListener) {
        this.context = context;
        this.event = event;
        this.dataListener = dataListener;
        locationName = new ObservableField<>("No location");
        getLocationName();
    }

    @Override
    public void destroy() {
        if(locationSubscription != null && !locationSubscription.isUnsubscribed()) {
            locationSubscription.unsubscribe();
        }
        locationSubscription = null;
        context = null;
    }

    public void getLocationName() {
        LocationSources locationSources = new LocationSources();
        Observable<Location> call = Observable.concat(locationSources.memory(this.event.getLocationId()),locationSources.network(this.event.getLocationId())).first(new Func1<Location, Boolean>() {
            @Override
            public Boolean call(Location location) {
                return location != null;
            }
        });
        locationSubscription = call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        if(dataListener != null) {
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

    public String name() {return this.event.getName(); }
    public String description() {
        return this.event.getDescription();
    }


    public interface DataListener {
        void onLocationChanged(Location location);
    }
}
