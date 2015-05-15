package view;

import controller.Hauptprogramm;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MainMenuController {
    @FXML
    private ComboBox<String> gruppenDropDown_;
    
    private Hauptprogramm hauptProgramm_;
    
    public MainMenuController() {
    }
    
    
    
    @FXML
    private void initialize() {
        gruppenDropDown_.getItems().addAll("Keine Gruppe ausgewählt", "Test");
        gruppenDropDown_.setValue("Keine Gruppe ausgewählt");
    }
    
    
    
    public void setHauptProgramm(Hauptprogramm hauptProgramm) {
        hauptProgramm_ = hauptProgramm;
    }

    @FXML
    private void onWuerfelSimulatorClick() {
        hauptProgramm_.openWuerfelSimulator();
    }

    @FXML
    private void onCharakterManagerClick() {
        hauptProgramm_.openCharakterManager();
    }
    
    
    
    @FXML
    private void onHaendlerClick() {
        hauptProgramm_.openHaendler();
    }
    
    
    @FXML
    private void onKampfClick() {
        hauptProgramm_.openTeilnehmerauswahl();
    }
}
