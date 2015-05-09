package controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;



import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;

import controller.interfaces.DBManipulator;
import model.Beute;
import model.interfaces.DBObject;

public class BeuteManipulator implements DBManipulator{
    private static EntityManager theManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
    private static BeuteManipulator singelton;
    
    private BeuteManipulator() {}
    
    @Override
    public boolean add(DBObject entity) {
        theManager.getTransaction().begin();
        try {
            theManager.persist((Beute) entity);
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
            System.err.println("IllegalArgumentException in add in Beutemanipulator: "
                    + addExceptionThree.getMessage());
            return false;
        }
        
        theManager.getTransaction().commit();
        return true;
    }
    
    @Override
    public boolean delete(DBObject entity) {
        boolean returnValue = true;
        try {
            Beute defaultBeute = getDefaultBeute();
            theManager.getTransaction().begin();
            replaceBeute(defaultBeute, (Beute)entity);
            theManager.remove((Beute) entity);
        }
        catch (TransactionRequiredException persistExceptionTwo) {
            System.err.println("TransactionRequiredException: "
                    + persistExceptionTwo.getMessage());
            returnValue = false;
        }
        catch (IllegalArgumentException persistExceptionThree) {
            System.err.println("IllegalArgumentException in delete from BeuteManipulator"
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
                theManager.getTransaction().commit();
                return false;
            }
        }
        
        return returnValue;
    }
    
    
    
    private boolean replaceBeute(Beute defaultBeute, Beute deletedBeute) {
        Query getAllRows;
        try {
            getAllRows = theManager.createQuery("UPDATE Gegner SET beuteTyp_ = "+ defaultBeute.ID_ 
                    + " WHERE beuteTyp_ = " + deletedBeute.ID_);
        }
        catch(IllegalArgumentException createQueryExceptionOne) {
            System.err.println("IllegalArgumentException: in replace Beute from BeuteManipulator ");
            return false;
        }
        try {
            System.out.println("Hallo");
            getAllRows.executeUpdate();
            return true;
        }
        catch(HibernateException e) {
            return false;
        }
    }
    
    
    private Beute getDefaultBeute() {
        TypedQuery<Beute> getAllRows;
        theManager.getTransaction().begin();
        try {
            getAllRows = theManager.createQuery("FROM Beute WHERE profil_ = '" + Beute.DEFAULTNAME + "'",
                Beute.class);
        }
        catch(IllegalArgumentException createQueryExceptionOne)   {
            System.err.println("IllegalArgumentException in getDefaultBeute: ");
            return null;
        }
        try {
            return getAllRows.getSingleResult();
        }
        catch(NoResultException noResult) {
            System.err.println("NoResultException in getDefaultBeute: ");
            Beute defaultBeute = new Beute();
            add(defaultBeute);
            return getAllRows.getSingleResult();
        }
        catch(NonUniqueResultException notUnique) {
            // TODO:: Hier sollte evtl. noch was gemacht werden.
            System.err.println("There is more than one Beute: ");
            return getAllRows.getResultList().get(0);
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
        finally {
            try {
                theManager.getTransaction().commit();
            }
            catch (RollbackException commitExceptionOne) {
                System.err.println("RollBackException: "
                        + commitExceptionOne.getMessage());
                return null;
            }
            catch (PersistenceException commitExceptionTwo) {
                System.err.println("PersistenceException: "
                        + commitExceptionTwo.getMessage());
                theManager.getTransaction().commit();
                return null;
            }
        }
    }
    
    
    
    @Override
    public boolean update(DBObject entity) {
        boolean returnValue = true;
        theManager.getTransaction().begin();
        try {
            theManager.merge((Beute) entity);
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
    
    public List<Beute> getAll() {
        TypedQuery<Beute> getAllRows;
        try {
            getAllRows = theManager.createQuery("FROM Beute", Beute.class);
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
    
    public static BeuteManipulator getInstance() {
        if (singelton == null) {
            singelton = new BeuteManipulator();
        }
        return singelton;
    }
}
