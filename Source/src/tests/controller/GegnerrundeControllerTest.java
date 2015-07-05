package tests.controller;

import static org.junit.Assert.*;

import model.Charakter;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Ruestungseffekt;
import model.Spieler;
import model.Waffen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.controller.GegnerrundeController;

public class GegnerrundeControllerTest extends GegnerrundeController {
    
    private Spieler spieler_;
    private GegnerEinheit gegner_;
    private GegnerTyp gegnerTyp_;
    
    
    @Before
    public void initializeInDB() {
        spieler_ = createSpieler(40, 20, 60);
        gegnerTyp_ = createGegner(135);
        gegner_ = GegnerEinheit.createEinheiten(gegnerTyp_, 1).get(0);
        assertTrue(Spieler.getAll().contains(spieler_));
        assertTrue(GegnerTyp.getAll().contains(gegnerTyp_));
    }
    
    @After
    public void deleteFromDB() {
        spieler_.deleteFromDB();
        gegnerTyp_.deleteFromDB();
        assertFalse(Spieler.getAll().contains(spieler_));
        assertFalse(GegnerTyp.getAll().contains(gegnerTyp_));
    }

    @Test
    public void checkLebensPunkteVerlust() {
        int schaden = 135;
        assertLebensverlust(spieler_, schaden, 0, 0);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_RUESTUNG, 1);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_HELM, 3);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_DIREKT, 75);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_KRITISCH, 82);
        schaden = -2;
        assertLebensverlust(spieler_, schaden, 0, 0);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_RUESTUNG, 0);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_HELM, 0);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_DIREKT, 0);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_KRITISCH, 0);
    }

    private void assertLebensverlust(Spieler spieler, int schaden,
            int wuerfelErgebnis, int erwarteterLebensverlust) {
        int lebensverlust;
        lebensverlust = spieler.getLebensverlust(schaden, wuerfelErgebnis, 0);
        assertTrue(lebensverlust == erwarteterLebensverlust);
    }
    
    @Test
    public void checkSimulatedLebensverlust() {
        for(int i = 0; i < 20; ++i){
            int geschickWurf = 15;
            int verlorenesLeben = simuliereLebensverlustAmSpieler(gegner_, spieler_, geschickWurf);
            assertTrue(verlorenesLeben == 0 || verlorenesLeben == 1 
                    || verlorenesLeben == 3 || verlorenesLeben == 75);
        }
    }
    
    
    
    @Test
    public void lowerGeschickWurfWithMalus() {
        Waffen waffeWithGeschickMalus = new Waffen();
        waffeWithGeschickMalus.setEffektTyp_(Waffen.EffektTyp.MALUS_GESCHICK);
        waffeWithGeschickMalus.setEffektWert_(100);
        spieler_.addWaffe(waffeWithGeschickMalus);
        
        gegnerTyp_.setGeschick_(100);
        GegnerEinheit geschickterGegner = GegnerEinheit.createEinheiten(gegnerTyp_, 1).get(0);
        
        GegnerrundeController controller = new GegnerrundeController();
        
        for(int i = 0; i<100; i++) {
            int wurf = controller.simulateGeschickWurf(geschickterGegner, spieler_, waffeWithGeschickMalus);
            assertTrue(wurf > 0 && wurf < 5);
        }
        spieler_.deleteWaffe(waffeWithGeschickMalus);
        
        Ruestungseffekt geschickMalusEffekt = new Ruestungseffekt();
        geschickMalusEffekt.setEffektTyp_(Ruestungseffekt.EffektTyp.MALUS_GESCHICK);
        geschickMalusEffekt.setEffektWert_(3);
        spieler_.addRuestungsEffekt(geschickMalusEffekt);        
        for(int i = 0; i<100; i++) {
            int wurf = controller.simulateGeschickWurf(geschickterGegner, spieler_, waffeWithGeschickMalus);
            assertTrue(wurf > 0 && wurf < 5);
        }
        
        geschickMalusEffekt.setEffektWert_(50);
        waffeWithGeschickMalus.setEffektWert_(50);
        spieler_.addWaffe(waffeWithGeschickMalus);
        for(int i = 0; i<100; i++) {
            int wurf = controller.simulateGeschickWurf(geschickterGegner, spieler_, waffeWithGeschickMalus);
            assertTrue(wurf > 0 && wurf < 5);
        }
        spieler_.deleteWaffe(waffeWithGeschickMalus);
        
        waffeWithGeschickMalus.deleteFromDB();
        geschickMalusEffekt.deleteFromDB();
    }
    
    
    
    public Spieler createSpieler(int defR, int defH, int defS) {
        Spieler defaultSpieler = new Spieler();
        defaultSpieler.addToDB();
        defaultSpieler.setName_("Default");
        defaultSpieler.setDefH(defH);
        defaultSpieler.setDefR(defR);
        defaultSpieler.setDefS(defS);
        return defaultSpieler;
    }
    
    public GegnerTyp createGegner(int schaden) {
        GegnerTyp defaultGegner = new GegnerTyp();
        defaultGegner.addToDB();
        defaultGegner.setName_("Default");
        defaultGegner.setSchaden_(schaden);
        defaultGegner.setGeschick_(64);
        return defaultGegner;
    }
}
