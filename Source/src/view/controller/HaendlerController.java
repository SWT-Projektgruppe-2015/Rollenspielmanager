package view.controller;

import org.controlsfx.control.NotificationPane;

import javafx.fxml.FXML;

public class HaendlerController {
    @FXML
    private InventarHaendlerController inventarHaendlerController;
    @FXML
    private AusruestungsHaendlerController ausruestungsHaendlerController;
    
    @FXML
    private void initialize() {        
        inventarHaendlerController.initialize();
        ausruestungsHaendlerController.initialize();
    }

    public void setNotificationPane(NotificationPane notificationPane) {
        inventarHaendlerController.setNotificationPane(notificationPane);
        ausruestungsHaendlerController.setNotificationPane(notificationPane);
    }
}
