package de.ahlfeld.breminale.core.repositories.realm.specifications;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.ahlfeld.breminale.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by bjornahlfeld on 02.06.16.
 */
public class EventsByDateSpecification implements RealmSpecification<EventRealm> {


    private static final String TAG = EventsByDateSpecification.class.getSimpleName();
    private final Date from, to;

    public EventsByDateSpecification(@NonNull Date from, @NonNull Date to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Observable<RealmResults<EventRealm>> toObservableRealmResults(@NonNull Realm realm) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yy");
        Log.d(TAG,"Load events between " + sdf.format(from) + " and " + sdf.format(to));
        return realm.where(EventRealm.class).equalTo("deleted",false).between("startTime", from, to).findAllSortedAsync("startTime", Sort.ASCENDING).asObservable();
    }

    @Override
    public RealmResults<EventRealm> toRealmResults(Realm realm) {
        return null;
    }

    @Override
    public EventRealm toPlantRealm(Realm realm) {
        return null;
    }
}
