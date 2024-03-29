package view.controller;

import view.tabledata.SpielerMitWaffe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Spieler;
import model.Waffen;

public class WaffenwechselController {
    private Spieler spieler_;
    private SpielerrundeController controller_;
    private Stage stage_;
    
    @FXML
    private ListView<Waffen> waffenListView_;
    @FXML
    private TextField schadenTextField_;
    @FXML
    private Label effektTypTitleLabel_;
    @FXML
    private Label effektTypLabel_;
    @FXML
    private Label effektWertTitleLabel_;
    @FXML
    private Label effektWertLabel_;
    
    public void initialize(SpielerrundeController controller, SpielerMitWaffe spielerMitWaffe) {
        controller_ = controller;
        spieler_ = spielerMitWaffe.getSpieler();
        
        waffenListView_.getItems().setAll(spieler_.getWaffen());
        waffenListView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Waffen>() {
            public void changed(
                    ObservableValue<? extends Waffen> observable,
                    Waffen oldValue, Waffen newValue) {
                schadenTextField_.setText(Integer.toString(newValue.getWaffenSchaden_()));
                updateEffekte(newValue);
            }
        });
        waffenListView_.getSelectionModel().select(spielerMitWaffe.getWaffe());
    }
    
    
    
    @FXML
    private void changeWaffe() {
        int selectedIndex = waffenListView_.getSelectionModel().getSelectedIndex();
        Waffen selectedWaffe = waffenListView_.getItems().get(selectedIndex);
        
        controller_.changeSpielerWaffe(spieler_, selectedWaffe);
        stage_.close();
    }
    
    
    
    private void updateEffekte(Waffen waffe) {
        if(waffe.getEffektTyp_() == null) {
            hideEffektLabels();
        } else {
            showEffektLabels();
            effektTypLabel_.setText(waffe.getEffektTyp_().toString());
            effektWertLabel_.setText(Integer.toString(waffe.getEffektWert_()));
        }
    }
    
    
    
    private void hideEffektLabels() {
        changeEffektLabelVisibility(false);
    }
    
    
    
    private void showEffektLabels() {
        changeEffektLabelVisibility(true);
    }
    
    
    
    private void changeEffektLabelVisibility(boolean visible) {
        effektTypTitleLabel_.setVisible(visible);
        effektTypLabel_.setVisible(visible);
        effektWertTitleLabel_.setVisible(visible);
        effektWertLabel_.setVisible(visible);
    }



    public void setStage(Stage stage) {
        stage_ = stage;
    }
}
