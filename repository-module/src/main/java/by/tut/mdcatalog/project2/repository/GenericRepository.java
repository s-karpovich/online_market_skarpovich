package by.tut.mdcatalog.project2.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<I, T> {

    Connection getConnection();

    void persist(T entity);

    void merge(T entity);

    void remove(T entity);

    T getById(I id);

    @SuppressWarnings({"unchecked", "rawtypes"})
    //List<T> getAll(int offset, int limit);
    List<T> getAll();
    int getCountOfEntities();
}
