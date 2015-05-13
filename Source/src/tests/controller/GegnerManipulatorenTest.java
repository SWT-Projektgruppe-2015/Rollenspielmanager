package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import model.Gegner;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.manipulators.GegnerManipulator;

public class GegnerManipulatorenTest {
    private static GegnerManipulator gegnerManipulator;
    private static Gegner testGegner;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        gegnerManipulator = GegnerManipulator.getInstance();
    }
    
    
    //Ueberfluessig
    @Test
    public void CanMakeInstance() {
        assertNotNull(gegnerManipulator);
    }
    
    @Test
    public void twoInstancesAreSame() {
        GegnerManipulator testInstance2 = GegnerManipulator.getInstance();
        assertSame(gegnerManipulator, testInstance2);
    }
    
    
    /**
     * Ignoriert bis man rausbekommt wie man die Exception ausloest.
     */
    @Ignore
    @Test
    public void causeEntityExistsException() {
        testGegner = new Gegner();
        gegnerManipulator.add(testGegner);
        assertFalse("Can add same player twice.", gegnerManipulator.add(testGegner));
        gegnerManipulator.delete(testGegner);
        
    }
    
    @Test
    public void cantAddNonExistantGegner() {
        assertFalse("Can add non existant Gegner", gegnerManipulator.add(null));
    }
    
    
    
    @Test
    public void canAddGegner() {
        testGegner = new Gegner();
        assertTrue("Couldn't add new Gegner.", gegnerManipulator.add(testGegner));
        gegnerManipulator.delete(testGegner);
    }
    
    
    @Test
    public void canDeleteGegner() {
        testGegner = new Gegner();
        assertTrue(gegnerManipulator.add(testGegner));
        assertTrue("Can't delete Gegner", gegnerManipulator.delete(testGegner));
    }
    
    @Test
    public void cantDeleteNonExistantGegner() {
        assertFalse("Can delete non existant Gegner",
                gegnerManipulator.delete(null));
    }
    
    @Test
    public void canUpdateGegner() {
        testGegner = new Gegner();
        if (gegnerManipulator.add(testGegner)) {
            testGegner.setName_("Nr. 461");
            assertTrue(gegnerManipulator.update(testGegner));
            gegnerManipulator.delete(testGegner);
        }
    }
    
    @Test
    public void cantUpdateNonExistantGegner() {
        assertFalse("Can update non existant Gegner",
                gegnerManipulator.update(null));
    }
    
    @Test
    public void canGetGegnerList()     {
        assertNotNull(gegnerManipulator.getAll());
     }
}
