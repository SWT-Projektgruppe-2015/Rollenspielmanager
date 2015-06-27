package tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.AusruestungBeute;
import model.Gegenstand;
import model.GegnerEinheit;
import model.GegnerTyp;

public class AusruestungBeuteTest extends AusruestungBeute {
    private List<Gegenstand> sortedItems;
    private final int amount = 30;
    private GegnerTyp typ_;
    
    public AusruestungBeuteTest() {
        super(50,10);
    }
    
    @Before
    public void ensureItems() {
        sortedItems = new ArrayList<Gegenstand>();
        generateItems(Gegenstand.WAFFE);
        generateRuestungsItems();
        generateItems(Gegenstand.HELM);
        createDefensiveGegnerTyp();
    }

    private void generateItems(String kategorie) {
        for(int i = 0; i < amount; ++i) {
            Gegenstand item = new Gegenstand();
            item.setName_(String.valueOf(i));
            item.setKosten_(i);
            item.setWert_(Integer.toString(i*10)+"+W9");
            item.setKategorie_(kategorie);
            item.addToDB();
            sortedItems.add(item);
        }
    }
    
    
    
    @After
    public void cleanUp() {
        typ_.deleteFromDB();
        for(Gegenstand item : sortedItems) {
            item.deleteFromDB();
        }
    }
    
    
    
    @Test
    public void getItemWithApproximateValueTest() {
        List<Gegenstand> someItems = sortedItems.subList(0, amount);
        for(int i = 0; i < 20; ++i) {
            Gegenstand beute = super.getItemWithApproximateValue(i*20-1, someItems);
            if(i*20 < amount*10) {
                assertTrue(beute.equals(someItems.get(2*i)));
            } else {
                assertTrue(beute.equals(someItems.get(amount-1)));
            }
        }
    }
    
    
    
    @Test
    public void generateWaffenBeuteTest() {
        Gegenstand waffe = generateWaffenBeute(150);
        int expectedValue = (int)(150*getMalusFactor());
        assertTrue(waffe.computeValue() >= expectedValue-10);
        assertTrue(waffe.computeValue() <= expectedValue+10);
    }


    @Test
    public void generateRuestungsBeuteTest() {
        int ruestungsTeilValue;
        for(int i = 0; i<20; i++) {
            Gegenstand ruestung = generateRuestungsBeute(100);
            if(ruestung.getKategorie_().contains(Gegenstand.HARNISCH))
                ruestungsTeilValue = 50;
            else
                ruestungsTeilValue = 16;
            
            int expectedValue = (int)(ruestungsTeilValue*getMalusFactor());
            assertTrue(ruestung.computeValue() >= expectedValue-10);
            assertTrue(ruestung.computeValue() <= expectedValue+10);
        }
    }

    
    
    @Test
    public void generateHelmBeuteTest() {
        Gegenstand helm = generateHelmBeute(50);
        int expectedValue = (int)(50*getMalusFactor());
        assertTrue(helm.computeValue() >= expectedValue-10);
        assertTrue(helm.computeValue() <= expectedValue+10);
    }
    
    
    
    @Test
    public void beuteDistributionIsAsExpected() {
        GegnerEinheit einheit = GegnerEinheit.createEinheiten(typ_, 1).get(0);
        int helmCount = 0;
        int waffenCount = 0;
        int tries = 1000;
        for(int i = 0; i<tries; i++) {
            Gegenstand beute = generateAusruestungBeute(einheit);
            if(beute.isContainedInKategorie(Gegenstand.WAFFE)) {
                waffenCount++;
            } else if(beute.isContainedInKategorie(Gegenstand.HELM)) {
                helmCount++;
            }
        }
        
        assertTrue(waffenCount < tries * WAFFEN_RATIO * 1.1);
        assertTrue(waffenCount > tries * WAFFEN_RATIO * .9);
        assertTrue(helmCount < tries * HELM_RATIO * 1.2);
        assertTrue(helmCount > tries * HELM_RATIO * .8);
    }
    
    
    
    @Test
    public void ratiosAreConsistent() {
        assertEquals(HELM_RATIO 
                + WAFFEN_RATIO 
                + HANDSCHUH_RATIO + HARNISCH_RATIO + SCHUH_RATIO + GUERTEL_RATIO, 1, 0.001);
    }
    
    
    private void generateRuestungsItems() {
        generateItems(Gegenstand.RUESTUNG + "\\" + Gegenstand.HARNISCH);
        generateItems(Gegenstand.RUESTUNG + "\\" + Gegenstand.HANDSCHUH);
        generateItems(Gegenstand.RUESTUNG + "\\" + Gegenstand.SCHUH);
        generateItems(Gegenstand.RUESTUNG + "\\" + Gegenstand.GUERTEL);
    }
    
    
    
    private void createDefensiveGegnerTyp() {
        typ_ = new GegnerTyp();
        typ_.setDefH(50);
        typ_.setDefR(200);
        typ_.setDefS(20);
        typ_.setSchaden_(150);
        typ_.addToDB();
    }
    
    
}
