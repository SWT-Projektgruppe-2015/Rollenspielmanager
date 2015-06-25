package controller.manipulators;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Ausruestung;
import model.Ruestungseffekt;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class RuestungseffektManipulator extends DBManipulator {
    private static RuestungseffektManipulator instance_;
    
    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((Ruestungseffekt) entity);
    }
    
    @Override
    protected void removeEntity(DBObject entity) {
        theManager.remove((Ruestungseffekt) entity);      
    }
    
    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Ruestungseffekt) entity);
    }

    public static RuestungseffektManipulator getInstance() {
        if(instance_ == null) {
            instance_ = new RuestungseffektManipulator();
        }
        return instance_;
    }

    public List<Ruestungseffekt> getRuestungsEffekteForAusruestung(Ausruestung ausruestung) {
        TypedQuery<Ruestungseffekt> effekteForAusruestung;
        try {
            effekteForAusruestung = theManager.createQuery(
                "FROM Ruestungseffekt w WHERE w.ausruestung_ = " + ausruestung.getID_(), Ruestungseffekt.class);
        }
        catch(IllegalArgumentException createQueryExceptionOne)   {
            System.err.println("IllegalArgumentException: ");
            createQueryExceptionOne.printStackTrace();
            return null;
        }
        try {
            return effekteForAusruestung.getResultList();
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
}
