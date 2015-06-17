package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MenuBarController {
    public static final String ABOUT_THE_PRODUCT = 
            "Die 'DLVC Taverne' ist 2015 in der Projektgruppe 'Angewandte Softwaretechnologie' ins Leben gerufen worden.\n"
            + "Ihr Ziel ist es, Rollenspielleitern des Pen&Paper-Rollenspiels 'Die Legenden von Cysteron' in der "
            + "Ausf" + Hauptprogramm.UMLAUT_SMALL_UE + "hrung ihrer T" + Hauptprogramm.UMLAUT_SMALL_AE+ "tigkeit zu "
            + "unterst" + Hauptprogramm.UMLAUT_SMALL_UE + "tzen.";
    
    private String aboutThisWindow_;
    private String windowTitle_;
    
    public void setAboutWindow(String title, String about) {
        windowTitle_ = title;
        aboutThisWindow_ = about;
    }
    
    @FXML
    public void onAboutWindowClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Hilfe zum aktuellen Fenster");
        alert.setHeaderText(windowTitle_);
        alert.setContentText(aboutThisWindow_);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, aboutThisWindow_.length() / 3 + 100);

        alert.showAndWait();
    }
    
    @FXML
    public void onAboutUsClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Über DLVC Taverne");
        alert.setHeaderText(null);
        alert.setContentText(ABOUT_THE_PRODUCT);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(400, 200);

        alert.showAndWait();
    }
}
