package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventByFavoritSpecification;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventsByDateSpecification;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class EventListViewModel implements ViewModel {


    private static final String TAG = EventListViewModel.class.getSimpleName();
    private Subscription favoritSubscription;
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    private DataListener dataListener;
    private Context context;
    private List<Event> events;
    private Subscription subscription;

    public EventListViewModel(Context context, DataListener dataListener, Date from, Date to, boolean loadFavorits) {
        this.context = context;
        this.dataListener = dataListener;
        progressVisibility = new ObservableInt(View.VISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        if(loadFavorits) {
            loadFavorits();
        } else {
            loadEvents(from, to);
        }
    }

    private void loadFavorits() {
        EventRealmRepository repository = new EventRealmRepository(context);
        EventByFavoritSpecification specification = new EventByFavoritSpecification();
        favoritSubscription = repository.query(specification).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
                if(dataListener != null) {
                    dataListener.onEventsChanged(EventListViewModel.this.events);
                }
                progressVisibility.set(View.INVISIBLE);
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
                EventListViewModel.this.events = events;
            }
        });
    }

    private void loadEvents(@NonNull Date from,@NonNull Date to) {
        EventRealmRepository repository = new EventRealmRepository(context);
        EventsByDateSpecification specification = new EventsByDateSpecification(from, to);
        subscription = repository.query(specification).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
                if(dataListener != null) {
                    dataListener.onEventsChanged(EventListViewModel.this.events);
                }
                progressVisibility.set(View.INVISIBLE);
                if (!events.isEmpty()) {
                    recyclerViewVisibility.set(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error loading events", e);
            }

            @Override
            public void onNext(List<Event> events) {
                EventListViewModel.this.events = events;
            }
        });
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }


    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if(favoritSubscription != null && !favoritSubscription.isUnsubscribed()) {
            favoritSubscription.unsubscribe();
        }
        favoritSubscription = null;
        subscription = null;
        context = null;
        dataListener = null;
    }


    public interface DataListener {
        void onEventsChanged(List<Event> events);
    }
}
