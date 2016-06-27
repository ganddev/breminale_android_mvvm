package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import java.util.Date;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class EventsSortByNameSpecification implements RealmSpecification<EventRealm> {

    private final Date from, to;

    public EventsSortByNameSpecification(@NonNull Date from, @NonNull Date to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Observable<RealmResults<EventRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(EventRealm.class).equalTo("deleted",false).between("startTime", from, to).findAllSortedAsync("name", Sort.ASCENDING).asObservable();
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
