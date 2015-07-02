package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.SchadenAmSpieler;
import controller.Dice;
import model.GegnerEinheit;
import model.Spieler;
import model.Waffen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class GegnerrundeController {
    
    private List<SchadenAmSpieler> schadenAmSpielerListe_;
    
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
    
    private SpielerrundeController spielerrundeController_;
    
    public GegnerrundeController() {
        schadenAmSpielerListe_ = new ArrayList<SchadenAmSpieler>();
    }
    
    @FXML
    private void initialize() {
        schadenColumn_.setStyle( "-fx-alignment: CENTER;");
        spielerNameColumn_.setStyle( "-fx-alignment: CENTER-LEFT;");
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
    
    public void initializeParameters(List<Spieler> spielerListe, ObservableList<GegnerEinheit> gegnerListe, SpielerrundeController controller) {
        spielerrundeController_ = controller;
        
        for(Spieler spieler: spielerListe) {
            schadenAmSpielerListe_.add(new SchadenAmSpieler(spieler));
        }
        gegnerListView_.setItems(gegnerListe);
        schadensAnzeigeTableView_.getItems().setAll(schadenAmSpielerListe_);
        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Number>("schaden_"));
        trefferZoneColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("zone_"));
        trefferZoneColumn_.setCellFactory(
                new Callback<TableColumn<SchadenAmSpieler, String>, TableCell<SchadenAmSpieler, String>>() {
                    @Override
                    public TableCell<SchadenAmSpieler,String> call(TableColumn<SchadenAmSpieler,String> tableColumn)  {
                        return new TrefferzoneCell();
                    }
                }
            );
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
        if(selectedGegner == null) return;
        for(SchadenAmSpieler schadenAmSpieler: schadenAmSpielerListe_){
            Spieler spieler = schadenAmSpieler.getSpieler_();
            int geschickWurf = simulateGeschickWurf(selectedGegner, spieler);
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
    public int simulateGeschickWurf(GegnerEinheit selectedGegner, Spieler spieler) {
        Waffen waffe = spielerrundeController_.getCurrentWaffeFromSpieler(spieler);
        int geschick = selectedGegner.getGeschick_() - spieler.getTotalGeschickMalus(waffe);
        int wuerfelErgebnis = Dice.rollGeschick(Math.max(geschick, 1));
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
        int lebensVerlust = spieler.getLebensverlust(schaden, geschickWurf, 0);
        return lebensVerlust;
    }
    
    

    public void setSpielerRundeController(
            SpielerrundeController spielerrundeController) {
        spielerrundeController_ = spielerrundeController;
        
    }
}