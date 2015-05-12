package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import manipulators.SimpleSalesItemManipulator;
import model.Ausruestung;
import model.EinfacherGegenstand;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.EntityManagerFactoryProvider;

public class SimpleSalesItemManipulatorTest {
    private static SimpleSalesItemManipulator simpleSalesItemManipulator;
    private static EinfacherGegenstand testItem;
    private static EntityManager theManager;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        simpleSalesItemManipulator = SimpleSalesItemManipulator.getInstance();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }

    
    
    @AfterClass
    public static void cleanUp()    {
        Ausruestung cleanUpCrew = theManager.find(Ausruestung.class, testItem.getID_());
        theManager.getTransaction().begin();
        if(cleanUpCrew != null)
            theManager.remove(cleanUpCrew);
        theManager.getTransaction().commit();
    }
    
    
    
    @Test
    public void getInstanceTest() {
        simpleSalesItemManipulator = SimpleSalesItemManipulator.getInstance();
        assertNotNull(simpleSalesItemManipulator);
    }

    
    
    @Test
    public void twoInstanceAreSame() {
        SimpleSalesItemManipulator testInstanceTwo = SimpleSalesItemManipulator
                .getInstance();
        assertSame(testInstanceTwo, simpleSalesItemManipulator);
    }
    
    
    
    @Test
    public void testAdd() {
        testItem = new EinfacherGegenstand();
        assertTrue(simpleSalesItemManipulator.add(testItem));
        simpleSalesItemManipulator.delete(testItem);
    }
    
    
    
    @Test
    public void testNullAdd() {
        assertFalse(simpleSalesItemManipulator.add(null));

    }
    
    
    
    @Test
    public void testDelete() {
        testItem = new EinfacherGegenstand();
        if(simpleSalesItemManipulator.add(testItem)) {
            assertTrue(simpleSalesItemManipulator.delete(testItem));
        }
    }
    
    
    
    @Test
    public void twoItemDeleteAreSame() {
        testItem = new EinfacherGegenstand();
        if(simpleSalesItemManipulator.add(testItem)) {
            simpleSalesItemManipulator.delete(testItem);
            assertFalse(simpleSalesItemManipulator.delete(testItem));
        }      
    }
    
    
    
    @Test 
    public void testNullToDelete() {
        assertFalse(simpleSalesItemManipulator.delete(null));
    }
    
    
    
    @Test
    public void canSimpleSalesItem() {
        testItem = new EinfacherGegenstand();
        if(simpleSalesItemManipulator.add(testItem)) {
            testItem.setCost_(100);
            assertTrue("Can't update", simpleSalesItemManipulator.update(testItem));
            simpleSalesItemManipulator.delete(testItem);
        }
    }
    
    
    
    @Test
    public void cantUpdateNonExistantItems() {
        assertFalse("Can update non existant Item", simpleSalesItemManipulator.update(null));
    }
    
    
    
    @Test
    public void canGetAllItems() {
        assertNotNull(simpleSalesItemManipulator.getAll());
    }
}
