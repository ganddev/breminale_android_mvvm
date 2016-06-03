package de.ahlfeld.breminale.services;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

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
        //TODO
    }

    private void loadEventRessource(int eventId) {
        //TODO
    }
}
