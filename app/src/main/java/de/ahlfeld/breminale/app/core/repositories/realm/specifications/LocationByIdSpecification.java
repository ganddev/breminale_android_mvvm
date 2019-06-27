package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.LocationRealm;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationByIdSpecification implements RealmSpecification {

    private final Integer id;

    public LocationByIdSpecification(Integer id) {
        this.id = id;
    }

    @Override
    public Flowable<RealmResults<LocationRealm>> toFlowableRealmResults(@NonNull Realm realm) {
        return realm.where(LocationRealm.class)
                .equalTo("id", id)
                .findAllAsync()
                .asFlowable();
    }

    @Override
    public RealmResults<LocationRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(LocationRealm.class).equalTo("id", id).findAllAsync();
    }

    @Override
    public LocationRealm toPlantRealm(Realm realm) {
        return null;
    }
}
