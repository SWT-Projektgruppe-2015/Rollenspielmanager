package tests.model;

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
}
