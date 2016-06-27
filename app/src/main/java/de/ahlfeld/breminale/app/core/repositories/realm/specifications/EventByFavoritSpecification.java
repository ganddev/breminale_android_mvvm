package de.ahlfeld.breminale.app.core.repositories.realm.specifications;

import android.support.annotation.NonNull;

import de.ahlfeld.breminale.app.core.repositories.realm.modelRealm.EventRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by bjornahlfeld on 30.05.16.
 * Class to get all favorit events from the user. Sorted by startDate asc
 */
public class EventByFavoritSpecification implements RealmSpecification {

    private final boolean isFavorit;
    private final boolean isDeleted;

    public EventByFavoritSpecification() {
        this.isFavorit = true;
        this.isDeleted = false;
    }
    @Override
    public Observable<RealmResults<EventRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(EventRealm.class).equalTo("favorit",this.isFavorit).equalTo("deleted", this.isDeleted).findAllSortedAsync("startTime", Sort.ASCENDING).asObservable();
    }

    @Override
    public RealmResults toRealmResults(@NonNull Realm realm) {
        return null;
    }

    @Override
    public RealmObject toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
