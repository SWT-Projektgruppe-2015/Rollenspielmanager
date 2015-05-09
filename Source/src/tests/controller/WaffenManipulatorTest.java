package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import model.Ausruestung;
import model.Waffen;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.AusruestungsManipulator;
import controller.WaffenManipulator;
import controller.EntityManagerFactoryProvider;

public class WaffenManipulatorTest {
    private static WaffenManipulator waffenManipulator;
    private static Waffen testWaffen;
    private static EntityManager theManager;
    private static Ausruestung testAusruestung;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        waffenManipulator = WaffenManipulator.getInstance();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
        testAusruestung = new Ausruestung();
        AusruestungsManipulator.getInstance().add(testAusruestung);
    }

    
    
    @AfterClass
    public static void cleanUp()    {
        Ausruestung cleanUpCrew = theManager.find(Ausruestung.class, testAusruestung.getID_());
        theManager.getTransaction().begin();
        theManager.remove(cleanUpCrew);
        theManager.getTransaction().commit();
    }
    
    
    
    @Test
    public void getInstanceTest() {
        waffenManipulator = WaffenManipulator.getInstance();
        assertNotNull(waffenManipulator);
    }

    
    
    @Test
    public void twoInstanceAreSame() {
        WaffenManipulator testInstanceTwo = WaffenManipulator
                .getInstance();
        assertSame(testInstanceTwo, waffenManipulator);
    }
    
    
    
    @Test
    public void testAdd() {
        testWaffen = new Waffen();
        testWaffen.setAusruestung_(testAusruestung);
        assertTrue(waffenManipulator.add(testWaffen));
        waffenManipulator.delete(testWaffen);
    }
    
    
    @Ignore
    @Test
    public void testNullAdd() {
        assertFalse(waffenManipulator.add(null));

    }
    
    @Ignore
    @Test
    public void twoWaffenAddAreSame() {
       testWaffen = new Waffen();
       testWaffen.setAusruestung_(testAusruestung);
       theManager.detach(testWaffen);
       assertFalse(waffenManipulator.add(testWaffen));
       waffenManipulator.delete(testWaffen);
    }
    
    
    
    @Test
    public void testDelete() {
        testWaffen = new Waffen();
        testWaffen.setAusruestung_(testAusruestung);
        if(waffenManipulator.add(testWaffen)) {
            assertTrue(waffenManipulator.delete(testWaffen));
        }
        else {
            fail("This is fail.");
        }
    }
    
    
    @Test
    public void twoWaffenDeleteAreSame() {
        testWaffen = new Waffen();
        testWaffen.setAusruestung_(testAusruestung);
        if(waffenManipulator.add(testWaffen)) {
            waffenManipulator.delete(testWaffen);
            assertFalse(waffenManipulator.delete(testWaffen));
        }
        else {
            fail("This is fail.");
        }        
    }
    
    
    @Test 
    public void testNullToDelete() {
        assertFalse(waffenManipulator.delete(null));
    }
    
    
    
    @Test
    public void canUpdateWaffen() {
        testWaffen = new Waffen();
        AusruestungsManipulator.getInstance().add(testWaffen.getAusruestung_());
        if(waffenManipulator.add(testWaffen)) {
            testWaffen.setWaffenSchaden_(333);
            assertTrue("Can't update", waffenManipulator.update(testWaffen));
            waffenManipulator.delete(testWaffen);
        }
        else    {
            fail();
        }
    }
    
    
    @Test
    public void cantUpdateNonExistantWaffen() {
        assertFalse("Can update non existant Waffen",waffenManipulator.update(null));
    }
}
