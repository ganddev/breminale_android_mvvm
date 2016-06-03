package de.ahlfeld.breminale.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.repositories.realm.modelRealm.LocationRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by bjornahlfeld on 30.05.16.
 */
public class LocationSpecification implements RealmSpecification {

    @Override
    public Observable<RealmResults<LocationRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(LocationRealm.class).equalTo("deleted", false).findAllAsync().asObservable();
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
