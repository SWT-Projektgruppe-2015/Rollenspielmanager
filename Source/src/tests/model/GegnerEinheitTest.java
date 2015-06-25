package tests.model;

import static org.junit.Assert.*;

import java.util.List;

import model.GegnerEinheit;
import model.GegnerTyp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
        assertTrue(GegnerEinheit.getStaerkeModifier(gegner.getStaerke_()) == -2);
        gegner.setStaerke_(12);
        assertTrue(GegnerEinheit.getStaerkeModifier(gegner.getStaerke_()) == 1);
    }
    
    
    
    @Test
    public void correctStaerkeModifierBetween13And18() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setStaerke_(13);
        assertTrue(GegnerEinheit.getStaerkeModifier(gegner.getStaerke_()) == 1);
        gegner.setStaerke_(18);
        assertTrue(GegnerEinheit.getStaerkeModifier(gegner.getStaerke_()) == 2);
    }

    
    
    @Test
    public void correctStaerkeModifierFrom19() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setStaerke_(19);
        assertTrue(GegnerEinheit.getStaerkeModifier(gegner.getStaerke_()) == 2);
        gegner.setStaerke_(70);
        assertTrue(GegnerEinheit.getStaerkeModifier(gegner.getStaerke_()) == 19);
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
    
    
    
    @Test
    public void blockingBoundIsCorrect() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setGeschick_(5);
        assertTrue(gegner.getBoundForBlocking() == 18);
        gegner.setGeschick_(21);
        assertTrue(gegner.getBoundForBlocking() == 17);
        gegner.setGeschick_(41);
        assertTrue(gegner.getBoundForBlocking() == 16);
        gegner.setGeschick_(105);
        assertTrue(gegner.getBoundForBlocking() == 13);
    }
    
    
    
    @Test
    public void weakBlockIsSuccessfulRightNumberOfTimes() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setGeschick_(5);
        int numberOfSuccessfulBlocks = 0;
        for(int i = 0; i<1000; i++) {
            if(gegner.blockIsSuccessful())
                numberOfSuccessfulBlocks++;
        }
        
        assertTrue(numberOfSuccessfulBlocks > 80);
        assertTrue(numberOfSuccessfulBlocks < 120);
    }
    
    
    
    @Test
    public void goodBlockIsSuccessfulRightNumberOfTimes() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setGeschick_(61);
        int numberOfSuccessfulBlocks = 0;
        for(int i = 0; i<1000; i++) {
            if(gegner.blockIsSuccessful())
                numberOfSuccessfulBlocks++;
        }
        
        assertTrue(numberOfSuccessfulBlocks > 230);
        assertTrue(numberOfSuccessfulBlocks < 270);
    }
    
    
    
    @Test
    public void almostPerfectBlockIsSuccessfulRightNumberOfTimes() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setGeschick_(341);
        int numberOfSuccessfulBlocks = 0;
        for(int i = 0; i<1000; i++) {
            if(gegner.blockIsSuccessful())
                numberOfSuccessfulBlocks++;
        }
        
        assertTrue(numberOfSuccessfulBlocks > 920);
        assertTrue(numberOfSuccessfulBlocks < 980);
    }
    
    
    
    @Test
    public void lebensPunktePropertyIsCorrect() {
        GegnerEinheit gegner = GegnerEinheit.createEinheiten(new GegnerTyp(), 1).get(0);
        gegner.setCurrentLebenspunkte_(3);
        
        assertTrue(gegner.lebenspunkteProperty().get().equals("3/25"));
    }
}
