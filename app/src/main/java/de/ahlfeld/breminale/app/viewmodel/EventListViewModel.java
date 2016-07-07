package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableInt;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.app.core.SortOrderManager;
import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.app.core.repositories.realm.SortOptions;
import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.EventsByDateSpecification;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.EventsSortByLocationSpecification;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.EventsSortByNameSpecification;
import de.ahlfeld.breminale.app.core.repositories.realm.specifications.RealmSpecification;
import rx.Subscription;


/**
 * Created by bjornahlfeld on 07.04.16.
 */
public class EventListViewModel implements ViewModel, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = EventListViewModel.class.getSimpleName();
    private final Date from;
    private final Date to;
    public ObservableInt recyclerViewVisibility;
    private DataListener dataListener;
    private WeakReference<Context> context;
    private Subscription subscription;

    public EventListViewModel(Context context, DataListener dataListener, @NonNull Date from, @NonNull Date to) {
        this.context = new WeakReference<>(context);
        this.dataListener = dataListener;
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        this.from = from;
        this.to = to;
        loadEvents(from, to);

        registerSharedPreferenceLister(context);
    }

    private void registerSharedPreferenceLister(@NonNull Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this);
    }

    private void loadEvents(@NonNull Date from,@NonNull Date to) {
        EventRealmRepository repository = new EventRealmRepository(context.get().getApplicationContext());
        RealmSpecification<EventRealm> specification = getSpecification(to, from);
        subscription = repository.query(specification).subscribe(eventsFromDB ->
        {
            recyclerViewVisibility.set(View.VISIBLE);
            if(dataListener != null) {
                dataListener.onEventsChanged(eventsFromDB);
            }
        });
    }

    private RealmSpecification<EventRealm> getSpecification(@NonNull Date to, @NonNull Date from) {
        SortOptions sortOption = SortOrderManager.getSelectedSortOrder(context.get().getApplicationContext());
        switch (sortOption) {
            case ALPHABETICALLY:
                return new EventsSortByNameSpecification(from, to);
            case TIME:
                return new EventsByDateSpecification(from,to);
            case LOCATION:
                return new EventsSortByLocationSpecification(from, to);
            default:
                return null;
        }
    }


    @Override
    public void destroy() {
        Log.d(TAG, "destroy");
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        PreferenceManager.getDefaultSharedPreferences(context.get().getApplicationContext()).unregisterOnSharedPreferenceChangeListener(this);
        subscription = null;
        context = null;
        dataListener = null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        loadEvents(from,to);
    }


    public interface DataListener {
        void onEventsChanged(@NonNull List<Event> events);
    }
}
