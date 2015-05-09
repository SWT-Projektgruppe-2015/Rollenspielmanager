package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;

import model.Ausruestung;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.AusruestungsManipulator;
import controller.EntityManagerFactoryProvider;

public class AusruestungsManipulatorTest {

    private static AusruestungsManipulator testInstance;
    private static Ausruestung testAusruestung;
    private static EntityManager theManager;

    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testInstance = AusruestungsManipulator.getInstance();
        testAusruestung = new Ausruestung();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }

    
    
    @Test
    public void getInstanceTest() {
        testInstance = AusruestungsManipulator.getInstance();
        assertNotNull(testInstance);
    }

    
    
    @Test
    public void twoInstanceAreSame() {
        AusruestungsManipulator testInstanceTwo = AusruestungsManipulator
                .getInstance();
        assertSame(testInstanceTwo, testInstance);
    }
    
    
    
    @Test
    public void testAdd() {
        assertTrue(testInstance.add(testAusruestung));
        testInstance.delete(testAusruestung);
    }
    
    
    
    @Test
    public void testNullAdd() {
        assertFalse(testInstance.add(null));

    }
    
    
    @Ignore
    @Test
    public void twoAusruestungAddAreSame() {
       testInstance.add(testAusruestung);
       theManager.detach(testAusruestung);
       assertFalse(testInstance.add(testAusruestung));
       testInstance.delete(testAusruestung);
    }
    
    
    
    @Test
    public void testDelete() {
        testAusruestung = new Ausruestung();
        if(testInstance.add(testAusruestung)) {
            assertTrue(testInstance.delete(testAusruestung));
        }
        else {
            fail("This is fail.");
        }
    }
    
    
    
    @Test
    public void twoAusruestungDeleteAreSame() {
        Ausruestung testAusruestungHope = new Ausruestung();
        boolean test = testInstance.add(testAusruestungHope);
        if(test) {
            testInstance.delete(testAusruestungHope);
            assertFalse(testInstance.delete(testAusruestungHope));
        }
        else {
            fail("This is fail.");
        }        
    }
    
    
    
    @Test 
    public void testNullToDelete() {
        assertFalse(testInstance.delete(null));
    }
    
    
    
    @Test
    public void canUpdateAusruestung() {
        testAusruestung = new Ausruestung();
        if(testInstance.add(testAusruestung)) {
            testAusruestung.setDefH_(2);
            assertTrue("Can't update", testInstance.update(testAusruestung));
            testInstance.delete(testAusruestung);
        }
        else    {
            fail();
        }
    }
    
    
    
    @Test
    public void cantUpdateNonExistantAuesrustung() {
        assertFalse("Can update non existant Auesrustung",testInstance.update(null));
    }
    
    
    
    @Test
    public void testGetAll() {
        assertNotNull("List of Ausruestungen is Null.", testInstance.getAll());
    }
    
    
    
    @AfterClass
    public static void cleanUp()    {
        Ausruestung cleanUpEquipment = theManager.find(Ausruestung.class, testAusruestung.getID_());
        theManager.getTransaction().begin();
        if(cleanUpEquipment != null)
            theManager.remove(cleanUpEquipment);
        theManager.getTransaction().commit();
    }
}
