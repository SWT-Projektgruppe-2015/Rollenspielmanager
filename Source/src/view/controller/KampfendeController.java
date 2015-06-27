package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.ExpCategory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Gegenstand;
import model.GegnerEinheit;
import model.Spieler;

public class KampfendeController {
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
    
    
    public void initialize(List<Spieler> allSpieler, ObservableList<GegnerEinheit> allParticipatingGegner) {
        participatingGegner_ = allParticipatingGegner;
        
        gegnerTableView_.setItems(allParticipatingGegner);
        gegnerColumn_.setCellValueFactory(new PropertyValueFactory<GegnerEinheit, String>("name_"));
        
        initializeExpTable(allSpieler);
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
