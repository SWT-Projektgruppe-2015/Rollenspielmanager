package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import model.GegnerTyp;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.manipulators.GegnerManipulator;

public class GegnerManipulatorenTest {
    private static GegnerManipulator gegnerManipulator;
    private static GegnerTyp testGegner;
    
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
        testGegner = new GegnerTyp();
        gegnerManipulator.add(testGegner);
        assertFalse("Can add same player twice.", gegnerManipulator.add(testGegner));
        gegnerManipulator.delete(testGegner);
        
    }
    
    @Test
    public void cantAddNonExistantGegner() {
        assertFalse("Can add non existant GegnerTyp", gegnerManipulator.add(null));
    }
    
    
    
    @Test
    public void canAddGegner() {
        testGegner = new GegnerTyp();
        assertTrue("Couldn't add new GegnerTyp.", gegnerManipulator.add(testGegner));
        gegnerManipulator.delete(testGegner);
    }
    
    
    @Test
    public void canDeleteGegner() {
        testGegner = new GegnerTyp();
        gegnerManipulator.add(testGegner);
        assertTrue("Can't delete GegnerTyp", gegnerManipulator.delete(testGegner));
    }
    
    @Test
    public void cantDeleteNonExistantGegner() {
        assertFalse("Can delete non existant GegnerTyp",
                gegnerManipulator.delete(null));
    }
    
    
    
    @Test
    public void canUpdateGegner() {
        testGegner = new GegnerTyp();
        if (gegnerManipulator.add(testGegner)) {
            testGegner.setName_("Nr. 461");
            assertTrue(gegnerManipulator.update(testGegner));
            gegnerManipulator.delete(testGegner);
        }
    }
    
    @Test
    public void cantUpdateNonExistantGegner() {
        assertFalse("Can update non existant GegnerTyp",
                gegnerManipulator.update(null));
    }
    
    @Test
    public void canGetGegnerList()     {
        assertNotNull(gegnerManipulator.getAll());
     }
}
