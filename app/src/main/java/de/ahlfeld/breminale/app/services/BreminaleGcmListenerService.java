package de.ahlfeld.breminale.app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.core.DataManager;
import de.ahlfeld.breminale.app.view.MainActivity;

/**
 * Created by bjornahlfeld on 25.04.16.
 */
public class BreminaleGcmListenerService extends GcmListenerService {
    private static final String TAG = BreminaleGcmListenerService.class.getSimpleName();
    private static final String RESSOURCE_TYPE = "ressource_type";
    private static final String RESSOURCE_TYPE_EVENT = "event";
    private static final String IDENTIFIER = "identifier";
    private static final String RESSOURCE_TYPE_LOCATION = "locations";
    private static final String MESSAGE = "message";
    private static final String RESSOURCE_TYPE_MESSAGE = "message";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (data != null) {
            Log.d(TAG, data.toString());

            if (data.containsKey(RESSOURCE_TYPE)) {
                if (data.getString(RESSOURCE_TYPE).equals(RESSOURCE_TYPE_EVENT) && data.containsKey(IDENTIFIER)) {
                    try {
                        loadEventRessource(Integer.parseInt(data.getString(IDENTIFIER)));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else if (data.getString(RESSOURCE_TYPE).equals(RESSOURCE_TYPE_LOCATION) && data.containsKey(IDENTIFIER)) {
                    try {
                        loadLocationRessource(Integer.parseInt(data.getString(IDENTIFIER)));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else if (data.getString(RESSOURCE_TYPE).equals(RESSOURCE_TYPE_MESSAGE) && data.containsKey(MESSAGE)) {
                    showNotification(data.getString(MESSAGE));
                }
            }
        }
    }

    private void showNotification(@NonNull String message) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.zelt)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message));

        int mNotificationId = 001;

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void loadLocationRessource(int locationId) {
        DataManager manager = new DataManager(getApplicationContext());
        manager.loadLocationById(locationId);
    }

    private void loadEventRessource(int eventId) {
        DataManager manager = new DataManager(getApplicationContext());
        manager.loadEventById(eventId);
    }
}
