package techmaster.lesson8.repository;

import java.util.List;
public interface Dao <T>{
    
    List<T> list();

    List<T> list(Integer limit, Integer offset);

    T getID(Long id);

    void update(Long id, T object);

    void add(T object);

    void delete(Long id);

}
