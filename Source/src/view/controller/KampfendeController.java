package view.controller;

import java.util.List;

import view.tabledata.ExpCategory;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    
    
    public void initialize(List<Spieler> allSpieler) {
        
    }
    
}
