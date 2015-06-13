package view.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;

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
    
    protected void createConfirmMessage(String text, String confirmationButtonText, Action afterConfirmationAction) {
        notification_.setText(text);
        afterConfirmationAction.setText(confirmationButtonText);
        notification_.getActions().add(afterConfirmationAction);
        
        notification_.show();
    }
}
