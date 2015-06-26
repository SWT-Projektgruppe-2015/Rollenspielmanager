package controller.manipulators;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Ausruestung;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class AusruestungsManipulator extends DBManipulator {
    private static AusruestungsManipulator singelton;   
   
    public List<Ausruestung> getAll() {
        TypedQuery<Ausruestung> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Ausruestung",
                Ausruestung.class);
        }
        catch(IllegalArgumentException createQueryExceptionOne)   {
            createQueryExceptionOne.printStackTrace();
            return null;
        }
        try {
            return getAllRows.getResultList();
        }
        catch(IllegalStateException| PersistenceException AnyException)  {
            AnyException.printStackTrace();
            return null;
        }
    }
    
    
   
    public static AusruestungsManipulator getInstance() {
        if (singelton == null) {
            singelton = new AusruestungsManipulator();
        }
        return singelton;
    }



    @Override
    protected void persistEntity(DBObject entity) {
        theManager.persist((Ausruestung) entity);
    }



    @Override
    protected void removeEntity(DBObject entity) {
       theManager.remove((Ausruestung) entity);        
    }



    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Ausruestung) entity);
    }
}
