package manipulators;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import controller.interfaces.DBManipulator;
import model.Gruppe;
import model.interfaces.DBObject;

public class GruppenManipulator extends DBManipulator {
    private static GruppenManipulator Singleton;    
    
    public static GruppenManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new GruppenManipulator();
        }
        return Singleton;
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



    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((Gruppe) entity);
    }



    @Override
    protected void removeEntity(DBObject entity) {
        theManager.remove((Gruppe) entity);
    }



    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Gruppe) entity);
    }
}
