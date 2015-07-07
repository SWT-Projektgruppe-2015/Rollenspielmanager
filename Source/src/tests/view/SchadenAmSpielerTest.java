package tests.view;

import static org.junit.Assert.assertEquals;
import model.Spieler;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import view.tabledata.SchadenAmSpieler;

public class SchadenAmSpielerTest {
    
    static SchadenAmSpieler testSubject = new SchadenAmSpieler(new Spieler());
    static Spieler testSpieler;
    
    @BeforeClass
    public static void setUp() {
        testSpieler = new Spieler();
        testSpieler.addToDB();
        testSubject = new SchadenAmSpieler(testSpieler);
    }
    
    
    @AfterClass
    public static void cleanUp() {
        testSpieler.deleteFromDB();
    }
    
    @Test
    public void zoneIsSetCorrectly() {
        testSubject.setZone_(3);
        assertEquals("Geschickwert 3 ist nicht daneben", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.DANEBEN + "\n(Wurf: " + 3 + ")"), 0);
        testSubject.setZone_(4);
        assertEquals("Geschickwert 4 ist nicht Ruestung", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.RUESTUNG + "\n(Wurf: " + 4 + ")"), 0);
        testSubject.setZone_(14);
        assertEquals("Geschickwert 14 ist nicht Ruestung", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.RUESTUNG + "\n(Wurf: " + 14 + ")"), 0);
        testSubject.setZone_(15);
        assertEquals("Geschickwert 15 ist nicht Helm", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.HELM + "\n(Wurf: " + 15 + ")"), 0);
        testSubject.setZone_(24);
        assertEquals("Geschickwert 24 ist nicht Helm", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.HELM + "\n(Wurf: " + 24 + ")"), 0);
        testSubject.setZone_(25);
        assertEquals("Geschickwert 25 ist nicht Direkt", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.DIREKT + "\n(Wurf: " + 25 + ")"), 0);
        testSubject.setZone_(34);
        assertEquals("Geschickwert 34 ist nicht Direkt", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.DIREKT + "\n(Wurf: " + 34 + ")"), 0);
        testSubject.setZone_(35);
        assertEquals("Geschickwert 35 ist nicht Kritisch", 
                testSubject.getZone_().compareTo(SchadenAmSpieler.KRITISCH + "\n(Wurf: " + 35 + ")"), 0);
    }
    
}
