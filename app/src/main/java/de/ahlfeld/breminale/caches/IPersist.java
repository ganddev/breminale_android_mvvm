package de.ahlfeld.breminale.caches;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public interface IPersist<E extends RealmObject> {

    void persistObject(E object);

    void persistObjects(List<E> objects);
}
