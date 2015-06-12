package tests.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import model.Gegenstand;

import org.junit.Test;

public class EinfacherGegenstandTest {
    @Test
    public void correctOrderWithSamePrefix() {
        Gegenstand firstGegenstand = new Gegenstand();
        firstGegenstand.setName_("Schwert");
        Gegenstand secondGegenstand = new Gegenstand();
        secondGegenstand.setName_("Schwert aus Elfenstahl");
        
        assertTrue(firstGegenstand.compareTo(secondGegenstand) < 0);
    }
    
    
    
    @Test
    public void correctOrderWithDifferentPrefix() {
        Gegenstand firstGegenstand = new Gegenstand();
        firstGegenstand.setName_("Kn�ppel");
        Gegenstand secondGegenstand = new Gegenstand();
        secondGegenstand.setName_("Schwert");
        
        assertTrue(firstGegenstand.compareTo(secondGegenstand) < 0);
    }
    
    
    @Test
    public void writeIntoKategorien() {
        Gegenstand firstGegenstand = new Gegenstand();
        firstGegenstand.setName_("Kn�ppel");
        firstGegenstand.setKategorie_("Hiebwaffe");
        firstGegenstand.addToDB();
        
        List<Gegenstand> alleGegenstaende = Gegenstand.getAll();
        assertTrue(alleGegenstaende.contains(firstGegenstand));
        assertTrue(firstGegenstand.getKategorie_() == "Hiebwaffe");
        firstGegenstand.deleteFromDB();
    }
    
    
    
    @Test
    public void getKategorienTest() {
        Gegenstand gegenstandA = new Gegenstand();
        Gegenstand gegenstandB = new Gegenstand();
        gegenstandA.setKategorie_("A");
        gegenstandB.setKategorie_("B");
        
        List<Gegenstand> gegenstand = Arrays.asList(gegenstandA,gegenstandB);
        List<String> expected = Arrays.asList("A","B");
        List<String> actual = Gegenstand.getKategorien(gegenstand);
        assertEquals(expected, actual);
        
    }
    
    
    
    @Test
    public void getSubKategoriesTest() {
        String bsp = "Waffen.Schwert.S�bel.Koalab�r";
        List<String> actual = Gegenstand.getSubKategories(bsp);
        List<String> expected = Arrays.asList("Waffen","Schwert","S�bel","Koalab�r");
        assertEquals(actual,expected);
    }
}
