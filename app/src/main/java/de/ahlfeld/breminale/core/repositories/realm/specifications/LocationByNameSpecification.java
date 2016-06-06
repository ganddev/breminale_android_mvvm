package de.ahlfeld.breminale.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.repositories.realm.modelRealm.LocationRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

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
    public Observable<RealmResults<LocationRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(LocationRealm.class).beginsWith("name",query).findAllAsync().asObservable();
    }

    @Override
    public RealmResults<LocationRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }

    @Override
    public RealmObject toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
