package view.controller;

import java.util.List;

import controller.GruppenSubject;
import controller.manipulators.SpielerManipulator;
import model.Spieler;
import javafx.fxml.FXML;

public class CharaktermanagerController {

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
        List<Spieler> spielerList = SpielerManipulator.getInstance().getAll();
        gruppenManagerController.initialize(spielerList);
        spielerManagerController.initialize(spielerList, gruppenManagerController);
        gegnerManagerController.initialize();
    }



    public void setGruppenSubject_(GruppenSubject gruppenSubject_) {
        gruppenManagerController.setGruppenSubject_(gruppenSubject_);
        gruppenSubject_.addGruppenObserver(gruppenManagerController);
    }
}


