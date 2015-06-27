package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.NotificationTexts;
import view.tabledata.ExpCategory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
    
    private ObservableList<ExpCategory> expCategoriesList_;
    private ObservableList<GegnerEinheit> participatingGegner_;
    private ObservableList<Gegenstand> beuteList_;
    
    public void initialize(List<Spieler> allSpieler, ObservableList<GegnerEinheit> allParticipatingGegner) {
        participatingGegner_ = allParticipatingGegner;
        beuteList_ = FXCollections.observableList(new ArrayList<Gegenstand>());
        
        initializeGegnerTable(); 
        initializeExpTable(allSpieler);
        initializeBeuteTable();
    }

    
    
    private void initializeBeuteTable() {
        beuteTableView_.setItems(beuteList_);
        beuteColumn_.setCellValueFactory(new PropertyValueFactory<Gegenstand, String>("name_"));
        traglastColumn_.setCellValueFactory(new PropertyValueFactory<Gegenstand, Integer>("traglast_"));
    }



    @FXML
    public void onGeneriereBeuteClick() {
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
        
        createNotification(NotificationTexts.textForBeuteGenerator(inventarWert, inventarStreuung, ausruestungsMalus, ausruestungStreuung));
        beuteList_.clear();
        for(GegnerEinheit gegner : participatingGegner_) {
            generateInventarBeuteFromGegner(inventarWert, inventarStreuung);
            generateAusruestungsBeuteFromGegner(gegner, ausruestungsMalus, ausruestungStreuung);
        }
    }



    private void generateInventarBeuteFromGegner(int inventarWert, int inventarStreuung) {
        InventarBeute inventar = new InventarBeute(inventarWert, inventarStreuung);
        inventar.generateInventarBeute();
        if(inventar.getInventarBeute() != null)
            beuteList_.addAll(inventar.getInventarBeute());
    }
    

    
    private void generateAusruestungsBeuteFromGegner(GegnerEinheit gegner,
            int ausruestungsMalus, int ausruestungStreuung) {
        AusruestungBeute ausruestung = new AusruestungBeute(ausruestungsMalus, ausruestungStreuung);
        Gegenstand ausruestungsTeil = ausruestung.generateAusruestungBeute(gegner);
        if(ausruestungsTeil != null)
            beuteList_.add(ausruestungsTeil);
    }



    private void initializeGegnerTable() {
        gegnerTableView_.setItems(participatingGegner_);
        gegnerColumn_.setCellValueFactory(new PropertyValueFactory<GegnerEinheit, String>("name_"));
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
