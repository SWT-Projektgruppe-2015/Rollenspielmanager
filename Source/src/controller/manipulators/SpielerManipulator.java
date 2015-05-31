package controller.manipulators;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Ausruestung;
import model.Gruppe;
import model.Spieler;
import model.Waffen;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class SpielerManipulator extends DBManipulator {
    private static SpielerManipulator Singleton;
    
    public static SpielerManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new SpielerManipulator();
        }
        return Singleton;
    }
    
    
    
    public List<Spieler> getAll() {
        TypedQuery<Spieler> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Spieler",
                Spieler.class);
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



    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((Spieler) entity);       
    }



    @Override
    protected void removeEntity(DBObject entity) {
        Ausruestung besitz = null;
        if(entity != null) {
            besitz = theManager.find(Ausruestung.class,
                ((Spieler) entity).getAusruestung_().getID_());
            for(Waffen waffe : ((Spieler) entity).getWaffen()) {
                theManager.remove(theManager.contains(waffe) ? waffe : (Waffen) theManager.merge(waffe));
            }

            for (Gruppe gruppe : ((Spieler) entity).getMembership_()){
                gruppe.getMembers_().remove(entity);
            }
        }
        theManager.remove((Spieler) entity);
        if(besitz != null)
            theManager.remove(theManager.contains(besitz) ? besitz : (Ausruestung) theManager.merge(besitz)); 
    }



    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Spieler) entity);
    }
}
