package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class EventSpecification implements RealmSpecification {

    @Override
    public Flowable<RealmResults<EventRealm>> toFlowableRealmResults(@NonNull Realm realm) {
        return realm
                .where(EventRealm.class)
                .equalTo("deleted", false)
                .sort("startTime", Sort.ASCENDING )
                .findAllAsync()
                .asFlowable();
    }

    @Override
    public RealmResults<EventRealm> toRealmResults(@NonNull Realm realm) {
        return realm
                .where(EventRealm.class)
                .equalTo("deleted", false)
                .sort("startTime", Sort.ASCENDING)
                .findAllAsync();
    }

    @Override
    public EventRealm toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
