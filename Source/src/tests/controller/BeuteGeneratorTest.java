package tests.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Gegenstand;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Beute;

public class BeuteGeneratorTest extends Beute{
    List<Gegenstand> sortedItems;
    
    @Before
    public void ensureItems() {
        sortedItems = new ArrayList<Gegenstand>();
        int amount = 30;
        for(int i = 0; i < amount; ++i) {
            Gegenstand item = new Gegenstand();
            item.setName_(String.valueOf(i));
            item.setKosten_(i);
            item.addToDB();
            sortedItems.add(item);
        }
    }
    
    
    
    @Test
    public void getRandomItemWithMaxValueTest() {
        for(int i = 0; i < 20; ++i) {
            Gegenstand beute = super.getRandomItemWithMaxValue(3, sortedItems);
            assertTrue(beute.getKosten_() <= 3);
        }
    }
    
    
    
    @Test
    public void getInventarBeuteTest() {
        int gesamtwert = 10000, streuung = 50, inventarWert = 0;
        Beute beute = new Beute(gesamtwert,streuung);
        beute.generateInventarBeute();
        for(Gegenstand item : beute.getInventarBeute()) {
            inventarWert += item.getKosten_();
        }
        assertTrue(inventarWert == beute.getInventarWert());
        int wertAbweichung = java.lang.Math.abs(beute.getGeldWert() + beute.getInventarWert() - gesamtwert);
        assertTrue(wertAbweichung <= streuung);
    }
    
    
    
    @Test
    public void splitGesamtWertTest() {
        Beute beute = new Beute();
        int sum = 0;
        for(int i = 0; i < 100; ++i) {
            beute.splitGesamtWert(100);
            sum += beute.getGeldWert();
        }
        assertTrue(sum/100. > 39.);
    }
    
    
    
    @After
    public void deleteItems() {
        for(Gegenstand item : sortedItems) {
            item.deleteFromDB();
        }
    }
}
