package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

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
        Alert alert = getScrollingAlert(aboutThisWindow_, 600, 400);
        alert.setTitle("Hilfe zum aktuellen Fenster");
        alert.setHeaderText(windowTitle_);

        alert.showAndWait();
    }

    
    
    @FXML
    public void onAboutUsClick() {
        Alert alert = getScrollingAlert(ABOUT_THE_PRODUCT, 400, 200);
        alert.setTitle("Über DLVC Taverne");
        alert.setHeaderText(null);

        alert.showAndWait();
    }
    
    
    
    private Alert getScrollingAlert(String content, int width, int height) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(width, height);
        
        ScrollPane pane = new ScrollPane();
        Label text = new Label(content);
        pane.setContent(text);
        pane.setStyle("-fx-background-color: -fx-background");

        text.setWrapText(true);
        pane.setFitToWidth(true);
        pane.setHbarPolicy(ScrollBarPolicy.NEVER);
        
        alert.getDialogPane().setContent(pane);
        
        return alert;
    }
}
