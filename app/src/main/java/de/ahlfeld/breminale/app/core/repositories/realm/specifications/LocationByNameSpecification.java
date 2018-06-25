package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.LocationRealm;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bjornahlfeld on 06.06.16.
 */
public class LocationByNameSpecification implements RealmSpecification {

    private String query;
    public LocationByNameSpecification(@NonNull String query) {
        super();
        this.query = query;
    }

    @Override
    public Flowable<RealmResults<LocationRealm>> toFlowableRealmResults(@NonNull Realm realm) {
        return realm
                .where(LocationRealm.class)
                .beginsWith("name",query)
                .findAllAsync()
                .asFlowable();
    }

    @Override
    public RealmResults<LocationRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }

    @Override
    public LocationRealm toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
