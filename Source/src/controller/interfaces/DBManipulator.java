package controller.interfaces;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import controller.EntityManagerFactoryProvider;
import model.interfaces.DBObject;

public abstract class DBManipulator {
    protected static EntityManager theManager = EntityManagerFactoryProvider.getFactory().createEntityManager();

    public boolean add(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            persistEntity(entity);
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
    
    protected abstract void persistEntity(DBObject entity);

    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            removeEntity(entity);
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

    protected abstract void removeEntity(DBObject entity);
    
    public boolean update(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            mergeEntity(entity);
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
    
    protected abstract void mergeEntity(DBObject entity);
}
