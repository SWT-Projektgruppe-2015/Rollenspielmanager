package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;

import model.Ausruestung;
import model.interfaces.DBObject;
import controller.interfaces.DBManipulator;

public class AusruestungsManipulator implements DBManipulator {
    private static EntityManagerFactory factory_ = Persistence
            .createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager theManager = factory_.createEntityManager();
    private static AusruestungsManipulator singelton;

    private AusruestungsManipulator() {

    }

    @Override
    public boolean add(DBObject entity) {
        theManager.getTransaction().begin();
        try {
            theManager.persist((Ausruestung) entity);
        }
        catch (EntityExistsException addExceptionOne) {
            System.err.println("EntityExistsException: "
                    + addExceptionOne.getMessage());
            theManager.getTransaction().commit();
            return false;
        }
        catch (TransactionRequiredException addExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + addExceptionTwo.getMessage());
            return false;
        }
        catch (IllegalArgumentException addExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + addExceptionThree.getMessage());
            return false;
        }

        theManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(DBObject entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(DBObject entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<DBObject> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    public static AusruestungsManipulator getInstance() {
        if (singelton == null) {
            singelton = new AusruestungsManipulator();
        }
        return singelton;
    }

}
