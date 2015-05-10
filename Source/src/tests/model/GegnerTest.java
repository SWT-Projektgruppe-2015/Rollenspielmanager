package tests.model;

import static org.junit.Assert.*;
import model.Gegner;
import model.Spieler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GegnerTest {
    private Gegner normalerGegner;
    
    @Before
    public void initObjects() {
        normalerGegner = new Gegner();
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
        normalerGegner = new Gegner();
        assertTrue(normalerGegner.getDamage() == 0);
    }
    
    
    
    @Test
    public void damageCanBeChanged() {
        Gegner gegnerWithDamage = new Gegner();
        gegnerWithDamage.addToDB();
        
        gegnerWithDamage.setDamage(100);
        assertTrue(gegnerWithDamage.getDamage() == 100);
        
        gegnerWithDamage.deleteFromDB();
    }
    
}
