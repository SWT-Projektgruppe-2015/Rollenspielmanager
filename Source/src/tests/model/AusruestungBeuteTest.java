package tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Dice;
import model.AusruestungBeute;
import model.Gegenstand;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Spieler;
import model.Waffen;

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
        
        assertTrue(waffenCount < tries * WAFFEN_WKT * 1.1);
        assertTrue(waffenCount > tries * WAFFEN_WKT * .9);
        assertTrue(helmCount < tries * 1./RUESTUNG_TEILE * 1.25);
        assertTrue(helmCount > tries * 1./RUESTUNG_TEILE * .75);
    }
    
    
    
//    @Test
//    public void ratiosAreConsistent() {
//        assertEquals(HELM_WKT 
//                + WAFFEN_WKT 
//                + HANDSCHUH_RATIO + HARNISCH_RATIO + SCHUH_RATIO + GUERTEL_RATIO, 1, 0.001);
//    }
    
    
    private void generateRuestungsItems() {
        generateItems(Gegenstand.RUESTUNG + "/" + Gegenstand.HARNISCH);
        generateItems(Gegenstand.RUESTUNG + "/" + Gegenstand.HANDSCHUH);
        generateItems(Gegenstand.RUESTUNG + "/" + Gegenstand.SCHUH);
        generateItems(Gegenstand.RUESTUNG + "/" + Gegenstand.GUERTEL);
    }
    
    
    
    private void createDefensiveGegnerTyp() {
        typ_ = new GegnerTyp();
        typ_.setName_("Gegner Nr." + Dice.rollDice(100));
        typ_.setDefH(50);
        typ_.setDefR(200);
        typ_.setDefS(20);
        typ_.setSchaden_(150);
        typ_.addToDB();
    }
    
    
    
//  @Test // Dont delete this! It fills your DB if its empty.
//  public void fillDB() {
//      fillDBWith("Lebensmittel/Brot");
//      fillDBWith("Diverses/Seil");
//      fillDBWith("Diverses/Fackel");
//      fillDBWith(Gegenstand.WAFFE + "/" + "Schwert");
//      fillDBWith(Gegenstand.WAFFE + "/" + "Axt");
//      fillDBWith(Gegenstand.RUESTUNG + "/" + Gegenstand.HARNISCH);
//      fillDBWith(Gegenstand.RUESTUNG + "/" + Gegenstand.HANDSCHUH);
//      fillDBWith(Gegenstand.RUESTUNG + "/" + Gegenstand.SCHUH);
//      fillDBWith(Gegenstand.RUESTUNG + "/" + Gegenstand.GUERTEL);
//      fillDBWithGegnertyp();
//      fillDBWithSpieler();
//  }
  
  
  public void fillDBWith(String kategorie) {
      List<String> tmp = Gegenstand.getSubKategories(kategorie);
      for(int i = 0; i < amount; ++i) {
          Gegenstand item = new Gegenstand();
          item.setName_(tmp.get(tmp.size()-1) + " " + String.valueOf(i));
          item.setKosten_(i);
          if(item.isAusruestung())
              item.setWert_(Integer.toString(i*10)+"+W9");
          item.setKategorie_(kategorie);
          item.addToDB();
      }
  }
  
  
  private void fillDBWithGegnertyp() {
      for(int i = 0 ; i < 5; ++i) {
          GegnerTyp tmp = new GegnerTyp();
          tmp.setName_("Gegner Nr." + Dice.rollDice(100));
          tmp.setDefH(30);
          tmp.setDefR(60);
          tmp.setDefS(40);
          tmp.setSchaden_(250);
          tmp.setErfahrung_(1100);
          tmp.setGeschick_(20 + Dice.rollDice(60));
          tmp.setStaerke_(1 + Dice.rollDice(50));
          tmp.addToDB();
      }
  }
  
  
  private void fillDBWithSpieler() {
      for(int i = 0 ; i < 3; ++i) {
          Spieler tmp = new Spieler();
          tmp.setName_("Spieler Nr." + Dice.rollDice(100));
          tmp.setDefH(20 + Dice.rollDice(20));
          tmp.setDefR(50 + Dice.rollDice(20));
          tmp.setDefS(20 + Dice.rollDice(40));
          Waffen waffe = new Waffen();
          waffe.setWaffenName_("Waffe Nr. " + Dice.rollDice(100));
          waffe.setWaffenSchaden_(250 + Dice.rollDice(250));
          tmp.addWaffe(waffe);
          tmp.getAusruestung_().addWaffe(waffe);
          tmp.addToDB();
      }
  }
  
  

  private void generateItems(String kategorie) {
      for(int i = 0; i < amount; ++i) {
          Gegenstand item = new Gegenstand();
          item.setName_(String.valueOf(i) + kategorie);
          item.setKosten_(i);
          item.setWert_(Integer.toString(i*10)+"+W9");
          item.setKategorie_(kategorie);
          item.addToDB();
          sortedItems.add(item);
      }
  }
}
