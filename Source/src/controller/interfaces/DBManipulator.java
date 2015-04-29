package controller.interfaces;

import java.util.List;

import model.Spieler;
import model.interfaces.DBObject;

public interface DBManipulator {
	public boolean add(DBObject entity);
	public boolean delete(DBObject entity);
	public boolean update(DBObject entity);
	public List<DBObject> getAll();
}
