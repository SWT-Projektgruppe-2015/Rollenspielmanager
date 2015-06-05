package tests.controller;

import static org.junit.Assert.*;
import model.Charakter;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Spieler;

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
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_RUESTUNG, 1);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_HELM, 3);
        assertLebensverlust(spieler_, schaden, Charakter.LOWERBOUND_DIREKT, 75);
    }

    private void assertLebensverlust(Spieler spieler, int schaden,
            int wuerfelErgebnis, int erwarteterLebensverlust) {
        int lebensverlust;
        lebensverlust = spieler.getLebensverlust(schaden, wuerfelErgebnis);
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
