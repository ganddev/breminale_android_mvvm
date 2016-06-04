package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.List;

import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.repositories.realm.EventRealmRepository;
import de.ahlfeld.breminale.core.repositories.realm.specifications.EventByFavoritSpecification;
import rx.Subscription;

/**
 * Created by bjornahlfeld on 03.06.16.
 */
public class FavoritsListViewModel implements ViewModel {

    private static final String TAG = FavoritsListViewModel.class.getSimpleName();
    private final Navigation navigationListener;
    private Context context;
    private Subscription favoritSubscription;
    private DataListener dataListener;
    public ObservableInt recyclerViewVisibility;

    public ObservableInt emptyViewVisibility;

    public FavoritsListViewModel(@NonNull Context context,@NonNull DataListener dataListener, @NonNull Navigation navigationListener) {
        this.dataListener = dataListener;
        this.navigationListener = navigationListener;
        this.context = context;
        emptyViewVisibility = new ObservableInt(View.VISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        loadFavorits();

    }

    private void loadFavorits() {
        EventRealmRepository repository = new EventRealmRepository(context);
        EventByFavoritSpecification specification = new EventByFavoritSpecification();
        favoritSubscription = repository.query(specification).subscribe(favoritEventsFromDB -> {
            if(favoritEventsFromDB != null && !favoritEventsFromDB.isEmpty()) {
                emptyViewVisibility.set(View.GONE);
                recyclerViewVisibility.set(View.VISIBLE);
                dataListener.onFavoritsChanged(favoritEventsFromDB);
            } else {
                emptyViewVisibility.set(View.VISIBLE);
                recyclerViewVisibility.set(View.INVISIBLE);
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

    public void toProgram(View view) {
        Log.d(TAG, "toProgramClick");
        if(navigationListener != null) {
            navigationListener.onProgramClick();
        }
    }

    public interface DataListener {
        void onFavoritsChanged(List<Event> events);
    }

    public interface Navigation{
        void onProgramClick();
    }
}
