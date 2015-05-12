package manipulators;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.SimpleSalesItem;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class SimpleSalesItemManipulator extends DBManipulator{
    private static SimpleSalesItemManipulator singleton;

    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((SimpleSalesItem) entity);
    }

    
    
    @Override
    protected void removeEntity(DBObject entity) {
        theManager.remove((SimpleSalesItem) entity);
        
    }

    
    
    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((SimpleSalesItem) entity);       
    }

    
    
    public static SimpleSalesItemManipulator getInstance() {
        if(singleton == null)
            singleton = new SimpleSalesItemManipulator();
        
        return singleton;
    }



    public List<SimpleSalesItem> getAll() {
        TypedQuery<SimpleSalesItem> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM SimpleSalesItem",
                SimpleSalesItem.class);
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
