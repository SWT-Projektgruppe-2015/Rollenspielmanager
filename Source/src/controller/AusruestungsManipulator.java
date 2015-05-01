package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Ausruestung;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class AusruestungsManipulator implements DBManipulator {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager theManager = factory.createEntityManager();
	
	@Override
	public boolean add(DBObject entity) {
		theManager.getTransaction().begin();
		theManager.persist((Ausruestung) entity);
		theManager.getTransaction().commit();
		return false;
	}

	@Override
	public boolean delete(DBObject entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(DBObject entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DBObject> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public static AusruestungsManipulator getInstance() {
		return new AusruestungsManipulator();
	}

}
