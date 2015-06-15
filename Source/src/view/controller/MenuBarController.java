package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MenuBarController {
    private String aboutThisWindow_;
    
    public void setAboutWindow(String about) {
        aboutThisWindow_ = about;
    }
    
    @FXML
    public void onAboutWindowClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Hilfe zum aktuellen Fenster");
        alert.setHeaderText(null);
        alert.setContentText(aboutThisWindow_);

        alert.showAndWait();
    }
    
    @FXML
    public void onAboutUsClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Über das Produkt");
        alert.setHeaderText(null);
        alert.setContentText("Test");

        alert.showAndWait();
    }
}
