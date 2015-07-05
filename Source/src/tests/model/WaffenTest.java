package tests.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.Waffen;

import org.junit.Test;

public class WaffenTest {
    @Test
    public void correctOrderWithSamePrefix() {
        Waffen firstWaffe = new Waffen();
        firstWaffe.setWaffenName_("Schwert");
        Waffen secondWaffe = new Waffen();
        secondWaffe.setWaffenName_("Schwert aus Elfenstahl");
        
        assertTrue(firstWaffe.compareTo(secondWaffe) < 0);
    }
    
    
    
    @Test
    public void correctOrderWithDifferentPrefix() {
        Waffen firstWaffe = new Waffen();
        firstWaffe.setWaffenName_("Knüppel");
        Waffen secondWaffe = new Waffen();
        secondWaffe.setWaffenName_("Schwert");
        
        assertTrue(firstWaffe.compareTo(secondWaffe) < 0);
    }
    
    
    
    @Test
    public void isAoETest() {
        Waffen waffe = new Waffen();
        assertFalse(waffe.isAoE());
        
        waffe.setEffektTyp_(Waffen.EffektTyp.AOE_SCHADEN_RUA);
        assertTrue(waffe.isAoE());
        waffe.setEffektTyp_(Waffen.EffektTyp.AOE_SCHADEN_RUE);
        assertTrue(waffe.isAoE());
        
        waffe.setEffektTyp_(Waffen.EffektTyp.MALUS_GESCHICK);
        assertFalse(waffe.isAoE());
        waffe.setEffektTyp_(Waffen.EffektTyp.MALUS_STAERKE);
        assertFalse(waffe.isAoE());
        waffe.setEffektTyp_(Waffen.EffektTyp.RUA_SCHADEN);
        assertFalse(waffe.isAoE());
        waffe.setEffektTyp_(Waffen.EffektTyp.NO_EFFEKT);
        assertFalse(waffe.isAoE());
    }
}
