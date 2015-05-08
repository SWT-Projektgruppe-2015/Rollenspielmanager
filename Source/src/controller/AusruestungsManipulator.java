package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import model.Ausruestung;
import model.Spieler;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class AusruestungsManipulator implements DBManipulator {
    private static EntityManager theManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
    private static AusruestungsManipulator singelton;
    
    private AusruestungsManipulator() {}
    
    @Override
    public boolean add(DBObject entity) {
        theManager.getTransaction().begin();
        try {
            theManager.persist((Ausruestung) entity);
        }
        catch (EntityExistsException addExceptionOne) {
            System.err.println("EntityExistsException: "
                    + addExceptionOne.getMessage());
            theManager.getTransaction().commit();
            return false;
        }
        catch (TransactionRequiredException addExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + addExceptionTwo.getMessage());
            return false;
        }
        catch (IllegalArgumentException addExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + addExceptionThree.getMessage());
            return false;
        }
        
        theManager.getTransaction().commit();
        return true;
    }
    
    @Override
    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.remove((Ausruestung) entity);
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
            returnValue = false;
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
        
        return returnValue;
    }
    
    @Override
    public boolean update(DBObject entity) {
        // TODO Auto-generated method stub
        return false;
    }
    
    public List<DBObject> getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static AusruestungsManipulator getInstance() {
        if (singelton == null) {
            singelton = new AusruestungsManipulator();
        }
        return singelton;
    }
    
}
