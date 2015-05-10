package view;

import java.util.List;

import controller.SpielerManipulator;
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
}


