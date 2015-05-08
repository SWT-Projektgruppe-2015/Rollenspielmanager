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
            persistExceptionOne.printStackTrace();
            returnValue = false;
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            persistExceptionTwo.printStackTrace();
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            persistExceptionThree.printStackTrace();
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
            persistExceptionFinal.printStackTrace();
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
    
    
    
    public List<Ausruestung> getAll() {
        TypedQuery<Ausruestung> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Ausruestung",
                Ausruestung.class);
        }
        catch(IllegalArgumentException createQueryExceptionOne)   {
            System.err.println("IllegalArgumentException: ");
            createQueryExceptionOne.printStackTrace();
            return null;
        }
        try {
            return getAllRows.getResultList();
        }
        catch(IllegalStateException getResultListExceptionOne)  {
            System.err.println("IllegalStateException: ");
            getResultListExceptionOne.printStackTrace();
            return null;
        }
        catch(QueryTimeoutException getResultListExceptionTwo)  {
            System.err.println("QueryTimeoutException: ");
            getResultListExceptionTwo.printStackTrace();
            return null;
        }
        catch(TransactionRequiredException getResultListExceptionThree)  {
            System.err.println("TransactionRequiredException: ");
            getResultListExceptionThree.printStackTrace();
            return null;
        }
        catch(PessimisticLockException getResultListExceptionFour)  {
            System.err.println("PessimisticLockException: ");
            getResultListExceptionFour.printStackTrace();
            return null;
        }
        catch(LockTimeoutException getResultListExceptionFive)  {
            System.err.println("LockTimeoutException: ");
            getResultListExceptionFive.printStackTrace();
            return null;
        }
        catch(PersistenceException getResultListExceptionSix)  {
            System.err.println("PersistenceException: ");
            getResultListExceptionSix.printStackTrace();
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
