package tests.model;

import static org.junit.Assert.*;

import java.util.List;

import model.GegnerTyp;
import model.Ausruestung;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GegnerTest {
    private GegnerTyp normalerGegner;
    
    @Before
    public void initObjects() {
        normalerGegner = new GegnerTyp();
        normalerGegner.addToDB();
    }
    
    @After
    public void deleteFromDB() {
        normalerGegner.deleteFromDB();
    }
    
    /**
     * 
     */
    
    @Test
    public void checkGegnerInitialisation() {
        assertEquals(normalerGegner.getDefH(), 1);
        assertEquals(normalerGegner.getDefR(), 1);
        assertEquals(normalerGegner.getDefS(), 0);
        assertEquals(normalerGegner.getErfahrung_(), 1);
        assertEquals(normalerGegner.getKreis_(), 1);
        assertEquals(normalerGegner.getLevel_(), 0);
        assertEquals(normalerGegner.getGeschick_(), 1);
        assertEquals(normalerGegner.getStaerke_(), 1);
    }
    
    @Test
    public void onCreateTest(){
        GegnerTyp neuerGegner = new GegnerTyp();
        defineOnCreateConflict(neuerGegner);
        
        neuerGegner.addToDB();
        assertEquals(neuerGegner.getKreis_(),1);
        assertEquals(neuerGegner.getErfahrung_(), 1);
        assertEquals(neuerGegner.getStaerke_(), 1);
        assertEquals(neuerGegner.getGeschick_(), 1);
        assertEquals(neuerGegner.getSchaden_(), 0);
        neuerGegner.deleteFromDB();
        
    }

    private void defineOnCreateConflict(GegnerTyp neuerGegner) {
        neuerGegner.setName_(null);
        neuerGegner.setKreis_(0);
        neuerGegner.setErfahrung_(0);
        neuerGegner.setStaerke_(0);
        neuerGegner.setGeschick_(0);
        neuerGegner.setSchaden_(-1);
    }
    
    
    
    @Test
    public void ensureDefaultWaffenDmg() {
        assertEquals(normalerGegner.getSchaden_(), 0);
    }
    
    
    
    @Test
    public void damageCanBeChanged() {
        GegnerTyp gegnerWithDamage = new GegnerTyp();
        gegnerWithDamage.addToDB();
        
        gegnerWithDamage.setSchaden_(100);
        assertEquals(gegnerWithDamage.getSchaden_(), 100);
        
        gegnerWithDamage.deleteFromDB();
    }
    
    
    
    @Test
    public void correctOrderWithSamePrefix() {
        GegnerTyp firstGegner = new GegnerTyp();
        firstGegner.setName_("Adam");
        GegnerTyp secondGegner = new GegnerTyp();
        secondGegner.setName_("Adam E.");
        
        boolean test = firstGegner.compareTo(secondGegner) < 0;
        assertEquals(test, true);
    }
    
    
    
    @Test
    public void correctOrderWithDifferentPrefix() {
        GegnerTyp firstGegner = new GegnerTyp();
        firstGegner.setName_("Adam");
        GegnerTyp secondGegner = new GegnerTyp();
        secondGegner.setName_("Bert");
        
        assertTrue(firstGegner.compareTo(secondGegner) < 0);
    }
    
    // Line coverage for not important setters and getters.
    @Test
    public void coverNotImportantModifiers() {
        this.normalerGegner.getDefH();
        this.normalerGegner.getDefR();
        this.normalerGegner.getDefS();
        this.normalerGegner.getErfahrung_();
        this.normalerGegner.getID_();
        this.normalerGegner.getGeschick_();
        this.normalerGegner.getLevel_();
        this.normalerGegner.getMaxLebenspunkte_();
        this.normalerGegner.setDefH(1);
        this.normalerGegner.setDefR(1);
        this.normalerGegner.setDefS(1);
        this.normalerGegner.setErfahrung_(1);
        this.normalerGegner.setID_(0);
        this.normalerGegner.setGeschick_(0);
        this.normalerGegner.setLevel_(-2);
        this.normalerGegner.setMaxLebenspunkte_(-2);
        this.normalerGegner.setAusruestung_(new Ausruestung());
        this.normalerGegner.setAusruestung_(null);
        this.normalerGegner.setAusruestung_(this.normalerGegner.getAusruestung_());
    }
    
    
    @Test
    public void getAllTest() {
        List<GegnerTyp> allGegner = GegnerTyp.getAll();
        assertTrue(allGegner.contains(normalerGegner));
    }
    
    
    @Test
    public void detailsAreValidTest() {
        assertTrue(!GegnerTyp.detailsAreValid(-1, 5, 0, 0, 0));
    }
}
