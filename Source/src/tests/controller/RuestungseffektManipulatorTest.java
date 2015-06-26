package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Ausruestung;
import model.Ruestungseffekt;
import controller.EntityManagerFactoryProvider;
import controller.manipulators.AusruestungsManipulator;
import controller.manipulators.RuestungseffektManipulator;

public class RuestungseffektManipulatorTest {
    private static RuestungseffektManipulator ruestungsEffektManipulator_;
    private static Ruestungseffekt testEffekt_;
    private static EntityManager theManager_;
    private static Ausruestung testAusruestung_;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ruestungsEffektManipulator_ = RuestungseffektManipulator.getInstance();
        theManager_ = EntityManagerFactoryProvider.getFactory()
                .createEntityManager();
        testAusruestung_ = new Ausruestung();
        AusruestungsManipulator.getInstance().add(testAusruestung_);
    }
    
    
    
    @AfterClass
    public static void cleanUp() {
        Ausruestung cleanUpCrew = theManager_.find(Ausruestung.class, testAusruestung_.getID_());
        theManager_.getTransaction().begin();
        theManager_.remove(cleanUpCrew);
        theManager_.getTransaction().commit();
    }
    
    
    
    @Test
    public void getInstanceTest() {
        ruestungsEffektManipulator_ = RuestungseffektManipulator.getInstance();
        assertNotNull(ruestungsEffektManipulator_);
    }
    
    
    
    @Test
    public void twoInstanceAreSame() {
        RuestungseffektManipulator testInstanceTwo = RuestungseffektManipulator.getInstance();
        assertSame(testInstanceTwo, ruestungsEffektManipulator_);
    }
    
    
    
    @Test
    public void testAdd() {
        testEffekt_ = new Ruestungseffekt();
        testEffekt_.setAusruestung_(testAusruestung_);
        assertTrue(ruestungsEffektManipulator_.add(testEffekt_));
        ruestungsEffektManipulator_.delete(testEffekt_);
    }
    
    
    
    @Test
    public void testNullAdd() {
        assertFalse(ruestungsEffektManipulator_.add(null));
    }
    
    
    
    @Test
    public void testDelete() {
        testEffekt_ = new Ruestungseffekt();
        testEffekt_.setAusruestung_(testAusruestung_);
        if(ruestungsEffektManipulator_.add(testEffekt_)) {
            assertTrue(ruestungsEffektManipulator_.delete(testEffekt_));
        }
        else {
            fail("This is fail.");
        }
    }
    
    
    
    @Test 
    public void testNullToDelete() {
        assertFalse(ruestungsEffektManipulator_.delete(null));
    }
    
    
    
    @Test
    public void canUpdateEffekt() {
        testEffekt_ = new Ruestungseffekt();
        AusruestungsManipulator.getInstance().add(testEffekt_.getAusruestung_());
        if(ruestungsEffektManipulator_.add(testEffekt_)) {
            testEffekt_.setEffektWert_(100);
            assertTrue("Can't update", ruestungsEffektManipulator_.update(testEffekt_));
            ruestungsEffektManipulator_.delete(testEffekt_);
        }
        else    {
            fail();
        }
    }
    
    
    
    @Test
    public void cantUpdateNonExistantEffekt() {
        assertFalse("Can update non existant Effect ", ruestungsEffektManipulator_.update(null));
    }
    
    
    
    @Test
    public void getEffekteForAusruestung() {
        Ausruestung testAusruestung = new Ausruestung();
        testAusruestung.addToDB();
        Ruestungseffekt effekt1 = new Ruestungseffekt();
        effekt1.setEffektTyp_(Ruestungseffekt.EffektTyp.EXP_BOOST);
        effekt1.setAusruestung_(testAusruestung);
        effekt1.addToDB();
        Ruestungseffekt effekt2 = new Ruestungseffekt();
        effekt2.setEffektWert_(200);
        effekt2.setAusruestung_(testAusruestung);
        effekt2.addToDB();

        List<Ruestungseffekt> effektForAusruestung = ruestungsEffektManipulator_.getRuestungsEffekteForAusruestung(testAusruestung);
        assertTrue(effektForAusruestung.contains(effekt1));
        assertTrue(effektForAusruestung.contains(effekt2));
        assertTrue(effektForAusruestung.size() == 2);
        
        effekt1.deleteFromDB();
        effekt2.deleteFromDB();
        testAusruestung.deleteFromDB();
    }
}
