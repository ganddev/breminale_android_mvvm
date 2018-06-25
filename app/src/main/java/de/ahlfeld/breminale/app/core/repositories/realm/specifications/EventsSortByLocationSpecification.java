package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import java.util.Date;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class EventsSortByLocationSpecification implements RealmSpecification<EventRealm> {

    private final Date from, to;

    public EventsSortByLocationSpecification(@NonNull Date from, @NonNull Date to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public Flowable<RealmResults<EventRealm>> toFlowableRealmResults(Realm realm) {
        return realm
                .where(EventRealm.class).equalTo("deleted", false)
                .between("startTime", from, to)
                .sort("locationId", Sort.ASCENDING)
                .findAllAsync()
                .asFlowable();
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
