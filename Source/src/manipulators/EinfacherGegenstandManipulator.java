package manipulators;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.EinfacherGegenstand;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class EinfacherGegenstandManipulator extends DBManipulator{
    private static EinfacherGegenstandManipulator singleton;

    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((EinfacherGegenstand) entity);
    }

    
    
    @Override
    protected void removeEntity(DBObject entity) {
        theManager.remove((EinfacherGegenstand) entity);
        
    }

    
    
    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((EinfacherGegenstand) entity);       
    }

    
    
    public static EinfacherGegenstandManipulator getInstance() {
        if(singleton == null)
            singleton = new EinfacherGegenstandManipulator();
        
        return singleton;
    }



    public List<EinfacherGegenstand> getAll() {
        TypedQuery<EinfacherGegenstand> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM EinfacherGegenstand",
                EinfacherGegenstand.class);
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
