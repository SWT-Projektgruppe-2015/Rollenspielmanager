package tests.controller;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import model.Ausruestung;

import javax.persistence.EntityManager;

import model.Spieler;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
//import org.junit.Before;


import controller.EntityManagerFactoryProvider;
import controller.SpielerManipulator;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
public class SpielerManipulatorTest {
    
    private static SpielerManipulator testInstance;
    private static Spieler testSpieler;
    private static EntityManager theManager;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        testInstance = SpielerManipulator.getInstance();
        testSpieler = new Spieler();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    @Test
    public void CanMakeInstance() {
        assertNotNull(testInstance);
    }
    
    @Test
    public void twoInstancesAreSame() {
        SpielerManipulator testInstance2 = SpielerManipulator.getInstance();
        assertSame(testInstance, testInstance2);
    }
    
    @Test
    public void canAddSpieler() {
        assertTrue("Couldn't add new Spieler.", testInstance.add(testSpieler));
    }
    
    /**
     * Ignoriert bis man rausbekommt wie man die Exception ausloest.
     */
    @Ignore
    @Test
    public void causeEntityExistsException() {
        testInstance.add(testSpieler);
        assertFalse("Can add same player twice.", testInstance.add(testSpieler));
    }
    
    @Test
    public void cantAddNonExistantSpieler() {
        assertFalse("Can add non existant Spieler", testInstance.add(null));
    }
    
    @Test
    public void canDeleteSpieler() {
        testInstance.add(testSpieler);
        assertTrue("Can't delete Spieler", testInstance.delete(testSpieler));
    }
    
    @Test
    public void cantDeleteNonExistantSpieler() {
        assertFalse("Can delete non existant Spieler",
                testInstance.delete(null));
    }
    
    @Test
    public void canUpdateSpieler() {
        if (testInstance.add(testSpieler)) {
            testSpieler.setName_("Spieler formerly known as Jane");
            assertTrue(testInstance.update(testSpieler));
        }
    }
    
    @Test
    public void cantUpdateNonExistantSpieler() {
        assertFalse("Can update non existant Spieler",
                testInstance.update(null));
    }
    
    @Test
    public void canGetSpielerList()     {
        assertNotNull(testInstance.getAll());
    }
    @AfterClass
    public static void cleanUp(){
        testInstance.delete(testSpieler);
    }
}
