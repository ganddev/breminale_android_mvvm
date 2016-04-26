package de.ahlfeld.breminale.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.JsonObject;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.networking.BreminaleService;
import de.ahlfeld.breminale.utils.BreminaleConsts;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class GcmRegistrationService extends IntentService {


    private static final String TAG = GcmRegistrationService.class.getSimpleName();
    private Subscription deviceSubscription;

    public GcmRegistrationService() {
        super("GcmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + token);
            sendRegistrationToServer(createDeviceObject(token));
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(BreminaleConsts.SENT_TOKEN_TO_SERVER, false).apply();
            // Notify UI that registration has completed, so the progress indicator can be hidden.
            Intent registrationComplete = new Intent(BreminaleConsts.REGISTRATION_COMPLETE);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }
    }

    @Override
    public void onDestroy() {
        if(deviceSubscription != null && !deviceSubscription.isUnsubscribed()) {
            deviceSubscription.unsubscribe();
        }
        deviceSubscription = null;
        super.onDestroy();
    }

    /**
     * Persist registration to third-party servers.
     * <p>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param device The new device object.
     */
    private void sendRegistrationToServer(JsonObject device) {
        // Add custom implementation, as needed.
        final BreminaleService service = BreminaleService.Factory.create();
        Observable<JsonObject> call = service.postDeviceToken(device);
        deviceSubscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {
                        // You should store a boolean that indicates whether the generated token has been
                        // sent to your server. If the boolean is false, send the token to your server,
                        // otherwise your server should have already received the token.
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GcmRegistrationService.this);
                        sharedPreferences.edit().putBoolean(BreminaleConsts.SENT_TOKEN_TO_SERVER, true).apply();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "An error accured while sending divce object", e);
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Log.i(TAG, jsonObject.toString());
                    }
                });
    }

    private JsonObject createDeviceObject(final String token) {
        JsonObject object = new JsonObject();
        object.addProperty("device_type", BreminaleConsts.DEVICE_TYPE);
        object.addProperty("device_token",token);
        return object;
    }
}
