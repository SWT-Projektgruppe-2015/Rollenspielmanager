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
    
    @Ignore
    @Test
    public void ensureDefaultWaffenDmg() {
        assertTrue(normalerGegner.getDamage() == 0);
//        this.normalerGegner.setDmg(100);
//        assertTrue(normalGegner.getDmg(100));
    }
    
}
