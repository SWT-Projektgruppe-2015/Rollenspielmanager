package tests.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.List;

import model.Gruppe;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.GruppenSubject;
import controller.interfaces.GruppenObserver;

public class GruppenSubjectTest {
    
    private static GruppenSubject testSubject;
    private static GruppenObserver testObserver;
    private static GruppenObserver testObserver2;
    
    
    
    @BeforeClass
    public static void setUpBeforeClass() {
        testSubject = new GruppenSubject();
        testObserver = new TestObserver();
        testObserver2 = new TestObserver();
    }
    
    
    
    @After
    public void cleanUpAfter() {
        testSubject.removeGruppenObserver(testObserver);
        testSubject.removeGruppenObserver(testObserver2);
    }
    
    

    @Test
    public void canMakeGruppenSubject() {
        assertNotNull("New ObserverSubject is NULL.\n",testSubject);
    }
    
    
    
    @Test
    public void CanAddGruppenObserver() {
        testSubject.addGruppenObserver(testObserver);
        assertTrue("Added Observer not in the List.\n",testSubject.containsGruppenObserver(testObserver));
    }
    
    
    
    @Test
    public void CanUpdateNotSelectedSingleGroup() {
        testSubject.addGruppenObserver(testObserver);
        ((TestObserver)testObserver).setUpdateCounter_(0);
        testSubject.setGruppen(testSubject.getGruppen());
        assertEquals(((TestObserver)testObserver).getUpdateCounter_(),1);
    }
    
    
    
    @Test
    public void CanUpdateNotSelectedSingleGroupRepeatedly() {
        testSubject.addGruppenObserver(testObserver);
        ((TestObserver)testObserver).setUpdateCounter_(0);
        List<Gruppe> gruppen = testSubject.getGruppen();
        testSubject.setGruppen(gruppen);
        testSubject.setGruppen(gruppen);
        assertEquals(((TestObserver)testObserver).getUpdateCounter_(),2);
    }
    
    
    
    @Test
    public void CanUpdateSelectedSingleGroup() {
        testSubject.addGruppenObserver(testObserver);
        ((TestObserver)testObserver).setUpdateSelectedCounter_(0);
        testSubject.setSelectedGruppe(testSubject.getSelectedGruppe());
        assertEquals(((TestObserver)testObserver).getUpdateSelectedCounter_(),1);
    }
    
    
    
    @Test
    public void CanUpdateSelectedSingleGroupRepeatedly() {
        testSubject.addGruppenObserver(testObserver);
        ((TestObserver)testObserver).setUpdateSelectedCounter_(0);
        Gruppe gruppen = testSubject.getSelectedGruppe();
        testSubject.setSelectedGruppe(gruppen);
        testSubject.setSelectedGruppe(gruppen);
        assertEquals(((TestObserver)testObserver).getUpdateSelectedCounter_(),2);
    }
    
    
    @Test
    public void CanUpdateNotSelectedMultipleGroup() {
        testSubject.addGruppenObserver(testObserver2);
        testSubject.addGruppenObserver(testObserver);
        ((TestObserver)testObserver).setUpdateCounter_(0);
        ((TestObserver)testObserver2).setUpdateCounter_(0);
        testSubject.setGruppen(testSubject.getGruppen());
        assertEquals(((TestObserver)testObserver).getUpdateCounter_(),1);
        assertEquals(((TestObserver)testObserver2).getUpdateCounter_(),1);
    }
    
    
    
    @Test
    public void CanUpdateNotSelectedMultipleGroupRepeatedly() {
        testSubject.addGruppenObserver(testObserver);
        testSubject.addGruppenObserver(testObserver2);
        ((TestObserver)testObserver).setUpdateCounter_(0);
        ((TestObserver)testObserver2).setUpdateCounter_(0);
        List<Gruppe> gruppen = testSubject.getGruppen();
        testSubject.setGruppen(gruppen);
        testSubject.setGruppen(gruppen);
        assertEquals(((TestObserver)testObserver).getUpdateCounter_(),2);
        assertEquals(((TestObserver)testObserver2).getUpdateCounter_(),2);
    }
    
    
    
    @Test
    public void CanUpdateSelectedMultipleGroup() {
        testSubject.addGruppenObserver(testObserver);
        testSubject.addGruppenObserver(testObserver2);
        ((TestObserver)testObserver).setUpdateSelectedCounter_(0);
        ((TestObserver)testObserver2).setUpdateSelectedCounter_(0);
        testSubject.setSelectedGruppe(testSubject.getSelectedGruppe());
        assertEquals(((TestObserver)testObserver).getUpdateSelectedCounter_(),1);
        assertEquals(((TestObserver)testObserver2).getUpdateSelectedCounter_(),1);
    }
    
    
    
    @Test
    public void CanUpdateSelectedMultipleGroupRepeatedly() {
        testSubject.addGruppenObserver(testObserver);
        testSubject.addGruppenObserver(testObserver2);
        ((TestObserver)testObserver).setUpdateSelectedCounter_(0);
        ((TestObserver)testObserver2).setUpdateSelectedCounter_(0);
        Gruppe gruppen = testSubject.getSelectedGruppe();
        testSubject.setSelectedGruppe(gruppen);
        testSubject.setSelectedGruppe(gruppen);
        assertEquals(((TestObserver)testObserver).getUpdateSelectedCounter_(),2);
        assertEquals(((TestObserver)testObserver2).getUpdateSelectedCounter_(),2);
    }
}
