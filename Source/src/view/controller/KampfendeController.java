package view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.NotificationTexts;
import view.tabledata.ExpCategory;
import view.tabledata.SchadenAmSpieler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.AusruestungBeute;
import model.Gegenstand;
import model.GegnerEinheit;
import model.InventarBeute;
import model.Spieler;

public class KampfendeController extends NotificationController {
    @FXML
    private TableView<GegnerEinheit> gegnerTableView_;
    @FXML
    private TableColumn<GegnerEinheit, String> gegnerColumn_;
    
    @FXML
    private TableView<ExpCategory> expTableView_;
    @FXML
    private TableColumn<ExpCategory, String> spielereinflussColumn_;
    @FXML
    private TableColumn<ExpCategory, Integer> expColumn_;
    
    @FXML
    private TableView<Gegenstand> beuteTableView_;
    @FXML
    private TableColumn<Gegenstand, String> beuteColumn_;
    @FXML
    private TableColumn<Gegenstand, Integer> traglastColumn_;
    
    @FXML
    private TextField beuteWertTextField_;
    @FXML
    private TextField beuteKategorieTextField_;
    
    @FXML
    private TextField inventarWertTextField_;
    @FXML
    private TextField inventarStreuungTextField_;
    @FXML
    private TextField ausruestungMalusTextField_;
    @FXML
    private TextField ausruestungStreuungTextField_;
    @FXML
    private TextField geldTextField_;
    
    
    private Map<GegnerEinheit, InventarBeute> inventarBeute_;
    private Map<GegnerEinheit, AusruestungBeute> ausruestungBeute_;
    
    private ObservableList<ExpCategory> expCategoriesList_;
    private ObservableList<GegnerEinheit> participatingGegner_;
    private ObservableList<Gegenstand> beuteList_;
    
    public void initialize(List<Spieler> allSpieler, ObservableList<GegnerEinheit> allParticipatingGegner) {
        participatingGegner_ = allParticipatingGegner;
        beuteList_ = FXCollections.observableList(new ArrayList<Gegenstand>());
        
        inventarBeute_ = new HashMap<GegnerEinheit, InventarBeute>();
        ausruestungBeute_ = new HashMap<GegnerEinheit, AusruestungBeute>();
        initializeGegnerTable(); 
        initializeExpTable(allSpieler);
        initializeBeuteTable();
    }
    
    

    @FXML
    public void onGeneriereBeuteClick() {
        GegnerEinheit selectedGegner = gegnerTableView_.getSelectionModel().getSelectedItem();
        if(selectedGegner == null) {
            createNotification(NotificationTexts.textForGenerateBeuteWithoutSelectedGegner());
            return;
        }
        String inventarWertString = inventarWertTextField_.getText();
        String inventarStreuungString = inventarStreuungTextField_.getText();
        String ausruestungsMalusString = ausruestungMalusTextField_.getText();
        String ausruestungStreuungString = ausruestungStreuungTextField_.getText();
        
        int inventarWert, inventarStreuung;
        int ausruestungsMalus, ausruestungStreuung;
        try {
            inventarWert = Integer.parseUnsignedInt(inventarWertString);
            inventarStreuung = Integer.parseUnsignedInt(inventarStreuungString);
            ausruestungsMalus = Integer.parseInt(ausruestungsMalusString);
            ausruestungStreuung = Integer.parseUnsignedInt(ausruestungStreuungString);
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.textForBeuteGeneratingFailed());
            return;
        }
        
