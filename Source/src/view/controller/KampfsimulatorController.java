package view.controller;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.NotificationPane;

import model.GegnerEinheit;
import model.Spieler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class KampfsimulatorController extends NotificationController {
    @FXML
    private GegnerrundeController gegnerRundeController;
    @FXML
    private SpielerrundeController spielerRundeController;
    @FXML
    private KampfendeController kampfEndeController;    
    
    
    public void initializeAllTabs(Hauptprogramm main, List<Spieler> allSpieler, ObservableList<GegnerEinheit> allExpYieldingGegner) {
        ObservableList<GegnerEinheit> allActiveGegner = FXCollections.observableList(new ArrayList<GegnerEinheit>(allExpYieldingGegner));
        gegnerRundeController.initializeParameters(allSpieler, allActiveGegner, spielerRundeController);
        spielerRundeController.initialize(main, allSpieler, allActiveGegner, allExpYieldingGegner);
        kampfEndeController.initialize(allSpieler, allExpYieldingGegner);
    }
    
    @Override
    public void setNotificationPane(NotificationPane pane) {
        super.setNotificationPane(pane);
        spielerRundeController.setNotificationPane(pane);
    }
}
