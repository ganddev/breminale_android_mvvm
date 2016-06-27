package de.ahlfeld.breminale.app.core.repositories;

import java.util.List;

import rx.Observable;

/**
 * Created by bjornahlfeld on 28.05.16.
 */
public interface Repository<T> {

    Observable<T> getById(Integer id);

    Observable<String> add(T item);

    Observable<Integer> add(Iterable<T> items);

    Observable<T> update(T item);

    Observable<Integer> remove(T item);

    Observable<Integer> remove(Specification specification);

    Observable<List<T>> query(Specification specification);

}
