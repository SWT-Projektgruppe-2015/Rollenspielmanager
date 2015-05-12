package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;

import model.Ausruestung;
import model.Beute;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.EntityManagerFactoryProvider;
import controller.manipulators.BeuteManipulator;

public class BeuteManipulatorTest {
    
    private static BeuteManipulator testInstance;
    private static Beute testBeute;
    
    private static EntityManager theManager;

    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testInstance = BeuteManipulator.getInstance();
        testBeute = new Beute();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }

    
    
    @Test
    public void getInstanceTest() {
        testInstance = BeuteManipulator.getInstance();
        assertNotNull(testInstance);
    }

    
    
    @Test
    public void twoInstanceAreSame() {
        BeuteManipulator testInstanceTwo = BeuteManipulator
                .getInstance();
        assertSame(testInstanceTwo, testInstance);
    }

    
    
    @Test
    public void testAddAndDelete() {
        assertTrue("Adding Beute failed", testInstance.add(testBeute));
        assertTrue("Deleteing Beute failed", testInstance.delete(testBeute));
        
    }
    
    
    
    @Test
    public void testNullAdd() {
        assertFalse(testInstance.add(null));
    }
    
    
    
    @Ignore
    @Test
    public void twoBeuteAddAreSame() {
       testInstance.add(testBeute);
       theManager.detach(testBeute);
       assertFalse(testInstance.add(testBeute));
       testInstance.delete(testBeute);
    }

    

    @Test
    public void twoBeuteDeleteAreSame() {
        testBeute = new Beute();
        testInstance.add(testBeute);
        testInstance.delete(testBeute);
        assertFalse(testInstance.delete(testBeute));
    }
    
    
    
    @Test
    public void canGetBeuteList()     {
        assertNotNull(testInstance.getAll());
    }
    
    
    
    @Test
    public void canUpdateBeute() {
        testBeute = new Beute();
        if (testInstance.add(testBeute)) {
            testBeute.setProfil_("Updated");
            assertTrue(testInstance.update(testBeute));
            testInstance.delete(testBeute);
        }
    }
    
    
    
    
    @Test
    public void cantUpdateNonExistantBeute() {
        assertFalse("Can update non existant Beute",
                testInstance.update(null));
    }
    
    @AfterClass
    public static void cleanUp()    {
        Beute cleanUpBeute = theManager.find(Beute.class, testBeute.getID_());
        theManager.getTransaction().begin();
        if(cleanUpBeute != null)
            theManager.remove(cleanUpBeute);
        theManager.getTransaction().commit();
    }
}
