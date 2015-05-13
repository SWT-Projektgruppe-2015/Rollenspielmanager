package tests.controller;

import javax.persistence.EntityManager;

import model.Spieler;
import model.Waffen;
import controller.EntityManagerFactoryProvider;
import controller.manipulators.SpielerManipulator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SpielerManipulatorTest {
    
    private static SpielerManipulator spielerManipulator;
    private static Spieler testSpieler;
    private static EntityManager theManager;
    
    
    
    @BeforeClass
    public static void setUpBeforeClass() {
        spielerManipulator = SpielerManipulator.getInstance();
        testSpieler = new Spieler();
        theManager = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
    }
    
    
    
    @Test
    public void CanMakeInstance() {
        assertNotNull(spielerManipulator);
    }
    
    
    
    @Test
    public void twoInstancesAreSame() {
        SpielerManipulator testInstance2 = SpielerManipulator.getInstance();
        assertSame(spielerManipulator, testInstance2);
    }
    
    
    
    @Test
    public void canAddSpieler() {
        testSpieler = new Spieler();
        assertTrue("Couldn't add new Spieler.", spielerManipulator.add(testSpieler));
    }
    
    
    
    /**
     * Ignoriert bis man rausbekommt wie man die Exception ausloest.
     */
    @Ignore
    @Test
    public void causeEntityExistsException() {
        spielerManipulator.add(testSpieler);
        assertFalse("Can add same player twice.", spielerManipulator.add(testSpieler));
    }
    
    
    
    @Test
    public void cantAddNonExistantSpieler() {
        assertFalse("Can add non existant Spieler", spielerManipulator.add(null));
    }
    
    
    
    @Test
    public void canDeleteSpieler() {
        if(spielerManipulator.add(testSpieler))   {
            assertTrue("Can't delete Spieler", spielerManipulator.delete(testSpieler));
        }
    }
    
    
    
    @Test
    public void canDeleteSpielerWithWaffe() {
        Spieler spielerWithWaffe = new Spieler();
        spielerWithWaffe.addToDB();
        Waffen testWaffe = new Waffen();
        spielerWithWaffe.addWaffe(testWaffe);
        
        assertTrue(spielerManipulator.delete(spielerWithWaffe));
    }
    
    @Test
    public void cantDeleteNonExistantSpieler() {
        assertFalse("Can delete non existant Spieler",
                spielerManipulator.delete(null));
    }
    
    
    
    @Test
    public void canUpdateSpieler() {
        if (spielerManipulator.add(testSpieler)) {
            testSpieler.setName_("Spieler formerly known as Jane");
            assertTrue(spielerManipulator.update(testSpieler));
            spielerManipulator.delete(testSpieler);
        }
    }
    
    
    
    @Test
    public void cantUpdateNonExistantSpieler() {
        assertFalse("Can update non existant Spieler",
                spielerManipulator.update(null));
    }
    
    
    
    @Test
    public void canGetSpielerList()     {
        assertNotNull(spielerManipulator.getAll());
    }
    
    
    
    @AfterClass
    public static void cleanUp()    {
        Spieler cleanUpCrew = theManager.find(Spieler.class, testSpieler.getID_());
        theManager.getTransaction().begin();
        if(cleanUpCrew != null)
            theManager.remove(cleanUpCrew);
        theManager.getTransaction().commit();
    }
}
