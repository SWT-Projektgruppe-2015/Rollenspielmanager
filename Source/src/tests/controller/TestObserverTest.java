package tests.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestObserverTest {
    
    private static TestObserver testCreation;


    @BeforeClass
    public static void SetUpBeforeClass() {
        testCreation = new TestObserver();
    }
    
    
    
    @Test
    public void canUpdateGruppenList()  {
        assertNotNull("TestObserver constructor not working\n",testCreation);
        testCreation.updateGruppenList();
        assertEquals(testCreation.getUpdateCounter_(),1);
    }
    
    
    
    @Test
    public void canUpdateSelectedGruppenList()  {
        assertNotNull("TestObserver constructor not working\n",testCreation);
        testCreation.updateSelectedGruppe();
        assertEquals(testCreation.getUpdateCounter_(),1);
    }
}
