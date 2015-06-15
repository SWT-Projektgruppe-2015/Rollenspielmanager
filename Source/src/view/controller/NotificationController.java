package view.controller;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;

import view.NotificationTexts;

public class NotificationController {
    private NotificationPane notification_;
    
    public void setNotificationPane(NotificationPane notification) {
        notification_ = notification;
    }
    
    protected void createNotification(String text) {
        notification_.setText(text);
        notification_.getActions().clear();
        notification_.show();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(
             new Runnable() { @Override public void run() { 
                 notification_.hide(); } },
             3, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
    
    protected void createReallyDeleteDialog(String text, Action afterConfirmationAction) {
        createConfirmationDialog(text, NotificationTexts.DELETE_INFORMATION, NotificationTexts.DELETE_TITLE, afterConfirmationAction);
    }
    
    protected void createConfirmationDialog(String text, String furtherDescription, String confirmTitle, Action afterConfirmationAction) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(confirmTitle);
        alert.setHeaderText(text);
        alert.setContentText(furtherDescription);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            afterConfirmationAction.handle(new javafx.event.ActionEvent());
        }
    }
}
