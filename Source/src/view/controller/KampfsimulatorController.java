package view.controller;

import java.util.List;

import org.controlsfx.control.NotificationPane;

import model.GegnerEinheit;
import model.Spieler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class KampfsimulatorController extends NotificationController {
    @FXML
    private GegnerrundeController gegnerRundeController;
    @FXML
    private SpielerrundeController spielerRundeController;
    @FXML
    private KampfendeController kampfEndeController;    
    
    
    public void initializeAllTabs(Hauptprogramm main, List<Spieler> allSpieler, ObservableList<GegnerEinheit> allGegner) {
        gegnerRundeController.initializeParameters(allSpieler, allGegner);
        spielerRundeController.initialize(main, gegnerRundeController, allSpieler, allGegner);
        kampfEndeController.initialize(allSpieler, allGegner);
    }
    
    @Override
    public void setNotificationPane(NotificationPane pane) {
        super.setNotificationPane(pane);
        spielerRundeController.setNotificationPane(pane);
    }
}
