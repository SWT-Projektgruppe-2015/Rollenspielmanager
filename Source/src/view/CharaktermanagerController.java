package view;

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
    
    private GruppenSubject gruppenSubject_;

    
    public CharaktermanagerController() {
    }

    
    
    @FXML
    private void initialize() {
        List<Spieler> spielerList = SpielerManipulator.getInstance().getAll();
        gruppenManagerController.initialize(spielerList);
        gruppenManagerController.setGruppenSubject_(gruppenSubject_);
        spielerManagerController.initialize(spielerList, gruppenManagerController);
        gegnerManagerController.initialize();
    }



    public void setGruppenSubject_(GruppenSubject gruppenSubject_) {
        this.gruppenSubject_ = gruppenSubject_;
    }
}


