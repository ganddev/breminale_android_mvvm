package de.ahlfeld.breminale.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.LocationRealmRepository;
import de.ahlfeld.breminale.core.repositories.restAPI.RestEventAPIRepository;
import de.ahlfeld.breminale.core.repositories.restAPI.RestLocationAPIRepository;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 03.06.16.
 */
public class DataManager {


    private static final String TAG = DataManager.class.getSimpleName();
    private static final String LAST_UPDATE = "lastupdate";
    private static final long FIVE_HOURS_IN_MS = 18000000;
    private final Context context;
    private List<Location> locations;
    private List<Event> events;

    public DataManager(@NonNull Context context) {
        this.context = context;
        locations = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Returns true if data was never been loaded or the last update is more then 5 hours ago.
     * @return
     */
    public boolean shouldLoadData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.context.getApplicationContext());
        long lastUpdate = preferences.getLong(LAST_UPDATE, 0);
        Date now = new Date();
        if(lastUpdate <= 0) {
            return true;
        }
        else if(now.getTime() - lastUpdate >= FIVE_HOURS_IN_MS) {
            return  true;
        } else {
            return false;
        }
    }

    public void loadLocations() {
        RestLocationAPIRepository apiRepository = new RestLocationAPIRepository();
        Subscription locationSubscripton = apiRepository.query(null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Location>>() {

            @Override
            public void onCompleted() {
                persistLocations(DataManager.this.locations);
                updateLastUpdate();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading locations from server", e);
            }

            @Override
            public void onNext(List<Location> locations) {
                DataManager.this.locations.clear();
                DataManager.this.locations.addAll(locations);
            }
        });
    }

    private void updateLastUpdate() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LAST_UPDATE, new Date().getTime());
        editor.commit();
    }


    public Subscription loadEvents() {
        RestEventAPIRepository apiRepository = new RestEventAPIRepository();

        Subscription eventSubscription = apiRepository.query(null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
                persistEvents(DataManager.this.events);
                updateLastUpdate();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading events from server", e);
            }

            @Override
            public void onNext(List<Event> events) {
                DataManager.this.events.clear();
                DataManager.this.events.addAll(events);
            }
        });

        return eventSubscription;
    }

    private void persistLocations(List<Location> locations) {
        LocationRealmRepository realmRepository = new LocationRealmRepository(this.context);
        for(Location location : locations) {
            if(location.getDeleted()) {
                realmRepository.remove(location);
            }
            realmRepository.update(location);
        }
    }

    private void persistEvents(List<Event> events) {
        EventRealmRepository realmRepository = new EventRealmRepository(this.context);
        for(Event event : events) {
            if(event.getDeleted()) {
                realmRepository.remove(event);
            }
            realmRepository.update(event);
        }
    }

    public void loadLocationById(int locationId) {
        RestLocationAPIRepository apiRepository = new RestLocationAPIRepository();
        Subscription singleLocationSubscription = apiRepository.getById(locationId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Location>() {
            @Override
            public void onCompleted() {
                persistLocations(DataManager.this.locations);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading single location", e);
            }

            @Override
            public void onNext(Location location) {
                DataManager.this.locations.clear();
                DataManager.this.locations.add(location);
            }
        });
    }

    public void loadEventById(int eventId) {
        RestEventAPIRepository apiRepository = new RestEventAPIRepository();
        Subscription singleEventSubscription = apiRepository.getById(eventId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Event>() {
            @Override
            public void onCompleted() {
                persistEvents(DataManager.this.events);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading single event", e);
            }

            @Override
            public void onNext(Event event) {
                DataManager.this.events.clear();
                DataManager.this.events.add(event);
            }
        });
    }
}
