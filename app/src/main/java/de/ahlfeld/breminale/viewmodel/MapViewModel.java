package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.ahlfeld.breminale.caches.LocationSources;
import de.ahlfeld.breminale.models.Location;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class MapViewModel implements ViewModel {


    private static final String TAG = MapViewModel.class.getSimpleName();
    private Context context;
    private DataListener dataListener;

    private Subscription subscription;

    private List<Location> locations;

    public MapViewModel(Context context, DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;

        loadLocations();
    }

    private void loadLocations() {
        LocationSources sources = new LocationSources();

        Observable<List<Location>> call = Observable.concat(sources.memory(), sources.network()).first(new Func1<List<Location>, Boolean>() {
            @Override
            public Boolean call(List<Location> locations) {
                return locations != null && !locations.isEmpty();
            }
        });
        subscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Location>>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) {
                            dataListener.onLocationChanged(locations);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading locations", e);
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        Log.d(TAG, "locations loaded size: " + locations.size());
                        MapViewModel.this.locations = locations;
                    }
                });
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void destroy() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }

    public interface DataListener {
        void onLocationChanged(List<Location> locations);
    }
}
