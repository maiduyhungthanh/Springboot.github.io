package techmaster.lesson8.service;

import java.util.List;

public interface iService<T> {
    List<T> getList();

    List<T> getList(Integer limit, Integer offset);

    T getByID(Long id);

    void update(Long id, T object);

    void add(T object);

    void delete(Long id);
}