package tests.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Gegenstand;

import org.junit.Test;

public class GegenstandTest {
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
        firstGegenstand.setName_("Knüppel");
        Gegenstand secondGegenstand = new Gegenstand();
        secondGegenstand.setName_("Schwert");
        
        assertTrue(firstGegenstand.compareTo(secondGegenstand) < 0);
    }
    
    
    @Test
    public void writeIntoKategorien() {
        Gegenstand firstGegenstand = new Gegenstand();
        firstGegenstand.setName_("Knï¿½ppel");
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
        String bsp = "Waffen.Schwert.Sï¿½bel.Koalabï¿½r";
        List<String> actual = Gegenstand.getSubKategories(bsp);
        List<String> expected = Arrays.asList("Waffen","Schwert","Sï¿½bel","Koalabï¿½r");
        assertEquals(actual,expected);
    }
    
    
    
    @Test
    public void settersTest() {
        Gegenstand gegenstandA = new Gegenstand();
        setDefaultGegenstand(gegenstandA);
        assertTrue(verifyDefaultGegenstand(gegenstandA));
    }
    
    
    
    @Test
    public void ausruestungIsValidTest() {
        Gegenstand gegenstandA = new Gegenstand();
        assertTrue(gegenstandA.isValid());
        gegenstandA.setKosten_(-1);
        assertTrue(!gegenstandA.isValid());
    }
    
    
    
    @Test
    public void getFullSubkategoriePathTest() {
        List<Gegenstand> items = getSomeGegenstaende(3);
        items.get(0).setKategorie_("A");
        items.get(1).setKategorie_("A.B");
        items.get(2).setKategorie_("A.B.C");
        String path = Gegenstand.getFullSubkategoriePath("B", items);
        assertEquals(path,"A.B");
        path = Gegenstand.getFullSubkategoriePath("D", items);
        assertEquals(path, null);
    }



    private List<Gegenstand> getSomeGegenstaende(int amount) {
        List<Gegenstand> items = new ArrayList<Gegenstand>();
        for(int i = 0; i < amount; ++i) {
            items.add(new Gegenstand());
        }
        return items;
    }



    private boolean verifyDefaultGegenstand(Gegenstand gegenstandA) {
        boolean verifyDefault = gegenstandA.getName_() == "A"
                && gegenstandA.getStaerke_() == 30 
                && gegenstandA.getBeschreibung_() == "super"
                && gegenstandA.getKosten_() == 10
                && gegenstandA.getTraglast_() == 10
                && gegenstandA.getWert_() == "100 + W6";
        return verifyDefault;
    }



    private void setDefaultGegenstand(Gegenstand gegenstandA) {
        gegenstandA.addToDB();
        gegenstandA.setName_("A");
        gegenstandA.setStaerke_(30);
        gegenstandA.setBeschreibung_("super");
        gegenstandA.setKosten_(10);
        gegenstandA.setTraglast_(10);
        gegenstandA.setWert_("100 + W6");
        gegenstandA.setKategorie_("neue Kategorie");
    }
}
