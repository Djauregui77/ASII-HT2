package gt.com.restaurante.dao;

import gt.com.restaurante.model.Orden;

import java.util.List;

public interface OrdenesDao<T> extends CRUD<T> {
    Orden getToReport(String id);
}
