package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.ahlfeld.breminale.caches.EventSources;
import de.ahlfeld.breminale.models.Event;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class EventListViewModel implements ViewModel {


    private static final String TAG = EventListViewModel.class.getSimpleName();
    private ObservableInt progressVisibility;
    private ObservableInt recyclerViewVisibility;
    private DataListener dataListener;
    private final Context context;
    private List<Event> events;
    private Subscription subscription;

    public EventListViewModel(Context context, DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;
        progressVisibility = new ObservableInt(View.VISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        loadEvents();
    }

    private void loadEvents() {
        EventSources sourcess = new EventSources();
        Observable<List<Event>> call = Observable.concat(sourcess.memory(),sourcess.network()).first(new Func1<List<Event>, Boolean>() {
            @Override
            public Boolean call(List<Event> events) {
                return events != null && !events.isEmpty();
            }
        });
        subscription = call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Event>>() {
                    @Override
                    public void onCompleted() {
                        if (dataListener != null) {
                            dataListener.onEventsChanged(events);
                        }
                        progressVisibility.set(View.INVISIBLE);
                        if (!events.isEmpty()) {
                            recyclerViewVisibility.set(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Event> events) {
                        Log.i(TAG, "Events loaded " + events.size());
                        EventListViewModel.this.events = events;
                    }
                });
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }




    @Override
    public void destroy() {

    }


    public interface DataListener {
        void onEventsChanged(List<Event> events);
    }
}
