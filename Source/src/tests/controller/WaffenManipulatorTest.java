package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;

import manipulators.AusruestungsManipulator;
import manipulators.WaffenManipulator;
import model.Ausruestung;
import model.Waffen;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
    
    @Ignore
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
    
    
    
    @Test
    public void getWaffenForAusruestung() {
        Ausruestung testAusruestung = new Ausruestung();
        testAusruestung.addToDB();
        Waffen waffe1 = new Waffen();
        waffe1.setWaffenName_("Waffe1");
        waffe1.setAusruestung_(testAusruestung);
        waffe1.addToDB();
        Waffen waffe2 = new Waffen();
        waffe2.setWaffenName_("Waffe2");
        waffe2.setAusruestung_(testAusruestung);
        waffe2.addToDB();

        List<Waffen> waffenInAusruestung = waffenManipulator.getWaffenInAusruestung(testAusruestung);
        assertTrue(waffenInAusruestung.contains(waffe1));
        assertTrue(waffenInAusruestung.contains(waffe2));
        assertTrue(waffenInAusruestung.size() == 2);
        
        waffe1.deleteFromDB();
        waffe2.deleteFromDB();
        testAusruestung.deleteFromDB();
    }
}
