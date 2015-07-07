package controller.interfaces;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
        catch (EntityExistsException 
                | TransactionRequiredException 
                | IllegalArgumentException addException) {
            addException.printStackTrace();
            returnValue =  false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (PersistenceException commitException) {
                commitException.printStackTrace();
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
        catch (IllegalArgumentException 
                | PersistenceException removeException) {
            removeException.printStackTrace();
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (PersistenceException commitException) {
                commitException.printStackTrace();
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
        catch (IllegalArgumentException 
                | PersistenceException persistException) {
            persistException.printStackTrace();
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (PersistenceException commitException) {
                commitException.printStackTrace();
                return false;
            }
        }
        return returnValue;
    }
    
    protected abstract void mergeEntity(DBObject entity);
}
