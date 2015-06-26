package tests.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Gegenstand;

import org.junit.Test;

import view.controller.Hauptprogramm;

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
        firstGegenstand.setName_("Kn" + Hauptprogramm.UMLAUT_SMALL_UE + "ppel");
        Gegenstand secondGegenstand = new Gegenstand();
        secondGegenstand.setName_("Schwert");
        
        assertTrue(firstGegenstand.compareTo(secondGegenstand) < 0);
    }
    
    
    @Test
    public void writeIntoKategorien() {
        Gegenstand firstGegenstand = new Gegenstand();
        firstGegenstand.setName_("Kn" + Hauptprogramm.UMLAUT_SMALL_UE + "ppel");
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
        String bsp = "Waffe/Schwert/S"+ Hauptprogramm.UMLAUT_SMALL_AE + "bel/Koalab" + Hauptprogramm.UMLAUT_SMALL_AE + "r";
        List<String> actual = Gegenstand.getSubKategories(bsp);
        List<String> expected = Arrays.asList("Waffe","Schwert","S"+ Hauptprogramm.UMLAUT_SMALL_AE + "bel","Koalab" + Hauptprogramm.UMLAUT_SMALL_AE + "r");
        assertEquals(actual,expected);
    }
    
    
    
     @Test
     public void isAusreustungTest() {
         Gegenstand gegenstandA = new Gegenstand();
         gegenstandA.setKategorie_("Waffe/Schwert/S"+ Hauptprogramm.UMLAUT_SMALL_AE + "bel/Koalab" + Hauptprogramm.UMLAUT_SMALL_AE + "r");
         assertTrue(gegenstandA.isAusruestung());
         gegenstandA.setKategorie_("R"+ Hauptprogramm.UMLAUT_SMALL_UE + "stung/Superhemd/Koalab" + Hauptprogramm.UMLAUT_SMALL_AE + "r");
         assertTrue(gegenstandA.isContainedInKategorie("Superhemd"));
         assertTrue(gegenstandA.isAusruestung());
     }
     
     
     
     @Test
     public void getSearchMatchingKategorienTest() {
         List<String> kategorien = new ArrayList<String>();
         kategorien.add("Waffe/Langschwert/S"+ Hauptprogramm.UMLAUT_SMALL_AE + "bel/Koalab" + Hauptprogramm.UMLAUT_SMALL_AE + "r");
         kategorien.add("Waffe/SuperDuper");
         kategorien.add("Nichts");
         List<String> actual = Gegenstand.getSearchMatchingKategorien("Schwert", kategorien);
         List<String> expected = new ArrayList<String>();
         expected.add("Waffe/Langschwert/S"+ Hauptprogramm.UMLAUT_SMALL_AE + "bel/Koalab"+ Hauptprogramm.UMLAUT_SMALL_AE + "r");
         System.out.println(actual.get(0));
         assertEquals(actual, expected);
         actual = Gegenstand.getSearchMatchingKategorien("", kategorien);
         assertEquals(actual, kategorien);
     }
     
     
     
     @Test
     public void sortByKostenTest() {
         List<Gegenstand> items = getSomeGegenstaende(3);
         items.get(0).setKosten_(6);
         items.get(1).setKosten_(7);
         items.get(2).setKosten_(5);
         Gegenstand.sortByKosten(items);
         assertEquals(items.get(0).getKosten_(), 5);
         assertEquals(items.get(1).getKosten_(), 6);
         assertEquals(items.get(2).getKosten_(), 7);
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
        items.get(1).setKategorie_("A/B");
        items.get(2).setKategorie_("A/B/C");
        String path = Gegenstand.getFullSubkategoriePath("B", items);
        assertEquals(path,"A/B");
        path = Gegenstand.getFullSubkategoriePath("D", items);
        assertEquals(path, null);
    }
    
    
    
//    @Test
//    public void getAllAusruestungTest() {
//        List<Gegenstand> testItems = getSomeGegenstaende(2);
//        testItems.get(0).setKategorie_("Waffe/Schwert");
//        testItems.get(1).setKategorie_("R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung/Helm");
//        List<Gegenstand> items = Gegenstand.getAllInventar();
//        assertTrue(items.contains(testItems.get(0)));
//    }



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
