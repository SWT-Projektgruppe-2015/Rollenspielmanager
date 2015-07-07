package view.controller;

import view.NotificationTexts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;

public class MaxTextLengthListener implements ChangeListener<String>{
    private TextInputControl textInputControl_;
    private NotificationController notificationController_;
    private int maxLength_;
    
    public MaxTextLengthListener(TextInputControl textInputControl, NotificationController notificationController, int maxLength) {
        textInputControl_ = textInputControl;
        notificationController_ = notificationController;
        maxLength_ = maxLength;
    }
    
    @Override
    public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {
        if (newValue == null) {
            return;
        }


        if (newValue.length() > maxLength_) {
            textInputControl_.setText(oldValue);
            notificationController_.createNotification(NotificationTexts.textForMaxTextFieldLengthExceeded(maxLength_));
        } else {
            textInputControl_.setText(newValue);
        }
    }
    
}
