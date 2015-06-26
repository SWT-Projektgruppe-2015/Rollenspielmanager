package view.controller;

import java.util.List;

import org.controlsfx.control.NotificationPane;

import model.GegnerEinheit;
import model.Spieler;
import javafx.fxml.FXML;

public class KampfsimulatorController extends NotificationController {
    @FXML
    private GegnerrundeController gegnerRundeController;
    @FXML
    private SpielerrundeController spielerRundeController;
    @FXML
    private KampfendeController kampfendeController;    
    
    private List<Spieler> allSpieler_;
    private List<GegnerEinheit> allGegner_;
    
    public void initializeAllTabs(Hauptprogramm main, List<Spieler> allSpieler, List<GegnerEinheit> allGegner) {
        gegnerRundeController.initializeParameters(allSpieler, allGegner);
        spielerRundeController.initialize(main, gegnerRundeController, allSpieler, allGegner);
        kampfendeController.initialize(allSpieler);
        allSpieler_ = allSpieler;
        allGegner_ = allGegner;
    }
    
    @Override
    public void setNotificationPane(NotificationPane pane) {
        super.setNotificationPane(pane);
        spielerRundeController.setNotificationPane(pane);
    }
}
