package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.SchadenAmSpieler;
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
//    private ObservableList<Gegner> gegnerListe_;
    
    @FXML
    private Button kampfButton;
    @FXML
    private ListView<Gegner> gegnerListView_;
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
        gegnerListView_.getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<Gegner>(){
                @Override
              public void changed(
                      ObservableValue<? extends Gegner> observable,
                      Gegner oldValue, Gegner newValue) {
                  updateSchadenAmSpielerTable(newValue);
              }
            }); 
        
//        spielerNameColumn_.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
//        schadenColumn_.setCellValueFactory(cellData -> cellData.getValue().getSchadenProperty());
//        trefferZoneColumn_.setCellValueFactory(cellData -> cellData.getValue().getZoneProperty());
        
        
//        schadensAnzeigeTableView_.setItems(schadenAmSpielerListe_);
        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Number>("schaden_"));
        trefferZoneColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("zone_"));
        
        
    }
    
    public void initializeParameters(List<Spieler> spielerListe, List<Gegner> gegnerListe) {
        for(Spieler spieler: spielerListe)
            schadenAmSpielerListe_.add(new SchadenAmSpieler(spieler));
        gegnerListe_ = gegnerListe;
        gegnerListView_.getItems().setAll(gegnerListe_);
        schadensAnzeigeTableView_.getItems().setAll(schadenAmSpielerListe_);
        spielerNameColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("name_"));
        schadenColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Number>("schaden_"));
        trefferZoneColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, String>("zone_"));
//                new Callback<CellDataFeatures<SchadenAmSpieler, ObjectProperty<Angriff>>, ObservableValue<ObjectProperty<Angriff>>>() {
//                    @Override public ObservableValue<ObjectProperty<Angriff>> call(CellDataFeatures<SchadenAmSpieler, ObjectProperty<Angriff>> c) {
//                        return new SimpleObjectProperty(c.getValue().getTestBedName()));
//                    }
//                }
                
//          spielerNameColumn_.setCellValueFactory(cellData -> cellData.getValue().getName_Property());
//          trefferZoneColumn_.setCellValueFactory(new PropertyValueFactory<SchadenAmSpieler, Integer>("zone_"));
//          trefferZoneColumn_.setCellValueFactory(cellData -> cellData.getValue().getZonenProperty_());
//          trefferZoneColumn_.setCellFactory(column -> {
//              return new TableCell<SchadenAmSpieler, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty){
//                    super.updateItem(item, empty);
//                    if (item == null || empty) {
//                        setText(null);
//                        setStyle("   ");
//                    } else {
////                        setText(item);
////                        setTextFill()
//                        // Format date.
////                        setText(myDateFormatter.format(item));
//
//                        // Style all dates in March with a different color.
////                        if (item.getMonth() == Month.MARCH) {
////                            setTextFill(Color.CHOCOLATE);
//                            setStyle("-fx-background-color: yellow");
////                        } else {
////                            setTextFill(Color.BLACK);
////                            setStyle("   ");
////                        }
//                    }
//                }
//            };
//        });
        
//        trefferZoneColumn_.setCellFactory(new Callback<TableColumn<SchadenAmSpieler, Integer>, TableCell<SchadenAmSpieler, Integer>>() {
//            @Override
//            public TableCell<SchadenAmSpieler, Integer> call(TableColumn<SchadenAmSpieler, Integer> p) {
//
//
//             return new TableCell<SchadenAmSpieler, Integer>() {
//
//            @Override
//            public void updateItem(Integer item, boolean empty) {
//                super.updateItem(item, empty);
//                if (!isEmpty()) {
//                    this.setStyle("-fx-background-color:red");
//                    setText(item.toString());
//                }
//            }
//        };
    }
    
    
    
    @FXML
    public void executeFightButton() {
        Gegner selectedGegner = gegnerListView_.getSelectionModel().getSelectedItem();
        updateSchadenAmSpielerTable(selectedGegner);
    }
    
    
    
    /**
     * Aktualisiert die "schadenAmSpielerTableView_" mit neu simuliertem
     * Lebenspunkteverlust.
     * @param selectedGegner
     */
    protected void updateSchadenAmSpielerTable(Gegner selectedGegner) {
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
    private int simulateGeschickWurf(Gegner selectedGegner) {
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
    protected int simuliereLebensverlustAmSpieler(Gegner selectedGegner, Spieler spieler, int geschickWurf) {
        int schaden = selectedGegner.getDamage();
        int lebensVerlust = spieler.getLebensverlust(schaden, geschickWurf);
        return lebensVerlust;
    }
}