        createNotification(NotificationTexts.textForBeuteGenerator(selectedGegner));
        beuteList_.clear();
        generateInventarBeuteFromGegner(selectedGegner, inventarWert, inventarStreuung);
        generateAusruestungsBeuteFromGegner(selectedGegner, ausruestungsMalus, ausruestungStreuung);
    }



    private void generateInventarBeuteFromGegner(GegnerEinheit gegner, int inventarWert, int inventarStreuung) {
        InventarBeute inventarBeute = new InventarBeute(inventarWert, inventarStreuung);
        inventarBeute.generateInventarBeute();
        if(inventarBeute.getInventarBeute() != null) {
            beuteList_.addAll(inventarBeute.getInventarBeute());
            inventarBeute_.put(gegner, inventarBeute);
        }
        geldTextField_.setText(Integer.toString(inventarBeute.getGeldWert()));
    }
    

    
    private void generateAusruestungsBeuteFromGegner(GegnerEinheit gegner,
            int ausruestungsMalus, int ausruestungStreuung) {
        AusruestungBeute ausruestungBeute = new AusruestungBeute(ausruestungsMalus, ausruestungStreuung);
        Gegenstand ausruestungsTeil = ausruestungBeute.generateAusruestungBeute(gegner);
        if(ausruestungsTeil != null){
            beuteList_.add(ausruestungsTeil);
            ausruestungBeute_.put(gegner, ausruestungBeute);
        }
    }



    private void initializeGegnerTable() {
        gegnerTableView_.setItems(participatingGegner_);
        gegnerColumn_.setCellValueFactory(new PropertyValueFactory<GegnerEinheit, String>("name_"));
        gegnerColumn_.setCellFactory(
                new Callback<TableColumn<GegnerEinheit, String>, TableCell<GegnerEinheit, String>>() {
                    @Override
                    public TableCell<GegnerEinheit, String> call(TableColumn<GegnerEinheit, String> tableColumn)  {
                        return new GegnerStateCell();
                    }
                }
            );
        gegnerTableView_.getSelectionModel().setCellSelectionEnabled(true);
        addListenerToGegnerTableView();
    }
    
    
    
    private void addListenerToGegnerTableView() {
        gegnerTableView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<GegnerEinheit>() {

            @Override
            public void changed(ObservableValue<? extends GegnerEinheit> arg0,
                    GegnerEinheit oldVal, GegnerEinheit newVal) {
                updateBeute(newVal);
            }
        });
    }
    
    
    
    private void updateBeute(GegnerEinheit newVal) {
        beuteList_.clear();
        geldTextField_.clear();
        InventarBeute inventar = inventarBeute_.get(newVal);
        AusruestungBeute ausruestung = ausruestungBeute_.get(newVal);
        if(inventar != null) {
            if(inventar.getInventarBeute() != null)
                beuteList_.addAll(inventar.getInventarBeute());
            geldTextField_.setText(Integer.toString(inventar.getGeldWert()));
        }
        if(ausruestung != null && ausruestung.getAusruestungBeute() != null)
            beuteList_.addAll(ausruestung.getAusruestungBeute());
    }

    

    private void initializeExpTable(List<Spieler> allSpieler) {
        spielereinflussColumn_.setCellValueFactory(new PropertyValueFactory<ExpCategory, String>("name_"));
        expColumn_.setCellValueFactory(new PropertyValueFactory<ExpCategory, Integer>("exp_"));
        
        int totalExp = getTotalExp(participatingGegner_);
        List<ExpCategory> expCategories = extractExpCategories(allSpieler, totalExp);        
        expCategoriesList_ = FXCollections.observableList(expCategories);
        expTableView_.setItems(expCategoriesList_);
        
        participatingGegner_.addListener(new ListChangeListener<GegnerEinheit>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends GegnerEinheit> change) {
                int totalExp = getTotalExp(participatingGegner_);
                List<ExpCategory> expCategories = extractExpCategories(allSpieler, totalExp);
                expCategoriesList_.setAll(expCategories);
            }
        });
    }

    
    
    private void initializeBeuteTable() {
        beuteTableView_.setItems(beuteList_);
        beuteColumn_.setCellValueFactory(new PropertyValueFactory<Gegenstand, String>("name_"));
        traglastColumn_.setCellValueFactory(new PropertyValueFactory<Gegenstand, Integer>("traglast_"));
        
        beuteTableView_.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Gegenstand>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Gegenstand> observable,
                            Gegenstand oldValue, Gegenstand newValue) {
                        showGegenstandDetails(newValue);
                    }
                });
    }

    
    
    private void showGegenstandDetails(Gegenstand beute) {
        if(beute == null) {
            beuteWertTextField_.clear();
            beuteKategorieTextField_.clear();
        } else {
            beuteWertTextField_.setText(beute.getWert_());
            beuteKategorieTextField_.setText(beute.getKategorie_());
        }
    }

    
    
    private int getTotalExp(ObservableList<GegnerEinheit> allParticipatingGegner) {
        int sum = 0;
        for(GegnerEinheit einheit : allParticipatingGegner) {
            sum += einheit.getErfahrung_();
        }
        return sum;
    }


    private List<ExpCategory> extractExpCategories(List<Spieler> allSpieler, int totalExp) {
        List<ExpCategory> expCategories = new ArrayList<ExpCategory>();
        expCategories.add(new ExpCategory(null, totalExp));
        for(Spieler spieler : allSpieler) {
            if(spieler.getExpFactor() != 1.) {
                expCategories.add(new ExpCategory(spieler, totalExp));
            }
        }
        return expCategories;
    }
}
