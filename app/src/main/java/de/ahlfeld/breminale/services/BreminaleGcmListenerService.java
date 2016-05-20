package de.ahlfeld.breminale.services;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import de.ahlfeld.breminale.caches.EventSources;
import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.models.Location;
import rx.Subscriber;
import rx.observables.BlockingObservable;

/**
 * Created by bjornahlfeld on 25.04.16.
 */
public class BreminaleGcmListenerService extends GcmListenerService {
    private static final String TAG = BreminaleGcmListenerService.class.getSimpleName();
    private static final String RESSOURCE_TYPE = "ressource_type";
    private static final String RESSOURCE_TYPE_EVENT = "event";
    private static final String IDENTIFIER = "identifier";
    private static final String RESSOURCE_TYPE_LOCATION = "locations";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if(data != null) {
            Log.d(TAG, data.toString());

            if(data.containsKey(RESSOURCE_TYPE)) {
                if(data.getString(RESSOURCE_TYPE).equals(RESSOURCE_TYPE_EVENT) && data.containsKey(IDENTIFIER)) {
                    try {
                        loadEventRessource(Integer.parseInt(data.getString(IDENTIFIER)));
                    }catch (NumberFormatException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else if (data.getString(RESSOURCE_TYPE).equals(RESSOURCE_TYPE_LOCATION) && data.containsKey(IDENTIFIER)) {
                    try {
                        loadLocationRessource(Integer.parseInt(data.getString(IDENTIFIER)));
                    }catch (NumberFormatException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

    private void loadLocationRessource(int locationId) {
        LocationSources sources = new LocationSources();
        BlockingObservable<Location> call = sources.network(locationId).toBlocking();
    }

    private void loadEventRessource(int eventId) {
        EventSources sources = new EventSources();
        BlockingObservable<Event> call = sources.network(eventId).toBlocking();
        call.subscribe(new Subscriber<Event>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error occured while updating ressource", e);
            }

            @Override
            public void onNext(Event event) {
                Log.e(TAG, event.toString());
            }
        });
    }
}
