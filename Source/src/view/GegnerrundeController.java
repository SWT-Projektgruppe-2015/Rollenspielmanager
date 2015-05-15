package view;

import java.util.ArrayList;
import java.util.List;

import controller.Dice;
import model.Gegner;
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
    
    public void initialize(List<Spieler> spielerListe, List<Gegner> gegnerListe) {
        gegnerListView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Gegner>(){
            @Override
          public void changed(
                  ObservableValue<? extends Gegner> observable,
                  Gegner oldValue, Gegner newValue) {
              updateSchadenAmSpielerTable(newValue);
          }
        });
        
        schadenAmSpielerListe_ = new ArrayList<SchadenAmSpieler>();
        for(Spieler spieler: spielerListe)
            schadenAmSpielerListe_.add(new SchadenAmSpieler(spieler));
        gegnerListe_ = gegnerListe;
        gegnerListView_.getItems().setAll(gegnerListe_);
        schadensAnzeigeTableView_.getItems().setAll(schadenAmSpielerListe_);
        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Integer>("schaden_"));
    }        
    
    
    /**
     * Aktualisiert die "schadenAmSpielerTableView_" mit neu simuliertem
     * Lebenspunkteverlust.
     * @param selectedGegner
     */
    protected void updateSchadenAmSpielerTable(Gegner selectedGegner) {
        // TODO: F�rbung der Schadensfelder wird noch nicht gemacht.
        if(selectedGegner == null) return;
        for(SchadenAmSpieler schadenAmSpieler: schadenAmSpielerListe_){
            Spieler spieler = schadenAmSpieler.getSpieler_();
            int lebensVerlust = simuliereLebensverlustAmSpieler(selectedGegner, spieler);
            schadenAmSpieler.setSchaden_(lebensVerlust);
        }
    }


    /**
     * simuliert einen Angriff des Gegners am Spieler.
     * @param selectedGegner
     * @param spieler
     * @return verlorene Lebenspunkte von 'spieler' durch 'selectedGegner'
     */
    protected int simuliereLebensverlustAmSpieler(Gegner selectedGegner, Spieler spieler) {
        int geschick = selectedGegner.getGeschick_();
        int schaden = selectedGegner.getDamage();
        int wuerfelErgebnis = Dice.rollGeschick(geschick);
        int lebensVerlust = spieler.getLebensverlust(schaden, wuerfelErgebnis);
        return lebensVerlust;
    }
}
