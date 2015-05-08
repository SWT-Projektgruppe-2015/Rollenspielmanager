package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import javax.persistence.EntityManager;

import model.Ausruestung;

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
        System.err.println("----\n59\n----");
        assertTrue(testInstance.add(testAusruestung));
    }
    
    
    
    @Test
    public void testNullAdd() {
        System.err.println("----\n67\n----");
        assertFalse(testInstance.add(null));

    }
    
    
    @Ignore
    @Test
    public void twoAusruestungAddAreSame() {
       testInstance.add(testAusruestung);
       theManager.detach(testAusruestung);
       System.err.println("----\n78\n----");
       assertFalse(testInstance.add(testAusruestung));
    }
    
    
    
    @Test
    public void testDelete() {
        if(testInstance.add(testAusruestung)) {
            System.err.println("----\n87\n----");
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
            System.err.println("----\n103\n----");
            assertFalse(testInstance.delete(testAusruestungHope));
        }
        else {
            fail("This is fail.");
        }        
    }

}
