package controller.manipulators;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import controller.interfaces.DBManipulator;
import model.Ausruestung;
import model.Gegner;
import model.Waffen;
import model.interfaces.DBObject;

public class GegnerManipulator extends DBManipulator {
    private static GegnerManipulator Singleton;  
    
    public static GegnerManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new GegnerManipulator();
        }
        return Singleton;
    }
    
    
    
    public List<Gegner> getAll() {
        
        TypedQuery<Gegner> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Gegner",
                Gegner.class);
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
        Ausruestung ausruestung = entity == null ? null : ((Gegner)entity).getAusruestung_();
        boolean needsDefaultWaffe = ausruestung == null || ausruestung.getWaffen().isEmpty();
        theManager.persist((Gegner) entity);
        if(needsDefaultWaffe) {
            ausruestung = ((Gegner)entity).getAusruestung_();
            Waffen defaultWaffe = new Waffen(ausruestung);
            theManager.persist((Waffen) defaultWaffe);
        }
    }



    @Override
    protected void removeEntity(DBObject entity) {
        Ausruestung ausruestung = entity == null ? null : ((Gegner)entity).getAusruestung_();
        theManager.remove((Gegner) entity);    
        if(entity != null) {
            Ausruestung besitz = theManager.find(Ausruestung.class,
                ausruestung.getID_());
            
            if(besitz != null && besitz.getID_() != 0) {
                for(Waffen waffe : besitz.getWaffen()) {
                    theManager.remove(theManager.contains(waffe) ? waffe : (Waffen) theManager.merge(waffe));
                }
                
                theManager.remove(theManager.contains(besitz) ? besitz : (Ausruestung) theManager.merge(besitz));
            }
        }
    }



    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Gegner) entity);
    }
}
