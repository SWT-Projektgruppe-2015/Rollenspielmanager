package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;

import model.Beute;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.BeuteManipulator;
import controller.EntityManagerFactoryProvider;

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
    public void testAdd() {
        assertTrue(testInstance.add(testBeute));
        
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
    }
    
    @Test
    public void testDelete() {
        testBeute = new Beute();
        assertTrue(testInstance.add(testBeute));
        assertTrue(testInstance.delete(testBeute));
    }

    @Ignore
    @Test
    public void twoBeuteDeleteAreSame() {
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
}
