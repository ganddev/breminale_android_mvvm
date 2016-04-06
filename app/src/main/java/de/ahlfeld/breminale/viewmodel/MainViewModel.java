package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Location;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private DataListener dataListener;
    private Subscription subscription;
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    private List<Location> locations;

    public MainViewModel(Context ctx, DataListener dataListener) {
        this.dataListener = dataListener;
        progressVisibility = new ObservableInt(View.VISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        loadLocations();
    }


    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }


    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
        dataListener = null;
    }

    private void loadLocations() {
        LocationSources sources = new LocationSources();
        Observable<List<Location>> call = Observable.concat(sources.network(),sources.memory()).first();
        subscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Location>>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) dataListener.onLocationsChanged(locations);
                        progressVisibility.set(View.INVISIBLE);
                        if (!locations.isEmpty()) {
                            recyclerViewVisibility.set(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading locations", e);
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        Log.i(TAG, "Locations loaded " + locations.size());
                        MainViewModel.this.locations = locations;
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
