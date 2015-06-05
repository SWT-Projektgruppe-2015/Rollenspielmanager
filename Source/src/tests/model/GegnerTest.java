package tests.model;

import static org.junit.Assert.*;
import model.GegnerTyp;

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
        assertTrue(normalerGegner.getDefH() == 1);
        assertTrue(normalerGegner.getDefR() == 1);
        assertTrue(normalerGegner.getDefS() == 0);
        assertTrue(normalerGegner.getErfahrung_() == 1);
        assertTrue(normalerGegner.getKreis_() == 1);
        assertTrue(normalerGegner.getLevel_() == 0);
        assertTrue(normalerGegner.getGeschick_() == 1);
        assertTrue(normalerGegner.getStaerke_() == 1);
    }
    
    
    
    @Test
    public void ensureDefaultWaffenDmg() {
        assertTrue(normalerGegner.getSchaden_() == 0);
    }
    
    
    
    @Test
    public void damageCanBeChanged() {
        GegnerTyp gegnerWithDamage = new GegnerTyp();
        gegnerWithDamage.addToDB();
        
        gegnerWithDamage.setSchaden_(100);
        assertTrue(gegnerWithDamage.getSchaden_() == 100);
        
        gegnerWithDamage.deleteFromDB();
    }
    
    
    
    @Test
    public void correctOrderWithSamePrefix() {
        GegnerTyp firstGegner = new GegnerTyp();
        firstGegner.setName_("Adam");
        GegnerTyp secondGegner = new GegnerTyp();
        secondGegner.setName_("Adam E.");
        
        assertTrue(firstGegner.compareTo(secondGegner) < 0);
    }
    
    
    
    @Test
    public void correctOrderWithDifferentPrefix() {
        GegnerTyp firstGegner = new GegnerTyp();
        firstGegner.setName_("Adam");
        GegnerTyp secondGegner = new GegnerTyp();
        secondGegner.setName_("Bert");
        
        assertTrue(firstGegner.compareTo(secondGegner) < 0);
    }
}
