package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import model.Ausruestung;
import model.EinfacherGegenstand;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.EntityManagerFactoryProvider;
import controller.manipulators.EinfacherGegenstandManipulator;

public class EinfacherGegenstandManipulatorTest {
    private static EinfacherGegenstandManipulator gegenstandsManipulator;
    private static EinfacherGegenstand testGegenstand;
    private static EntityManager theManager;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        gegenstandsManipulator = EinfacherGegenstandManipulator.getInstance();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }

    
    
    @AfterClass
    public static void cleanUp()    {
        Ausruestung cleanUpCrew = theManager.find(Ausruestung.class, testGegenstand.getID_());
        if(cleanUpCrew != null) {
            theManager.getTransaction().begin();
            theManager.remove(cleanUpCrew);
            theManager.getTransaction().commit();
        }
    }
    
    
    
    @Test
    public void getInstanceTest() {
        gegenstandsManipulator = EinfacherGegenstandManipulator.getInstance();
        assertNotNull(gegenstandsManipulator);
    }

    
    
    @Test
    public void twoInstanceAreSame() {
        EinfacherGegenstandManipulator testInstanceTwo = EinfacherGegenstandManipulator
                .getInstance();
        assertSame(testInstanceTwo, gegenstandsManipulator);
    }
    
    
    
    @Test
    public void testAdd() {
        testGegenstand = new EinfacherGegenstand();
        assertTrue(gegenstandsManipulator.add(testGegenstand));
        gegenstandsManipulator.delete(testGegenstand);
    }
    
    
    
    @Test
    public void testNullAdd() {
        assertFalse(gegenstandsManipulator.add(null));

    }
    
    
    
    @Test
    public void testDelete() {
        testGegenstand = new EinfacherGegenstand();
        if(gegenstandsManipulator.add(testGegenstand)) {
            assertTrue(gegenstandsManipulator.delete(testGegenstand));
        }
    }
    
    
    
    @Test
    public void twoItemDeleteAreSame() {
        testGegenstand = new EinfacherGegenstand();
        if(gegenstandsManipulator.add(testGegenstand)) {
            gegenstandsManipulator.delete(testGegenstand);
            assertFalse(gegenstandsManipulator.delete(testGegenstand));
        }      
    }
    
    
    
    @Test 
    public void testNullToDelete() {
        assertFalse(gegenstandsManipulator.delete(null));
    }
    
    
    
    @Test
    public void canSimpleSalesItem() {
        testGegenstand = new EinfacherGegenstand();
        if(gegenstandsManipulator.add(testGegenstand)) {
            testGegenstand.setKosten_(100);
            assertTrue("Can't update", gegenstandsManipulator.update(testGegenstand));
            gegenstandsManipulator.delete(testGegenstand);
        }
    }
    
    
    
    @Test
    public void cantUpdateNonExistantItems() {
        assertFalse("Can update non existant Item", gegenstandsManipulator.update(null));
    }
    
    
    
    @Test
    public void canGetAllItems() {
        assertNotNull(gegenstandsManipulator.getAll());
    }
}
