package de.ahlfeld.breminale.app.core.repositories;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public interface Mapper<From, To> {
    To map(From from);
    To copy(From from, To to);
}
