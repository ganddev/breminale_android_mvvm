package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventsByDateSpecification;
import rx.Subscription;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class EventListViewModel implements ViewModel {


    private static final String TAG = EventListViewModel.class.getSimpleName();
    public ObservableInt progressVisibility;
    public ObservableInt recyclerViewVisibility;
    private DataListener dataListener;
    private Context context;
    private Subscription subscription;

    public EventListViewModel(Context context, DataListener dataListener, @NonNull Date from, @NonNull Date to) {
        this.context = context;
        this.dataListener = dataListener;
        progressVisibility = new ObservableInt(View.VISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        loadEvents(from, to);
    }

    private void loadEvents(@NonNull Date from,@NonNull Date to) {
        EventRealmRepository repository = new EventRealmRepository(context);
        EventsByDateSpecification specification = new EventsByDateSpecification(from, to);
        subscription = repository.query(specification).subscribe(eventsFromDB -> dataListener.onEventsChanged(eventsFromDB));
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
        context = null;
        dataListener = null;
    }


    public interface DataListener {
        void onEventsChanged(@NonNull List<Event> events);
    }
}
