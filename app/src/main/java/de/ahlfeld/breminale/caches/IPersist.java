package de.ahlfeld.breminale.caches;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by bjornahlfeld on 07.04.16.
 */
public interface IPersist<E extends RealmObject> {

    E persistObject(E object);

    List<E> persistObjects(List<E> objects);
}
