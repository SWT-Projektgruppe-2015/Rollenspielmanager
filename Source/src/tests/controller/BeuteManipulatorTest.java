package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;

import model.Beute;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.EntityManagerFactoryProvider;
import controller.manipulators.BeuteManipulator;

public class BeuteManipulatorTest {
    
    private static BeuteManipulator beuteManipulator_;
    private static Beute testBeute;
    private static EntityManager theManager;

    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        beuteManipulator_ = BeuteManipulator.getInstance();
        testBeute = new Beute();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    
    
    @Test
    public void getInstanceTest() {
        beuteManipulator_ = BeuteManipulator.getInstance();
        assertNotNull(beuteManipulator_);
    }

    
    
    @Test
    public void twoInstanceAreSame() {
        BeuteManipulator testInstanceTwo = BeuteManipulator
                .getInstance();
        assertSame(testInstanceTwo, beuteManipulator_);
    }

    
    
    @Test
    public void testAddAndDelete() {
        testBeute = new Beute();
        assertTrue("Adding Beute failed", beuteManipulator_.add(testBeute));
        assertTrue("Deleteing Beute failed", beuteManipulator_.delete(testBeute));
    }
    
    
    
    @Test
    public void testNullAdd() {
        assertFalse(beuteManipulator_.add(null));
    }
    
    
    
    @Ignore
    @Test
    public void twoBeuteAddAreSame() {
       beuteManipulator_.add(testBeute);
       theManager.detach(testBeute);
       assertFalse(beuteManipulator_.add(testBeute));
       beuteManipulator_.delete(testBeute);
    }

    

    @Test
    public void twoBeuteDeleteAreSame() {
        testBeute = new Beute();
        beuteManipulator_.add(testBeute);
        beuteManipulator_.delete(testBeute);
        assertFalse(beuteManipulator_.delete(testBeute));
    }
    
    
    
    @Test
    public void canGetBeuteList()     {
        assertNotNull(beuteManipulator_.getAll());
    }
    
    
    
    @Test
    public void canUpdateBeute() {
        testBeute = new Beute();
        if (beuteManipulator_.add(testBeute)) {
            testBeute.setProfil_("Updated");
            assertTrue(beuteManipulator_.update(testBeute));
            beuteManipulator_.delete(testBeute);
        }
    }
    
    
    
    @Test
    public void cantUpdateNonExistantBeute() {
        assertFalse("Can update non existant Beute",
                beuteManipulator_.update(null));
    }
    
    
    
    @AfterClass
    public static void cleanUp() {
        Beute cleanUpBeute = theManager.find(Beute.class, testBeute.getID_());
        theManager.getTransaction().begin();
        if(cleanUpBeute != null)
            theManager.remove(cleanUpBeute);
        theManager.getTransaction().commit();
    }
}
