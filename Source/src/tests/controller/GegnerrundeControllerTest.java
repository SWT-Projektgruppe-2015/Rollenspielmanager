package tests.controller;

import static org.junit.Assert.*;
import model.Charakter;
import model.Gegner;
import model.Spieler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.controller.GegnerrundeController;

public class GegnerrundeControllerTest extends GegnerrundeController {
    
    private Spieler spieler_;
    private Gegner gegner_;
    
    
    @Before
    @Test
    public void initializeInDB() {
        spieler_ = createSpieler(20, 40, 60);
        gegner_ = createGegner(135);
        assertTrue(Spieler.getAll().contains(spieler_));
        assertTrue(Gegner.getAllGegner().contains(gegner_));
    }
    
    @After
    @Test
    public void deleteFromDB() {
        spieler_.deleteFromDB();
        gegner_.deleteFromDB();
        assertFalse(Spieler.getAll().contains(spieler_));
        assertFalse(Gegner.getAllGegner().contains(gegner_));
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
    
    
    
    public Spieler createSpieler(int defH, int defR, int defS) {
        Spieler defaultSpieler = new Spieler();
        defaultSpieler.addToDB();
        defaultSpieler.setName_("Default");
        defaultSpieler.setDefH(defH);
        defaultSpieler.setDefR(defR);
        defaultSpieler.setDefS(defS);
        return defaultSpieler;
    }
    
    public Gegner createGegner(int schaden) {
        Gegner defaultGegner = new Gegner();
        defaultGegner.addToDB();
        defaultGegner.setName_("Default");
        defaultGegner.setDamage(schaden);
        defaultGegner.setGeschick_(64);
        return defaultGegner;
    }
}
