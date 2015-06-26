package tests.model;

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
        for(int i = 0; i < amount; ++i) {
            Gegenstand item = new Gegenstand();
            item.setName_(String.valueOf(i));
            item.setKosten_(i);
            item.setWert_(Integer.toString(i*10)+"+W9");
            item.setKategorie_(i % 2 == 0 ? Gegenstand.WAFFE : Gegenstand.RUESTUNG);
            item.addToDB();
            sortedItems.add(item);
        }
        createDefensiveGegnerTyp();
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
        for(int i = 0; i < 20; ++i) {
            Gegenstand beute = super.getItemWithApproximateValue(i*20-1, sortedItems);
            if(i*20 < amount*10) {
                assertTrue(beute.equals(sortedItems.get(2*i)));
            } else {
                assertTrue(beute.equals(sortedItems.get(amount-1)));
            }
        }
    }
    
    
    @Test
    public void generateWaffenBeuteTest() {
        Gegenstand waffe = generateWaffenBeute(150);
        double gesamtMalusInPercent = getGesamtMalus()/100.;
        int expectedValue = (int)(150*(1.-gesamtMalusInPercent));
        System.out.println(expectedValue);
        assertTrue(waffe.computeValue() >= expectedValue-10);
        assertTrue(waffe.computeValue() <= expectedValue+10);
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
