package de.ahlfeld.breminale.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import rx.Subscription;


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
        //TODO
    }

    private void syncLocations() {
       //TODO
    }

}
