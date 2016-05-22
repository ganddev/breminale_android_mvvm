package de.ahlfeld.breminale.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import de.ahlfeld.breminale.caches.EventSources;
import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.models.Location;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by bjornahlfeld on 27.04.16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {


    private static final String TAG = SyncAdapter.class.getSimpleName();
    private final AccountManager mAccountManager;
    private Subscription locationSubscription;
    private Subscription eventSubscription;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync for account[" + account.name + "]");
        //Load locations...
        syncLocations();
        //Load Events
        syncEvents();
    }

    private void syncEvents() {
        EventSources eventSources = new EventSources();
        Observable<List<Event>> eventCall = eventSources.network();
        eventSubscription = eventCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Event>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error synchronizing events", e);
                    }

                    @Override
                    public void onNext(List<Event> events) {

                    }
                });
    }

    private void syncLocations() {
        LocationSources locationSources = new LocationSources();
        Observable<List<Location>> locationsCall = locationSources.network();
        locationSubscription = locationsCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Location>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error synchronize locations", e);
                    }

                    @Override
                    public void onNext(List<Location> locations) {

                    }
                });
    }

}
