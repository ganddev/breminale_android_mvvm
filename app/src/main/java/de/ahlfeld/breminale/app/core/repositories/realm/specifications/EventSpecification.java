package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventSpecification implements RealmSpecification {

    @Override
    public Observable<RealmResults<EventRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(EventRealm.class).equalTo("deleted", false).findAllSortedAsync("startTime", Sort.ASCENDING ).asObservable();
    }

    @Override
    public RealmResults<EventRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(EventRealm.class).equalTo("deleted", false).findAllSortedAsync("startTime", Sort.ASCENDING);
    }

    @Override
    public EventRealm toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
