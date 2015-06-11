package tests.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.EinfacherGegenstand;

import org.junit.Ignore;
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
    
    
    @Test
    public void writeIntoKategorien() {
        EinfacherGegenstand firstGegenstand = new EinfacherGegenstand();
        firstGegenstand.setName_("Knüppel");
        firstGegenstand.setKategorie_("Hiebwaffe");
        firstGegenstand.addToDB();
        
        List<EinfacherGegenstand> alleGegenstaende = EinfacherGegenstand.getAll();
        assertTrue(alleGegenstaende.contains(firstGegenstand));
        assertTrue(firstGegenstand.getKategorie_() == "Hiebwaffe");
        firstGegenstand.deleteFromDB();
    }
    
    
    
    @Test
    public void getKategorienTest() {
        EinfacherGegenstand gegenstandA = new EinfacherGegenstand();
        EinfacherGegenstand gegenstandB = new EinfacherGegenstand();
        gegenstandA.setKategorie_("A");
        gegenstandB.setKategorie_("B");
        
        List<EinfacherGegenstand> gegenstand = Arrays.asList(gegenstandA,gegenstandB);
        List<String> expected = Arrays.asList("A","B");
        List<String> actual = EinfacherGegenstand.getKategorien(gegenstand);
        assertEquals(expected, actual);
        
    }
    
    
    
    @Test
    public void getSubKategoriesTest() {
        String bsp = "Waffen.Schwert.Säbel.Koalabär";
        List<String> actual = EinfacherGegenstand.getSubKategories(bsp);
        List<String> expected = Arrays.asList("Waffen","Schwert","Säbel","Koalabär");
        assertEquals(actual,expected);
    }
}
