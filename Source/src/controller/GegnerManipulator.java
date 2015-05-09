package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import controller.interfaces.DBManipulator;
import model.Ausruestung;
import model.Beute;
import model.Gegner;
import model.interfaces.DBObject;

public class GegnerManipulator extends DBManipulator {
    private static GegnerManipulator Singleton;
    private static EntityManager theManager;
    
    private GegnerManipulator() {
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    
    
    public static GegnerManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new GegnerManipulator();
        }
        return Singleton;
    }

    
    @Override
    public boolean add(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.persist((Gegner) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: in add from GegnerManipulator "
                    + persistExceptionOne.getMessage());
            returnValue = false;
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: in add from GegnerManipulator "
                    + persistExceptionTwo.getMessage());
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: in add from GegnerManipulator "
                    + persistExceptionThree.getMessage());
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: in add from GegnerManipulator "
                    + persistExceptionFinal.getMessage());
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: in add from GegnerManipulator "
                        + commitExceptionOne.getMessage());
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: in add from GegnerManipulator "
                        + commitExceptionTwo.getMessage());
                return false;
            }
        }
        return returnValue;
    }
    
    
    private boolean deleteBeuteAndAusruestung(Gegner gegner) {
        Ausruestung ausruestung = theManager.find(Ausruestung.class, gegner.getAusruestung_().getID_());
        Beute beute = theManager.find(Beute.class, gegner.getBeute_().getID_());
        DBManipulator ausruestungManipulator = AusruestungsManipulator.getInstance();
        DBManipulator beuteManipulator = BeuteManipulator.getInstance();
        
        return ausruestungManipulator.delete(ausruestung) && beuteManipulator.delete(beute);
    }
    
    @Override
    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            if (deleteBeuteAndAusruestung((Gegner) entity)) 
                theManager.remove((Gegner) entity);
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFour) {
            System.err.println("PersistenceException: "
                    + persistExceptionFour.getMessage());
            returnValue = false;
        }
        catch (NullPointerException persistExceptionFive) {
            System.err.println("NullPointerException: "
                    + persistExceptionFive.getMessage());
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                return false;
            }
        }
        
        return returnValue;
    }
    
    
    @Override
    public boolean update(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.merge((Gegner) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: "
                    + persistExceptionOne.getMessage());
            returnValue = false;
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                return false;
                
            }
        }
        return returnValue;
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
        // TODO Auto-generated method stub
        
    }



    @Override
    protected void removeEntity(DBObject entity) {
        // TODO Auto-generated method stub
        
    }



    @Override
    protected void mergeEntity(DBObject entity) {
        // TODO Auto-generated method stub
        
    }
}
