package de.ahlfeld.breminale;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import de.ahlfeld.breminale.caches.FontCache;
import de.ahlfeld.breminale.core.DataManager;
import de.ahlfeld.breminale.networking.BreminaleService;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Scheduler;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class BreminaleApplication extends Application {

    private BreminaleService breminaleService;
    private Scheduler defaultSubscribeScheduler;

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        context = getApplicationContext();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .build())
                        .build());

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
}
