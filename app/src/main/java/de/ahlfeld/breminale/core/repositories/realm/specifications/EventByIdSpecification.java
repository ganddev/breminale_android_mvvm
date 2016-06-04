package de.ahlfeld.breminale.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public class EventByIdSpecification implements RealmSpecification {

    private final Integer id;

    public EventByIdSpecification(Integer id) {
        this.id = id;
    }


    @Override
    public Observable<RealmResults<EventRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(EventRealm.class)
                .equalTo("id", id)
                .findAllAsync().asObservable();
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
