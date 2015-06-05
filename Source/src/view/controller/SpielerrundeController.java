package view.controller;

import java.util.List;

import view.tabledata.SharedGegnerTableEntry;
import view.tabledata.SpielerMitWaffe;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Spieler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class SpielerrundeController {
    @FXML
    private TableView<SpielerMitWaffe> spielerTableView_;
    @FXML
    private TableColumn<SpielerMitWaffe, String> spielerNameColumn_;
    @FXML
    private TableColumn<SpielerMitWaffe, String> waffenNameColumn_;
    
    @FXML
    private TreeTableView<SharedGegnerTableEntry> gegnerTreeTableView_;
    @FXML
    private TreeTableColumn<SharedGegnerTableEntry, String> gegnerNameColumn_;
    @FXML
    private TreeTableColumn<SharedGegnerTableEntry, String> gegnerDealtSchadenColumn_;
    @FXML
    private TreeTableColumn<SharedGegnerTableEntry, String> gegnerLebenspunkteColumn_;
    
    public void initialize(List<Spieler> allSpieler, List<GegnerEinheit> allGegner) {
        initializeSpielerTableView(allSpieler);      
        setCellValueFactoriesForSpieler();
        
        initializeGegnerTreeTableView(allGegner);          
        setCellValueFactoriesForGegner();        
    }

    private void initializeSpielerTableView(List<Spieler> allSpieler) {
        for(Spieler spieler : allSpieler) {
            spielerTableView_.getItems().add(new SpielerMitWaffe(spieler));
        }
    }

    private void initializeGegnerTreeTableView(List<GegnerEinheit> allGegner) {
        final TreeItem<SharedGegnerTableEntry> dummyRoot = new TreeItem<>(new GegnerTyp());
        dummyRoot.setExpanded(true);
        gegnerTreeTableView_.setRoot(dummyRoot);
        gegnerTreeTableView_.showRootProperty().set(false);
        
        if(!allGegner.isEmpty()) {
            addGegnerToTable(allGegner, dummyRoot);
        }
    }

    private void addGegnerToTable(List<GegnerEinheit> allGegner,
            final TreeItem<SharedGegnerTableEntry> dummyRoot) {
        
        GegnerTyp currentTyp = null;        
        TreeItem<SharedGegnerTableEntry> typRoot = null;      
        for(GegnerEinheit gegner : allGegner) {
            boolean typIsDifferentFromGegner = currentTyp == null ||
                    !gegner.getTyp_().getName_().equals(currentTyp.getName_());
            
            if(typIsDifferentFromGegner) {
                currentTyp = gegner.getTyp_();
                typRoot = createGegnerTypRoot(currentTyp);
                dummyRoot.getChildren().add(typRoot);
            }
            typRoot.getChildren().add(new TreeItem<>(gegner));
        }
    }

    private TreeItem<SharedGegnerTableEntry> createGegnerTypRoot(GegnerTyp currentTyp) {
        TreeItem<SharedGegnerTableEntry> typRoot;
        typRoot = new TreeItem<>(currentTyp);     
        typRoot.setExpanded(true);
      
        return typRoot;
    }

    private void setCellValueFactoriesForSpieler() {
        assignTableColumnProperty(spielerNameColumn_, "spielerName_");
        assignTableColumnProperty(waffenNameColumn_, "waffenName_");
    }

    private void assignTableColumnProperty(TableColumn<SpielerMitWaffe, String> column, String propertyName) {
        column.setCellValueFactory(new PropertyValueFactory<SpielerMitWaffe, String>(propertyName));
    }

    private void setCellValueFactoriesForGegner() {
        assignTreeTableColumnProperty(gegnerNameColumn_, "name");
        assignTreeTableColumnProperty(gegnerDealtSchadenColumn_, "dealtSchaden");
        assignTreeTableColumnProperty(gegnerLebenspunkteColumn_, "lebenspunkte");
    }

    private void assignTreeTableColumnProperty(TreeTableColumn<SharedGegnerTableEntry, String> column, String propertyName) {
        column.setCellValueFactory(new TreeItemPropertyValueFactory<SharedGegnerTableEntry, String>(propertyName));
    }
    
}
