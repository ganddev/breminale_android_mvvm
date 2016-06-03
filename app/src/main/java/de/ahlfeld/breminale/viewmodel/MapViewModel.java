package de.ahlfeld.breminale.viewmodel;

import android.content.Context;

import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Location;
import rx.Subscription;

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
       //TODO
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
