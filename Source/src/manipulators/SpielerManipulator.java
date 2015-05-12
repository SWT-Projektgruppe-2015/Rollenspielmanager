package manipulators;

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

import model.Ausruestung;
import model.Spieler;
import model.Waffen;
import model.interfaces.DBObject;
import controller.EntityManagerFactoryProvider;
import controller.interfaces.DBManipulator;



public class SpielerManipulator extends DBManipulator {
    private static SpielerManipulator Singleton;
    private static EntityManager theManager;
    
    
    
    private SpielerManipulator() {
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    
    
    public static SpielerManipulator getInstance() {
        if (Singleton == null) {
            Singleton = new SpielerManipulator();
        }
        return Singleton;
    }
    
    
    
    public boolean add(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.persist((Spieler) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: "
                    + persistExceptionOne.getMessage());
            persistExceptionOne.printStackTrace();
            returnValue = false;
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            persistExceptionTwo.printStackTrace();
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            persistExceptionThree.printStackTrace();
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
            persistExceptionFinal.printStackTrace();
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                commitExceptionOne.printStackTrace();
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                commitExceptionTwo.printStackTrace();
                return false;            
            }
        }
        return returnValue;
    }
    
    
    
    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            Ausruestung besitz = theManager.find(Ausruestung.class,
                    ((Spieler) entity).getAusruestung_().getID_());
            for(Waffen waffe : ((Spieler) entity).getWaffen()) {
                waffe.deleteFromDB();
            }
            theManager.remove((Spieler) entity);
            theManager.remove(theManager.contains(besitz) ? besitz : (Ausruestung) theManager.merge(besitz));
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            persistExceptionTwo.printStackTrace();
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            persistExceptionThree.printStackTrace();
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFour) {
            System.err.println("PersistenceException: "
                    + persistExceptionFour.getMessage());
            persistExceptionFour.printStackTrace();
            returnValue = false;
        }
        catch (NullPointerException persistExceptionFive) {
            System.err.println("NullPointerException: "
                    + persistExceptionFive.getMessage());
            persistExceptionFive.printStackTrace();
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                commitExceptionOne.printStackTrace();
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                commitExceptionTwo.printStackTrace();
                return false;
            }
        }
        return returnValue;
    }
    
    
    
    public boolean update(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.merge((Spieler) entity);
        }
        catch (EntityExistsException persistExceptionOne) {
            System.err.println("EntityExistsException: "
                    + persistExceptionOne.getMessage());
            persistExceptionOne.printStackTrace();
            returnValue = false;
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            persistExceptionTwo.printStackTrace();
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException: "
                    + persistExceptionThree.getMessage());
            persistExceptionThree.printStackTrace();
            returnValue = false;
        }
        catch (PersistenceException persistExceptionFinal) {
            System.err.println("PersistenceException: "
                    + persistExceptionFinal.getMessage());
            persistExceptionFinal.printStackTrace();
            returnValue = false;
        }
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                commitExceptionOne.printStackTrace();
                return false;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                commitExceptionTwo.printStackTrace();
                return false;
                
            }
        }
        return returnValue;
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
