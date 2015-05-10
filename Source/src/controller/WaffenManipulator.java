package controller;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Ausruestung;
import model.Waffen;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class WaffenManipulator extends DBManipulator {
    private static WaffenManipulator singleton;   

    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((Waffen) entity);
    }

    @Override
    protected void removeEntity(DBObject entity) {
        theManager.remove((Waffen) entity);
    }

    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Waffen) entity);
    }

    public static WaffenManipulator getInstance() {
        if(singleton == null)
            singleton = new WaffenManipulator();
        return singleton;
    }
    
    public List<Waffen> getWaffenInAusruestung(Ausruestung ausruestung) {
        TypedQuery<Waffen> waffenInAusruestung;
        try {
            waffenInAusruestung = theManager.createQuery(
                "FROM Waffen w WHERE w.ausruestung_ = " + ausruestung.getID_(), Waffen.class);
        }
        catch(IllegalArgumentException createQueryExceptionOne)   {
            System.err.println("IllegalArgumentException: ");
            createQueryExceptionOne.printStackTrace();
            return null;
        }
        try {
            return waffenInAusruestung.getResultList();
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
