package view;

import controller.Hauptprogramm;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class MainMenuController {
    @FXML
    private ComboBox<String> gruppenDropDown;

    private Hauptprogramm hauptProgramm;

    public MainMenuController() {
    }

    
    
    @FXML
    private void initialize() {
    	gruppenDropDown.getItems().addAll("Keine Gruppe ausgewählt", "Test");
        gruppenDropDown.setValue("Keine Gruppe ausgewählt");
    }

    
    
    public void setHauptProgramm(Hauptprogramm hauptProgramm) {
        this.hauptProgramm = hauptProgramm;
    }
}
