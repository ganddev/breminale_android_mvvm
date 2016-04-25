package de.ahlfeld.breminale.services;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by bjornahlfeld on 25.04.16.
 */
public class BreminaleGcmListenerService extends GcmListenerService {
    private static final String TAG = BreminaleGcmListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        //TODO handle downstream messages


    }
}
