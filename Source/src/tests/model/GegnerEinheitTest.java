package tests.model;

import static org.junit.Assert.*;

import java.util.List;

import model.GegnerEinheit;
import model.GegnerTyp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Dice;

public class GegnerEinheitTest {
    
    static GegnerTyp normalerGegner;
    
    @BeforeClass
    public static void initObjects() throws Exception {
        normalerGegner = new GegnerTyp();
        normalerGegner.addToDB();
    }
    
    @AfterClass
    public static void deleteFromDB() {
        normalerGegner.deleteFromDB();
    }
    
    @Test
    public void einheitIsNotNull() {
        GegnerEinheit neueEinheit = GegnerEinheit.createEinheiten(normalerGegner, 1).get(0);
        assertTrue(neueEinheit != null);
        assertTrue(neueEinheit.getID_() == 1);
    }
    
    @Test
    public void createEinheitenTest() {
        List<GegnerEinheit> einheiten = GegnerEinheit.createEinheiten(normalerGegner, 6);
        assertTrue("size is not correct", einheiten.size() == 6);
        assertTrue("created einheit is null", einheiten.get(2) != null);
        assertTrue("ID was not set correct", einheiten.get(5).getID_() == 6);
    }
    
    @Test
    public void verifyUniqueness()  {
        List<GegnerEinheit> einheiten = GegnerEinheit.createEinheiten(normalerGegner, 2);
        assertTrue(einheiten.get(0).compareTo(einheiten.get(1))!= 0);
    }
    
    
    
    @Test
    public void correctStaerkeModifierUntil12() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setStaerke_(1);
        assertTrue(gegner.getStaerkeModifier() == -2);
        gegner.setStaerke_(12);
        assertTrue(gegner.getStaerkeModifier() == 1);
    }
    
    
    
    @Test
    public void correctStaerkeModifierBetween13And18() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setStaerke_(13);
        assertTrue(gegner.getStaerkeModifier() == 1);
        gegner.setStaerke_(18);
        assertTrue(gegner.getStaerkeModifier() == 2);
    }

    
    
    @Test
    public void correctStaerkeModifierFrom19() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setStaerke_(19);
        assertTrue(gegner.getStaerkeModifier() == 2);
        gegner.setStaerke_(70);
        assertTrue(gegner.getStaerkeModifier() == 19);
    }
    
    
    
    @Test
    public void verifyLowStaerkeWurfInCorrectRange() {
        verifyStaerkeWurfInCorrectRange(3, 10, -1);
    }

    
    
    @Test
    public void verifyMediumStaerkeWurfInCorrectRange() {
        verifyStaerkeWurfInCorrectRange(14, 13, 2);
    }
    
    
    
    @Test
    public void verifyHighStaerkeWurfInCorrectRange() {
        verifyStaerkeWurfInCorrectRange(70, 31, 20);
    }
    
    
    
    private void verifyStaerkeWurfInCorrectRange(int staerke, int upperBound, int lowerBound) {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setStaerke_(staerke);
        for(int i = 0; i < 100; ++i){
            int ergebnis = gegner.simulateStaerkeProbe();
            assertTrue(ergebnis <= upperBound);
            assertTrue(ergebnis >= lowerBound);
        }
    }
}
