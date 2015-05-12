package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import model.Gruppe;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.manipulators.GruppenManipulator;

public class GruppenManipulatorTest {
    private static GruppenManipulator gruppenManipulator;
    private static Gruppe testGruppe;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        gruppenManipulator = GruppenManipulator.getInstance();
    }
    
    @Test
    public void CanMakeInstance() {
        assertNotNull(gruppenManipulator);
    }
    
    @Test
    public void twoInstancesAreSame() {
        GruppenManipulator testInstance2 = GruppenManipulator.getInstance();
        assertSame(gruppenManipulator, testInstance2);
    }
    
    
    /**
     * Ignoriert bis man rausbekommt wie man die Exception ausloest.
     */
    @Ignore
    @Test
    public void causeEntityExistsException() {
        testGruppe = new Gruppe();
        gruppenManipulator.add(testGruppe);
        assertFalse("Can add same player twice.", gruppenManipulator.add(testGruppe));
        gruppenManipulator.delete(testGruppe);
        
    }
    
    @Test
    public void cantAddNonExistantGruppe() {
        assertFalse("Can add non existant Gruppe", gruppenManipulator.add(null));
    }
    
    
    
    @Test
    public void canAddGruppe() {
        testGruppe = new Gruppe();
        assertTrue("Couldn't add new Gruppe.", gruppenManipulator.add(testGruppe));
        gruppenManipulator.delete(testGruppe);
    }
    
    
    @Test
    public void canDeleteGruppe() {
        testGruppe = new Gruppe();
        assertTrue(gruppenManipulator.add(testGruppe));
        assertTrue("Can't delete Gruppe", gruppenManipulator.delete(testGruppe));
    }
    
    @Test
    public void cantDeleteNonExistantGruppe() {
        assertFalse("Can delete non existant Gruppe",
                gruppenManipulator.delete(null));
    }
    
    @Test
    public void canUpdateGruppe() {
        testGruppe = new Gruppe();
        if (gruppenManipulator.add(testGruppe)) {
            testGruppe.setName_("Nr. 461");
            assertTrue(gruppenManipulator.update(testGruppe));
            gruppenManipulator.delete(testGruppe);
        }
    }
    
    @Test
    public void cantUpdateNonExistantGruppe() {
        assertFalse("Can update non existant Gruppe",
                gruppenManipulator.update(null));
    }
    
    @Test
    public void canGetGruppeList()     {
        assertNotNull(gruppenManipulator.getAll());
    }
}
