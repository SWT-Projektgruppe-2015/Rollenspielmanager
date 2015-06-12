package view.controller;

import java.util.List;

import model.GegnerEinheit;
import model.Spieler;
import javafx.fxml.FXML;

public class KampfsimulatorController {
    @FXML
    private GegnerrundeController gegnerRundeController;
    @FXML
    private SpielerrundeController spielerRundeController;
    
    private List<Spieler> allSpieler_;
    private List<GegnerEinheit> allGegner_;
    
    public void initializeAllTabs(Hauptprogramm main, List<Spieler> allSpieler, List<GegnerEinheit> allGegner) {
        gegnerRundeController.initializeParameters(allSpieler, allGegner);
        spielerRundeController.initialize(main, gegnerRundeController, allSpieler, allGegner);
        allSpieler_ = allSpieler;
        allGegner_ = allGegner;
    }
}
