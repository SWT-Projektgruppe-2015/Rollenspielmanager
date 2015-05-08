package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import model.Gegner;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.EntityManagerFactoryProvider;
import controller.GegnerManipulator;

public class GegnerManipulatorenTest {
    private static GegnerManipulator testInstance;
    private static Gegner testGegner;
    private static EntityManager theManager;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        testInstance = GegnerManipulator.getInstance();
        testGegner = new Gegner();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    @Test
    public void CanMakeInstance() {
        assertNotNull(testInstance);
    }
    
    @Test
    public void twoInstancesAreSame() {
        GegnerManipulator testInstance2 = GegnerManipulator.getInstance();
        assertSame(testInstance, testInstance2);
    }
    
    
    /**
     * Ignoriert bis man rausbekommt wie man die Exception ausloest.
     */
    @Ignore
    @Test
    public void causeEntityExistsException() {
        testInstance.add(testGegner);
        
        assertFalse("Can add same player twice.", testInstance.add(testGegner));
        
    }
    
    @Test
    public void cantAddNonExistantGegner() {
        assertFalse("Can add non existant Gegner", testInstance.add(null));
    }
    
    
    
    @Test
    public void canAddGegner() {
        assertTrue("Couldn't add new Gegner.", testInstance.add(testGegner));
    }
    
    
    
    @Test
    public void canDeleteGegner() {
        assertTrue("Can't delete Gegner", testInstance.delete(testGegner));
    }
    
    @Test
    public void cantDeleteNonExistantGegner() {
        assertFalse("Can delete non existant Gegner",
                testInstance.delete(null));
    }
    
    @Test
    public void canUpdateGegner() {
        if (testInstance.add(testGegner)) {
            testGegner.setName_("Gegner formerly known as Gegner Nr. 460");
            assertTrue(testInstance.update(testGegner));
        }
    }
    
    @Test
    public void cantUpdateNonExistantGegner() {
        assertFalse("Can update non existant Gegner",
                testInstance.update(null));
    }
    
    @Test
    public void canGetGegnerList()     {
        assertNotNull(testInstance.getAll());
     }
}
