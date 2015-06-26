package controller.manipulators;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import controller.interfaces.DBManipulator;
import model.Gruppe;
import model.Spieler;
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
        catch(IllegalStateException| PersistenceException AnyException)  {
            AnyException.printStackTrace();
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
        for (Spieler spieler : ((Gruppe) entity).getMembers_()){
            spieler.getMembership_().remove(entity);
        }
    }



    @Override
    protected void mergeEntity(DBObject entity) {
        theManager.merge((Gruppe) entity);
    }
}
