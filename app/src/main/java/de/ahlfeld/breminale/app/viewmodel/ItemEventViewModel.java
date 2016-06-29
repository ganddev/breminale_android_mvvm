package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.app.core.repositories.realm.LocationRealmRepository;
import de.ahlfeld.breminale.app.view.EventActivity;
import rx.Subscription;

/**
 * Created by bjornahlfeld on 04.04.16.
 */
public class ItemEventViewModel extends BaseObservable implements ViewModel {

    private Context context;
    private Event event;

    private Subscription locationSubscription;
    public ObservableField<String> locationName;
    private Tracker tracker;

    public ItemEventViewModel(Context context, Event event) {
        this.context = context;
        this.event = event;
        locationName = new ObservableField<>("No location");
        getLocationName();
        tracker = ((BreminaleApplication)context.getApplicationContext()).getDefaultTracker();
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
        LocationRealmRepository realmRepository = new LocationRealmRepository(context);
        locationSubscription = realmRepository.getById(event.getLocationId()).subscribe(locationFromDB -> locationName.set(locationFromDB.getName()));
    }

    public void onItemClick(View view) {
        tracker.send(new HitBuilders.EventBuilder().setCategory("eventItem").setAction("onItemClick").build());
        context.startActivity(EventActivity.newIntent(context, event));
    }

    public void onFavoritClick(View view) {
        tracker.send(new HitBuilders.EventBuilder().setCategory("eventItem").setAction("onFavoritClick").build());
        EventRealmRepository repository = new EventRealmRepository(context);
        event.setFavorit(!event.isFavorit());
        repository.saveEventAsFavorit(event);
        notifyChange();
    }

    public void setEvent(Event event) {
        this.event = event;
        this.getLocationName();
        notifyChange();
    }

    public boolean isFavorit() {
        return this.event.isFavorit();
    }
}
