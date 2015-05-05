package tests.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import model.Ausruestung;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.AusruestungsManipulator;

public class AusruestungsManipulatorTest {
    
    private static AusruestungsManipulator testInstance;
    private static Ausruestung testAusruestung;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        testInstance = AusruestungsManipulator.getInstance();
        testAusruestung = new Ausruestung();
        
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
        
    }
    
}
