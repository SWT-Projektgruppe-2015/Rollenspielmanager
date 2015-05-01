package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;

import model.Spieler;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class SpielerManipulator implements DBManipulator {
	private static SpielerManipulator Singleton;
	private EntityManagerFactory factory_;
    private EntityManager theManager_;
	
	private SpielerManipulator()	{
		factory_ = Persistence.createEntityManagerFactory("thePersistenceUnit");
		theManager_ = factory_.createEntityManager();
	}
	
	
	public static SpielerManipulator getInstance()	{
		if(Singleton == null)	{
			Singleton = new SpielerManipulator();
		}
		return Singleton;
	}
	
	
	public  boolean add(DBObject entity) {
		theManager_.getTransaction().begin();
		try {
			theManager_.persist((Spieler)entity);
		}
		catch(EntityExistsException addExceptionOne)	{
			System.err.println("EntityExistsException: " + addExceptionOne.getMessage());
			theManager_.getTransaction().commit();
			return false;
		}
		catch(TransactionRequiredException addExceptionTwo)	{
			System.err.println("TransactionRequiredException: " + addExceptionTwo.getMessage());
			return false;
		}
		catch(IllegalArgumentException addExceptionThree)	{
			System.err.println("IllegalArgumentException: " + addExceptionThree.getMessage());
			return false;
		}
		theManager_.getTransaction().commit();
		return true;
	}

	
	public boolean delete(DBObject entity) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean update(DBObject entity) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public List<DBObject> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
