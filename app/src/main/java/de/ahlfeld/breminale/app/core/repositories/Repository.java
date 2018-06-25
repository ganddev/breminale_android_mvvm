package de.ahlfeld.breminale.app.core.repositories;

import java.util.List;

import io.reactivex.Flowable;


/**
 * Created by bjornahlfeld on 28.05.16.
 */
public interface Repository<T> {

    Flowable<T> getById(Integer id);

    Flowable<String> add(T item);

    Flowable<Integer> add(Iterable<T> items);

    Flowable<T> update(T item);

    Flowable<Integer> remove(T item);

    Flowable<Integer> remove(Specification specification);

    Flowable<List<T>> query(Specification specification);

}
