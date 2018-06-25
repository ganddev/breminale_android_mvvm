package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import de.ahlfeld.breminale.app.core.repositories.Specification;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public interface RealmSpecification<T extends RealmObject> extends Specification {

    Flowable<RealmResults<T>> toFlowableRealmResults(Realm realm);
    RealmResults<T> toRealmResults(Realm realm);
    T toPlantRealm(Realm realm);
}
