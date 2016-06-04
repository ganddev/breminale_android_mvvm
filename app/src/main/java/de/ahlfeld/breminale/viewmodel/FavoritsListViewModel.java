package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventByFavoritSpecification;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bjornahlfeld on 03.06.16.
 */
public class FavoritsListViewModel implements ViewModel {

    private static final String TAG = FavoritsListViewModel.class.getSimpleName();
    private Context context;
    private Subscription favoritSubscription;
    private List<Event> events;
    private DataListener dataListener;
    public ObservableInt recyclerViewVisibility;

    public FavoritsListViewModel(Context context, DataListener dataListener) {
        this.dataListener = dataListener;
        this.context = context;
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        loadFavorits();

    }

    private void loadFavorits() {
        EventRealmRepository repository = new EventRealmRepository(context);
        EventByFavoritSpecification specification = new EventByFavoritSpecification();
        favoritSubscription = repository.query(specification).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
                if(dataListener != null) {
                    dataListener.onFavoritsChanged(FavoritsListViewModel.this.events);
                }
                if (!events.isEmpty()) {
                    recyclerViewVisibility.set(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading favorits", e);
            }

            @Override
            public void onNext(List<Event> events) {
                FavoritsListViewModel.this.events = events;
            }
        });
    }

    @Override
    public void destroy() {
        if(favoritSubscription != null && !favoritSubscription.isUnsubscribed()) {
            favoritSubscription.unsubscribe();
        }
        favoritSubscription = null;
        context = null;
        dataListener = null;
    }

    public interface DataListener {
        void onFavoritsChanged(List<Event> events);
    }
}
