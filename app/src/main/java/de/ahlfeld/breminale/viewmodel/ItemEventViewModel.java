package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import java.text.SimpleDateFormat;

import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.models.Location;
import de.ahlfeld.breminale.view.EventActivity;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 04.04.16.
 */
public class ItemEventViewModel extends BaseObservable implements ViewModel {


    private Context context;
    private Event event;

    private Subscription locationSubscription;
    public ObservableField<String> locationName;

    public ItemEventViewModel(Context context, Event event) {
        this.context = context;
        this.event = event;
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

    public String getImageUrl() {
        return event.imageUrl();
    }

    public String getName() {
        return event.getName();
    }

    public String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE, d. MMMM / HH:mm ");
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(event.getStartTime()));
        sb.append("Uhr");
        sb.append(" / ");
        return sb.toString();
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

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Location location) {
                        locationName.set(location.getName());
                    }
                });
    }

    public void onItemClick(View view) {
        context.startActivity(EventActivity.newIntent(context, event));
    }


    public void setEvent(Event event) {
        this.event = event;
        notifyChange();
    }
}
