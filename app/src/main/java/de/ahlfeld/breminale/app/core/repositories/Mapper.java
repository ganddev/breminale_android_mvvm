package de.ahlfeld.breminale.app.core.repositories;

import io.realm.Realm;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public interface Mapper<From, To> {
    To map(Realm realm, From from);
    To copy(From from, To to);
}
