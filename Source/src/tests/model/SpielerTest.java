package tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import model.Charakter;
import model.Faehigkeiten;
import model.Ruestungseffekt;
import model.Spieler;
import model.Waffen;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SpielerTest {
    private Spieler normalSpieler;
    
    
    
    @Before
    public void initObjects() {
        normalSpieler = new Spieler();
        normalSpieler.addToDB();
        normalSpieler.setLevel_(2);
        normalSpieler.setKreis_(1);
    }
    
    
    
    @After
    public void deleteFromDB() {
        if(normalSpieler != null)
            normalSpieler.deleteFromDB();
    }
    
    
    
    @Test
    public void levelIncrease() {
        int kreisBefore = normalSpieler.getKreis_();
        int levelBefore = normalSpieler.getLevel_();

        normalSpieler.increaseLevel();
        assertTrue(normalSpieler.getKreis_() == kreisBefore);
        assertTrue(normalSpieler.getLevel_() == levelBefore + 1);
    }

    

    @Test
    public void levelIncreaseOnMaxLevel() {
        Spieler spielerOnMaxLevel = createMaxLevelSpieler();

        int kreisBefore = spielerOnMaxLevel.getKreis_();
        
        spielerOnMaxLevel.increaseLevel();
        assertTrue(spielerOnMaxLevel.getKreis_() == kreisBefore + 1);
        assertTrue(spielerOnMaxLevel.getLevel_() == Charakter.MIN_LEVEL);
    }
    
    
    
    @Test
    public void levelIncreaseOnMaxLevelAndKreis() {
        Spieler spielerOnMaxLevelAndKreis = createMaxLevelSpieler();
        spielerOnMaxLevelAndKreis.setKreis_(Charakter.MAX_KREIS);

        int kreisBefore = spielerOnMaxLevelAndKreis.getKreis_();
        int levelBefore = spielerOnMaxLevelAndKreis.getLevel_();

        spielerOnMaxLevelAndKreis.increaseLevel();
        assertTrue(spielerOnMaxLevelAndKreis.getKreis_() == kreisBefore);
        assertTrue(spielerOnMaxLevelAndKreis.getLevel_() == levelBefore);
    }
    
    
    
    private Spieler createMaxLevelSpieler() {
        Spieler spielerOnMaxLevel = new Spieler();
        spielerOnMaxLevel.setLevel_(Charakter.MAX_LEVEL);
        spielerOnMaxLevel.setKreis_(1);
        
        return spielerOnMaxLevel;
    }
    
    
    
    @Test
    public void levelDecrease() {
        int kreisBefore = normalSpieler.getKreis_();
        int levelBefore = normalSpieler.getLevel_();

        normalSpieler.decreaseLevel();
        assertTrue(normalSpieler.getKreis_() == kreisBefore);
        assertTrue(normalSpieler.getLevel_() == levelBefore - 1);
    }

    
    
    @Test
    public void levelDecreaseOnMinLevel() {
        Spieler spielerOnMinLevel = createSpielerOnMinLevel();
        
        int kreisBefore = spielerOnMinLevel.getKreis_();
        
        spielerOnMinLevel.decreaseLevel();
        assertTrue(spielerOnMinLevel.getKreis_() == kreisBefore - 1);
        assertTrue(spielerOnMinLevel.getLevel_() == Charakter.MAX_LEVEL);
    }
    
    
    
    @Test
    public void setDefH() {
        normalSpieler.setDefH(200);
        assertTrue(normalSpieler.getDefH() == 200);
    }
    
    
    
    @Test
    public void setNegativeDefH() {
        int defHBefore = normalSpieler.getDefH();

        normalSpieler.setDefH(-200);
        assertTrue(normalSpieler.getDefH() == defHBefore);
    }


    
    @Test
    public void setDefR() {
        normalSpieler.setDefR(200);
        assertTrue(normalSpieler.getDefR() == 200);
    }

    

    @Test
    public void setNegativeDefR() {
        int defRBefore = normalSpieler.getDefR();

        normalSpieler.setDefR(-200);
        assertTrue(normalSpieler.getDefR() == defRBefore);
    }


    
    @Test
    public void setDefS() {
        normalSpieler.setDefS(200);
        assertTrue(normalSpieler.getDefS() == 200);
    }

    

    @Test
    public void setNegativeDefS() {
        int defSBefore = normalSpieler.getDefS();

        normalSpieler.setDefS(-200);
        assertTrue(normalSpieler.getDefS() == defSBefore);
    }
    
    
    
    @Test
    public void zeroDefIsInvalid() {
        assertFalse(Charakter.ausruestungIsValid(0, 2, 5));
        assertFalse(Charakter.ausruestungIsValid(3, 0, 2));
    }
    
    
    @Test
    public void defValidatorValidatesValidDef() {
        assertTrue(Charakter.ausruestungIsValid(2, 3, 0));
        assertTrue(Charakter.ausruestungIsValid(2, 3, 5));
    }
    
    
    
    @Test
    public void negativeDefHIsInvalid() {
        assertFalse(Charakter.ausruestungIsValid(2, 3, -2));
        assertFalse(Charakter.ausruestungIsValid(-1, 2, 5));
        assertFalse(Charakter.ausruestungIsValid(3, -10, 2));
    }


    
    @Test
    public void levelDecreaseOnMinLevelAndKreis() {
        Spieler spielerOnMinLevelAndKreis = createSpielerOnMinLevel();
        spielerOnMinLevelAndKreis.setKreis_(Charakter.MIN_KREIS);

        spielerOnMinLevelAndKreis.decreaseLevel();
        assertTrue(spielerOnMinLevelAndKreis.getKreis_() == Charakter.MIN_KREIS);
        assertTrue(spielerOnMinLevelAndKreis.getLevel_() == Charakter.MIN_LEVEL);
    }
    
    
    
    private Spieler createSpielerOnMinLevel() {
        Spieler spielerOnMinLevel = new Spieler();
        spielerOnMinLevel.setKreis_(2);
        spielerOnMinLevel.setLevel_(Charakter.MIN_LEVEL);
        
        return spielerOnMinLevel;
    }
    
    
    
    @Test
    public void addWaffe() {
        Spieler spielerWithWaffe = new Spieler();
        spielerWithWaffe.addToDB();
        
        Waffen waffe = new Waffen();
        
        spielerWithWaffe.addWaffe(waffe);
        assertTrue(spielerWithWaffe.getWaffen().contains(waffe));
        
        waffe.deleteFromDB();
        spielerWithWaffe.deleteFromDB();
    }

    

    @Test
    public void deleteWaffe() {
        Spieler spielerWithWaffe = new Spieler();
        spielerWithWaffe.addToDB();
        
        Waffen waffe = new Waffen();
        spielerWithWaffe.addWaffe(waffe);
        spielerWithWaffe.deleteWaffe(waffe);
        
        assertTrue(!spielerWithWaffe.getWaffen().contains(waffe));
        
        waffe.deleteFromDB();
        spielerWithWaffe.deleteFromDB();
    }
    
    
    
    @Test
    public void waffenAreOrdered() {
        Spieler spielerWithWaffe = new Spieler();
        spielerWithWaffe.addToDB();
        
        Waffen firstWaffe = new Waffen();
        firstWaffe.setWaffenName_("Dolch");
        Waffen secondWaffe = new Waffen();
        secondWaffe.setWaffenName_("Holzschwert");
        Waffen thirdWaffe = new Waffen();
        thirdWaffe.setWaffenName_("Speer");
        
        spielerWithWaffe.addWaffe(secondWaffe);
        spielerWithWaffe.addWaffe(thirdWaffe);
        spielerWithWaffe.addWaffe(firstWaffe);
        
        List<Waffen> waffen = spielerWithWaffe.getWaffen();
        assertTrue(waffen.get(0) == firstWaffe);
        assertTrue(waffen.get(1) == secondWaffe);
        assertTrue(waffen.get(2) == thirdWaffe);
        
        firstWaffe.deleteFromDB();
        secondWaffe.deleteFromDB();
        thirdWaffe.deleteFromDB();
        spielerWithWaffe.deleteFromDB();
    }

    @Ignore
    @Test
    public void addFaehigkeit() {
        Faehigkeiten faehigkeit = new Faehigkeiten();

        normalSpieler.addFaehigkeit(faehigkeit);
        assertTrue(normalSpieler.getFaehigkeiten().contains(faehigkeit));
    }


    
    @Test
    public void deleteFaehigkeit() {
        Faehigkeiten faehigkeit = new Faehigkeiten();
        normalSpieler.addFaehigkeit(faehigkeit);
        
        normalSpieler.deleteFaehigkeit(faehigkeit);
        assertTrue(!normalSpieler.getFaehigkeiten().contains(faehigkeit));
    }


    
    @Test
    public void allPlayersAreReturned() {
        List<Spieler> allPlayers = Spieler.getAll();
        for (Charakter player : allPlayers) {
            System.out.println(player.getName_());
        }
    }



    @Test
    public void playersHaveDefR() {
        List<Spieler> allPlayers = Spieler.getAll();
        for (Charakter player : allPlayers) {
            assertNotNull(player.getDefR());
        }
    }
    
    
    
    @Test
    public void playersHaveDefH() {
        List<Spieler> allPlayers = Spieler.getAll();
        for (Charakter player : allPlayers) {
            assertNotNull(player.getDefH());
        }
    }    
    
    
    
    @Test
    public void playersHaveDefS() {
        List<Spieler> allPlayers = Spieler.getAll();
        for (Charakter player : allPlayers) {
            assertNotNull(player.getDefS());
        }
    }
    
    
    
    @Test
    public void getTotalStaerkeMalusWorks() {
        Spieler testSpieler = new Spieler();
        testSpieler.addToDB();
        
        Waffen waffe1 = new Waffen();
        waffe1.setEffektTyp_(Waffen.EffektTyp.MALUS_STAERKE);
        waffe1.setEffektWert_(23);
        testSpieler.addWaffe(waffe1);
        
        Waffen waffe2 = new Waffen();
        waffe2.setEffektTyp_(Waffen.EffektTyp.MALUS_STAERKE);
        waffe2.setEffektWert_(3);
        testSpieler.addWaffe(waffe2);
        
        
        Ruestungseffekt effekt = new Ruestungseffekt();
        effekt.setEffektTyp_(Ruestungseffekt.EffektTyp.MALUS_STAERKE);
        effekt.setEffektWert_(4);
        testSpieler.addRuestungsEffekt(effekt);
        
        assertTrue(testSpieler.getTotalStaerkeMalus(null) == 4);
        assertTrue(testSpieler.getTotalStaerkeMalus(waffe1) == 27);
        assertTrue(testSpieler.getTotalStaerkeMalus(waffe2) == 7);
        
        waffe1.deleteFromDB();
        waffe2.deleteFromDB();
        effekt.deleteFromDB();
        testSpieler.deleteFromDB();
    }
    
    
    
    @Test
    public void getTotalGeschickMalusWorks() {
        Spieler testSpieler = new Spieler();
        testSpieler.addToDB();
        
        Waffen waffe1 = new Waffen();
        waffe1.setEffektTyp_(Waffen.EffektTyp.MALUS_STAERKE);
        waffe1.setEffektWert_(23);
        testSpieler.addWaffe(waffe1);
        
        Waffen waffe2 = new Waffen();
        waffe2.setEffektTyp_(Waffen.EffektTyp.MALUS_GESCHICK);
        waffe2.setEffektWert_(3);
        testSpieler.addWaffe(waffe2);
        
        
        Ruestungseffekt effekt = new Ruestungseffekt();
        effekt.setEffektTyp_(Ruestungseffekt.EffektTyp.MALUS_GESCHICK);
        effekt.setEffektWert_(4);
        testSpieler.addRuestungsEffekt(effekt);
        
        assertTrue(testSpieler.getTotalGeschickMalus(null) == 4);
        assertTrue(testSpieler.getTotalGeschickMalus(waffe1) == 4);
        assertTrue(testSpieler.getTotalGeschickMalus(waffe2) == 7);
        
        waffe1.deleteFromDB();
        waffe2.deleteFromDB();
        effekt.deleteFromDB();
        testSpieler.deleteFromDB();
    }
    
    
    
    @Test
    public void getExpFactorTest() {
        Spieler testSpieler = new Spieler();
        testSpieler.addToDB();
        
        Ruestungseffekt effekt = new Ruestungseffekt();
        effekt.setEffektTyp_(Ruestungseffekt.EffektTyp.EXP_BOOST);
        effekt.setEffektWert_(4);

        assertEquals(testSpieler.getExpFactor(), 1., 0.00001);
        testSpieler.addRuestungsEffekt(effekt);
        assertEquals(testSpieler.getExpFactor(), 1.04, 0.00001);
        effekt.deleteFromDB();
        testSpieler.deleteFromDB();
    }
}
