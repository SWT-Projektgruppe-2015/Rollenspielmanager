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
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.persist((Ausruestung) entity);
        }
        catch (EntityExistsException addExceptionOne) {
            System.err.println("EntityExistsException: "
                    + addExceptionOne.getMessage());
            addExceptionOne.printStackTrace();
            returnValue =  false;
        }
        catch (TransactionRequiredException addExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + addExceptionTwo.getMessage());
            addExceptionTwo.printStackTrace();
            returnValue =  false;
        }
        catch (IllegalArgumentException addExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + addExceptionThree.getMessage());
            addExceptionThree.printStackTrace();
            returnValue =  false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                commitExceptionOne.printStackTrace();
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                commitExceptionTwo.printStackTrace();
                return false;            
            }
        }
        return returnValue;
    }
    
    
    
    @Override
    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        if(entity == null)  {
            System.err.println("Why!!!!!\n");
        }
        theManager.getTransaction().begin();
        try {
            if(entity == null)  {
                System.err.println("Why!!!!!\n");
            }
            theManager.remove((Ausruestung) entity);
        }
        catch (TransactionRequiredException removeExceptionOne) {
            System.err.println("TransactionRequiredException: "
                    + removeExceptionOne.getMessage());
            removeExceptionOne.printStackTrace();
            returnValue = false;
        }
        catch (IllegalArgumentException removeExceptionTwo) {
            System.err.println("IllegalArgumentException: "
                    + removeExceptionTwo.getMessage());
            removeExceptionTwo.printStackTrace();
            returnValue = false;
        }
        catch (PersistenceException removeExceptionFinal) {
            System.err.println("PersistenceException: "
                    + removeExceptionFinal.getMessage());
            removeExceptionFinal.printStackTrace();
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                commitExceptionOne.printStackTrace();
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                commitExceptionTwo.printStackTrace();
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
