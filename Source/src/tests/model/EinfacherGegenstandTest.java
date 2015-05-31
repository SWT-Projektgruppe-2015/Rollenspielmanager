package tests.model;

import static org.junit.Assert.assertTrue;
import model.EinfacherGegenstand;

import org.junit.Test;

public class EinfacherGegenstandTest {
    @Test
    public void correctOrderWithSamePrefix() {
        EinfacherGegenstand firstGegenstand = new EinfacherGegenstand();
        firstGegenstand.setName_("Schwert");
        EinfacherGegenstand secondGegenstand = new EinfacherGegenstand();
        secondGegenstand.setName_("Schwert aus Elfenstahl");
        
        assertTrue(firstGegenstand.compareTo(secondGegenstand) < 0);
    }
    
    
    
    @Test
    public void correctOrderWithDifferentPrefix() {
        EinfacherGegenstand firstGegenstand = new EinfacherGegenstand();
        firstGegenstand.setName_("Knüppel");
        EinfacherGegenstand secondGegenstand = new EinfacherGegenstand();
        secondGegenstand.setName_("Schwert");
        
        assertTrue(firstGegenstand.compareTo(secondGegenstand) < 0);
    }
}
