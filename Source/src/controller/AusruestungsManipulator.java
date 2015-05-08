package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockTimeoutException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

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
            System.err.println("IllegalArgumentException in add in Ausruestungsmanager: "
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
            System.err.println("IllegalArgumentException in delete in Ausruestungsmanager: "
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
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.merge((Ausruestung) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: "
                    + persistExceptionOne.getMessage());
            returnValue = false;
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
                return false;
                
            }
        }
        return returnValue;
    }
    
    
    
    public List<Ausruestung> getAll() {
        TypedQuery<Ausruestung> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Ausruestung",
                Ausruestung.class);
        }
        catch(IllegalArgumentException createQueryExceptionOne)   {
            System.err.println("IllegalArgumentException: ");
            return null;
        }
        try {
            return getAllRows.getResultList();
        }
        catch(IllegalStateException getResultListExceptionOne)  {
            System.err.println("IllegalStateException: ");
            return null;
        }
        catch(QueryTimeoutException getResultListExceptionTwo)  {
            System.err.println("QueryTimeoutException: ");
            return null;
        }
        catch(TransactionRequiredException getResultListExceptionThree)  {
            System.err.println("TransactionRequiredException: ");
            return null;
        }
        catch(PessimisticLockException getResultListExceptionFour)  {
            System.err.println("PessimisticLockException: ");
            return null;
        }
        catch(LockTimeoutException getResultListExceptionFive)  {
            System.err.println("LockTimeoutException: ");
            return null;
        }
        catch(PersistenceException getResultListExceptionSix)  {
            System.err.println("PersistenceException: ");
            return null;
        }
    }
    
    
    
    public static AusruestungsManipulator getInstance() {
        if (singelton == null) {
            singelton = new AusruestungsManipulator();
        }
        return singelton;
    }
}
