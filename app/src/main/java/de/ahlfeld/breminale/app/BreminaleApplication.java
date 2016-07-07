package de.ahlfeld.breminale.app;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import de.ahlfeld.breminale.app.caches.FontCache;
import de.ahlfeld.breminale.app.core.DataManager;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class BreminaleApplication extends Application {

    private Tracker tracker;


    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        BreminaleApplication application = (BreminaleApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        context = getApplicationContext();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        FontCache.getInstance().addFont("roboto-regular", "Roboto-Regular.ttf");
        FontCache.getInstance().addFont("roboto-bold", "Roboto-Bold.ttf");
        FontCache.getInstance().addFont("roboto-light", "Roboto-Light.ttf");
        FontCache.getInstance().addFont("roboto-medium", "Roboto-Medium.ttf");
        FontCache.getInstance().addFont("georgia", "Georgia.ttf");

        DataManager dataManager = new DataManager(this);
        if(dataManager.shouldLoadData()) {
           dataManager.loadLocations();
           dataManager.loadEvents();
        }
    }

    public static BreminaleApplication get(Context ctx) {
        return (BreminaleApplication) ctx.getApplicationContext();
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }
}
