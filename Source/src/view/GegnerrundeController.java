package view;

import java.util.List;

import model.Gegner;
import model.Gruppe;
import model.Spieler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GegnerrundeController {
    
    private List<SchadenAmSpieler> schadenAmSpielerListe_;
    private List<Gegner> gegnerListe_;
    
    @FXML
    private Button kampfButton;
    
    @FXML
    private ListView<Gegner> gegnerListView_;
    
    @FXML
    private TableView<SchadenAmSpieler> schadensAnzeigeTableView_;
    
    @FXML
    private TableColumn<SchadenAmSpieler, String> spielerNameColumn_;
    
    @FXML
    private TableColumn<SchadenAmSpieler, Integer> schadenColumn_;
    
    void initialize(List<Spieler> spielerListe, List<Gegner> gegnerListe) {
        gegnerListView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Gegner>(){
            @Override
          public void changed(
                  ObservableValue<? extends Gegner> observable,
                  Gegner oldValue, Gegner newValue) {
              updateSchadenAmSpielerTable(newValue);
          }
        });
        
        for(Spieler spieler: spielerListe)
            schadenAmSpielerListe_.add(new SchadenAmSpieler(spieler));
        gegnerListe_ = gegnerListe;
        gegnerListView_.getItems().setAll(gegnerListe_);
        schadensAnzeigeTableView_.getItems().setAll(schadenAmSpielerListe_);
        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Integer>("schaden_"));
    }        
    
    
    
    private void updateSchadenAmSpielerTable(Gegner newValue) {
        // TODO Auto-generated method stub
    }
}
