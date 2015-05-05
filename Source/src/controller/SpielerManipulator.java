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
    private static EntityManagerFactory factory;
    private static EntityManager theManager;
    
    private SpielerManipulator() {
        factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        theManager = factory.createEntityManager();
    }
    
    public static SpielerManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new SpielerManipulator();
        }
        return Singleton;
    }
    
    public boolean add(DBObject entity) {
        theManager.getTransaction().begin();
        try {
            theManager.persist((Spieler) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: "
                    + persistExceptionOne.getMessage());
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                return false;
                
            }
        }
        return true;
    }
    
    public boolean delete(DBObject entity) {
        theManager.getTransaction().begin();
        try {
            theManager.remove((Spieler) entity);
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                theManager.getTransaction().commit();
                return false;
            }
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
