package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

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
		catch(EntityExistsException persistExceptionOne)	{
			System.err.println("EntityExistsException: " + persistExceptionOne.getMessage());
			theManager_.getTransaction().commit();
			return false;
		}
		catch(TransactionRequiredException persistExceptionTwo)	{
			System.err.println("TransactionRequiredException: " + persistExceptionTwo.getMessage());
			theManager_.getTransaction().commit();
			return false;
		}
		catch(IllegalArgumentException persistExceptionThree)	{
			System.err.println("IllegalArgumentException: " + persistExceptionThree.getMessage());
			theManager_.getTransaction().commit();
			return false;
		}
		catch(PersistenceException persistExceptionFinal)	{
			System.err.println("PersistenceException: " + persistExceptionFinal.getMessage());
			theManager_.getTransaction().commit();
			return false;
			
		}
		try	{
			theManager_.getTransaction().commit();
		}
		catch(RollbackException commitExceptionOne)	{
			System.err.println("RollBackException: " + commitExceptionOne.getMessage());
			return false;
		}
		catch(PersistenceException commitExceptionTwo)	{
			System.err.println("PersistenceException: " + commitExceptionTwo.getMessage());
			theManager_.getTransaction().commit();
			return false;
			
		}
		return true;
	}

	
	public boolean delete(DBObject entity) {
		theManager_.getTransaction().begin();
		try {
			theManager_.remove((Spieler)entity);
		}
		catch(TransactionRequiredException persistExceptionTwo)	{
			System.err.println("TransactionRequiredException: " + persistExceptionTwo.getMessage());
			theManager_.getTransaction().commit();
			return false;
		}
		catch(IllegalArgumentException persistExceptionThree)	{
			System.err.println("IllegalArgumentException: " + persistExceptionThree.getMessage());
			theManager_.getTransaction().commit();
			return false;
		}
		catch(PersistenceException persistExceptionFinal)	{
			System.err.println("PersistenceException: " + persistExceptionFinal.getMessage());
			theManager_.getTransaction().commit();
			return false;
			
		}
		try	{
			theManager_.getTransaction().commit();
		}
		catch(RollbackException commitExceptionOne)	{
			System.err.println("RollBackException: " + commitExceptionOne.getMessage());
			return false;
		}
		catch(PersistenceException commitExceptionTwo)	{
			System.err.println("PersistenceException: " + commitExceptionTwo.getMessage());
			theManager_.getTransaction().commit();
			return false;
			
		}
		return true;
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
