package view.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.NotificationPane;

public class NotificationController {
    private NotificationPane notification_;
    
    public void setNotificationPane(NotificationPane notification) {
        notification_ = notification;
    }
    
    public void createNotification(String text) {
        notification_.setText(text);        
        notification_.show();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(
             new Runnable() { @Override public void run() { 
                 notification_.hide(); } },
             3, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
}
