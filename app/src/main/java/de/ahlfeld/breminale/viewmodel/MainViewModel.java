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
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class MainViewModel implements ViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;

    private Context context;
    private Subscription subscription;

    private List<Location> locations;
    private DataListener dataListener;

    public MainViewModel(Context ctx, DataListener dataListener) {
        this.context = ctx;
        this.dataListener = dataListener;
        progressVisibility = new ObservableInt(View.VISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        loadLocations();
    }


    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
        context = null;
    }

    private void loadLocations() {
        progressVisibility.set(View.VISIBLE);
        recyclerViewVisibility.set(View.INVISIBLE);
        if(subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        BreminaleApplication application = BreminaleApplication.get(context);
        BreminaleService breminaleService = application.getBreminaleService();
        subscription = breminaleService.getLocations().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.getDefaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Location>>() {
                    @Override
                    public void onCompleted() {
                        if(dataListener != null) {
                            dataListener.onLocationsChanged(locations);
                        }
                        progressVisibility.set(View.INVISIBLE);
                        if(!locations.isEmpty()) {
                            recyclerViewVisibility.set(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error loading locations", e);
                        progressVisibility.set(View.INVISIBLE);
                        if(isHttp404(e)) {

                        } else {

                        }
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        Log.i(TAG, "Locations loaded " + locations);
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
