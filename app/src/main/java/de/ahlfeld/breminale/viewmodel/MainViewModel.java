package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.ahlfeld.breminale.BreminaleApplication;
import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Location;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private Subscription subscription;
    public MainViewModel(Context ctx, DataListener dataListener) {

        loadLocations();
    }


    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }

    private void loadLocations() {
        BreminaleService service = BreminaleService.Factory.create();
        Observable<Location> call = service.getLocation(1);
        subscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "on compledted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading locations", e);
                    }

                    @Override
                    public void onNext(Location location) {
                        Log.i(TAG, "Locations loaded " + location);
                    }
                });
    }


    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

    public interface DataListener {
        void onLocationsChanged(List<Location> locations);
    }
}
