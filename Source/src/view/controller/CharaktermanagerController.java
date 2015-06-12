package view.controller;

import java.util.List;

import org.controlsfx.control.NotificationPane;

import controller.GruppenSubject;
import model.Spieler;
import javafx.fxml.FXML;

public class CharaktermanagerController extends NotificationController {

    @FXML
    private GruppenmanagerController gruppenManagerController;
    @FXML
    private SpielermanagerController spielerManagerController;
    @FXML
    private GegnermanagerController gegnerManagerController;
    
    
    public CharaktermanagerController() {
    }

    
    
    @FXML
    private void initialize() {
        List<Spieler> spielerList = Spieler.getAll();
        gruppenManagerController.initialize(spielerList);
        spielerManagerController.initialize(spielerList, gruppenManagerController);
        gegnerManagerController.initialize();
    }



    public void setGruppenSubject_(GruppenSubject gruppenSubject_) {
        gruppenManagerController.setGruppenSubject_(gruppenSubject_);
        gruppenSubject_.addGruppenObserver(gruppenManagerController);
    }
    
    
    
    @Override
    public void setNotificationPane(NotificationPane pane) {
        super.setNotificationPane(pane);
        gruppenManagerController.setNotificationPane(pane);
        spielerManagerController.setNotificationPane(pane);
        gegnerManagerController.setNotificationPane(pane);
    }
}


