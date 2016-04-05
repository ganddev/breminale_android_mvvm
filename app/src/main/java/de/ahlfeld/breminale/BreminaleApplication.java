package de.ahlfeld.breminale;

import android.app.Application;
import android.content.Context;

import de.ahlfeld.breminale.models.BreminaleService;
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

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
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
