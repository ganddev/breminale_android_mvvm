package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.LocationRealm;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationSpecification implements RealmSpecification {

    @Override
    public Flowable<RealmResults<LocationRealm>> toFlowableRealmResults(@NonNull Realm realm) {
        return realm
                .where(LocationRealm.class)
                .equalTo("deleted", false)
                .findAllAsync()
                .asFlowable();
    }

    @Override
    public RealmResults<LocationRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(LocationRealm.class).equalTo("deleted", false).findAllAsync();
    }

    @Override
    public RealmObject toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
