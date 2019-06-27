package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public class EventByIdSpecification implements RealmSpecification {

    private final Integer id;

    public EventByIdSpecification(Integer id) {
        this.id = id;
    }


    @Override
    public Flowable<RealmResults<EventRealm>> toFlowableRealmResults(@NonNull Realm realm) {
        return realm.where(EventRealm.class)
                .equalTo("id", id)
                .findAllAsync()
                .asFlowable();
    }

    @Override
    public RealmResults<EventRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }

    @Override
    public EventRealm toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
