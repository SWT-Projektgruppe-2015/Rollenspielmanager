package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Ausruestung;
import model.Beute;
import model.Gruppe;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class GruppenManipulator {
    private static GruppenManipulator Singleton;
    private static EntityManager theManager;
    
    private GruppenManipulator() {
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    
    public static GruppenManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new GruppenManipulator();
        }
        return Singleton;
    }

    
    
    public boolean add(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.persist((Gruppe) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: in add from GruppenManipulator "
                    + persistExceptionOne.getMessage());
            returnValue = false;
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: in add from GruppenManipulator "
                    + persistExceptionTwo.getMessage());
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: in add from GruppenManipulator "
                    + persistExceptionThree.getMessage());
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: in add from GruppenManipulator "
                    + persistExceptionFinal.getMessage());
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: in add from GruppenManipulator "
                        + commitExceptionOne.getMessage());
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: in add from GruppenManipulator "
                        + commitExceptionTwo.getMessage());
                return false;
            }
        }
        return returnValue;
    }
    

    
    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.remove((Gruppe) entity);
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
        catch (PersistenceException persistExceptionFour) {
            System.err.println("PersistenceException: "
                    + persistExceptionFour.getMessage());
            returnValue = false;
        }
        catch (NullPointerException persistExceptionFive) {
            System.err.println("NullPointerException: "
                    + persistExceptionFive.getMessage());
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
    
    public boolean update(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.merge((Gruppe) entity);
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
    
    public List<Gruppe> getAll() {
        
        TypedQuery<Gruppe> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Gruppe",
                Gruppe.class);
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
}
