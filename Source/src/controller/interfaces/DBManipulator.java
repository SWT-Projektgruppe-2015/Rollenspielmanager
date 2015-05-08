package controller.interfaces;

import model.interfaces.DBObject;

public interface DBManipulator {
    public boolean add(DBObject entity);

    public boolean delete(DBObject entity);

    public boolean update(DBObject entity);
}
