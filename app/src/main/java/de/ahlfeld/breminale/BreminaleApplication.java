package de.ahlfeld.breminale;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import de.ahlfeld.breminale.caches.FontCache;
import de.ahlfeld.breminale.networking.BreminaleService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Scheduler;
import rx.schedulers.Schedulers;

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

    }

    public static BreminaleApplication get(Context ctx) {
        return (BreminaleApplication) ctx.getApplicationContext();
    }

    public BreminaleService getBreminaleService() {
        if(breminaleService == null) {
            breminaleService = BreminaleService.Factory.create();
        }
        return breminaleService;
    }

    public void setBreminaleService(BreminaleService breminaleService) {
        this.breminaleService = breminaleService;
    }

    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        defaultSubscribeScheduler = scheduler;
    }

    public Scheduler getDefaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }
}
