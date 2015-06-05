package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.SchadenAmSpieler;
import controller.Dice;
import model.GegnerEinheit;
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
    private List<GegnerEinheit> gegnerListe_;
    
    @FXML
    private Button kampfButton;
    @FXML
    private ListView<GegnerEinheit> gegnerListView_;
    @FXML
    private TableView<SchadenAmSpieler> schadensAnzeigeTableView_;
    @FXML
    private TableColumn<SchadenAmSpieler, String> spielerNameColumn_;
    @FXML
    private TableColumn<SchadenAmSpieler, Number> schadenColumn_;
    
    @FXML
    private TableColumn<SchadenAmSpieler, String> trefferZoneColumn_;
    
    public GegnerrundeController() {
        schadenAmSpielerListe_ = new ArrayList<SchadenAmSpieler>();
    }
    
    @FXML
    private void initialize() {
        schadenColumn_.setStyle( "-fx-alignment: CENTER;");
        gegnerListView_.getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<GegnerEinheit>(){
                @Override
              public void changed(
                      ObservableValue<? extends GegnerEinheit> observable,
                      GegnerEinheit oldValue, GegnerEinheit newValue) {
                  updateSchadenAmSpielerTable(newValue);
              }
            }); 

        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Number>("schaden_"));
        trefferZoneColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("zone_"));  
    }
    
    public void initializeParameters(List<Spieler> spielerListe, List<GegnerEinheit> gegnerListe) {
        for(Spieler spieler: spielerListe)
            schadenAmSpielerListe_.add(new SchadenAmSpieler(spieler));
        gegnerListe_ = gegnerListe;
        gegnerListView_.getItems().setAll(gegnerListe_);
        schadensAnzeigeTableView_.getItems().setAll(schadenAmSpielerListe_);
        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Number>("schaden_"));
        trefferZoneColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("zone_"));
    }
    
    
    
    @FXML
    public void executeFightButton() {
        GegnerEinheit selectedGegner = gegnerListView_.getSelectionModel().getSelectedItem();
        updateSchadenAmSpielerTable(selectedGegner);
    }
    
    
    
    /**
     * Aktualisiert die "schadenAmSpielerTableView_" mit neu simuliertem
     * Lebenspunkteverlust.
     * @param selectedGegner
     */
    protected void updateSchadenAmSpielerTable(GegnerEinheit selectedGegner) {
        // TODO: Faerbung der Schadensfelder wird noch nicht gemacht und Anzeigen bei deselektierung clearen.
        if(selectedGegner == null) return;
        for(SchadenAmSpieler schadenAmSpieler: schadenAmSpielerListe_){
            Spieler spieler = schadenAmSpieler.getSpieler_();
            int geschickWurf = simulateGeschickWurf(selectedGegner);
            int lebensVerlust = simuliereLebensverlustAmSpieler(selectedGegner, spieler, geschickWurf);
            schadenAmSpieler.setSchaden_(lebensVerlust);
            schadenAmSpieler.setZone_(geschickWurf);
        }
        schadensAnzeigeTableView_.getItems().setAll(schadenAmSpielerListe_);
    }


    
    /**
     * simuliert den GeschickWurf des selectedGegners.
     * @param selectedGegner
     * @return
     */
    private int simulateGeschickWurf(GegnerEinheit selectedGegner) {
        int geschick = selectedGegner.getGeschick_();
        int wuerfelErgebnis = Dice.rollGeschick(geschick);
        return wuerfelErgebnis;
    }

 
    
    /**
     * berechnet den Lebensverlust des Spielers durch den Schaden des Gegners, abhaengig vom geschickWurf
     * @param selectedGegner
     * @param spieler
     * @param geschickWurf 
     * @return verlorene Lebenspunkte von 'spieler' durch 'selectedGegner'
     */
    protected int simuliereLebensverlustAmSpieler(GegnerEinheit selectedGegner, Spieler spieler, int geschickWurf) {
        int schaden = selectedGegner.getSchaden_();
        int lebensVerlust = spieler.getLebensverlust(schaden, geschickWurf);
        return lebensVerlust;
    }
}