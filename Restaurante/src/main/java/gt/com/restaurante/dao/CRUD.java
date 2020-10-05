package gt.com.restaurante.dao;

import java.util.List;
import java.util.Optional;

public interface CRUD<T> {
    Optional<T> get(long id);
    List<T> getAll();
    void save(T object);
    void update(T object);
    void delete(T object);
}
