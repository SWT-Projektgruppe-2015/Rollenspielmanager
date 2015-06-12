package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.SharedGegnerTableEntry;
import view.tabledata.SpielerMitWaffe;
import model.Charakter;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Spieler;
import model.Waffen;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

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
    @FXML
    private TextField schadenModifierTextField_;
    
    
    private List<GegnerEinheit> removedGegnerEinheiten_;
    private Hauptprogramm main_;
    
    
    
    public void initialize(Hauptprogramm main, List<Spieler> allSpieler, List<GegnerEinheit> allGegner) {
        main_ = main;
        removedGegnerEinheiten_ = new ArrayList<GegnerEinheit>();
        initializeSpielerTableView(allSpieler);      
        setCellValueFactoriesForSpieler();
        
        initializeGegnerTreeTableView(allGegner);          
        setCellValueFactoriesForGegner();    
        setEditActionForGegner();
    }


    
    private void setEditActionForGegner() {
        gegnerLebenspunkteColumn_.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        gegnerLebenspunkteColumn_.setOnEditCommit(
                new EventHandler<TreeTableColumn.CellEditEvent<SharedGegnerTableEntry, String>>() {
                    @Override
                    public void handle(TreeTableColumn.CellEditEvent<SharedGegnerTableEntry, String> t) {
                        changeLebenspunkte(t);
                    }

                }
            );
    }
    
    
    private void changeLebenspunkte(TreeTableColumn.CellEditEvent<SharedGegnerTableEntry, String> t) {
        TreeItem<SharedGegnerTableEntry> changedItem = gegnerTreeTableView_.getSelectionModel().getSelectedItem();
        SharedGegnerTableEntry changedGegner = gegnerTreeTableView_.getSelectionModel().getSelectedItem().getValue();
        if(changedItem == null)    {
            return;
        }
        boolean isGegnerTyp = changedGegner instanceof GegnerTyp;
        if(isGegnerTyp)    {
            return;
        }
        String lebenspunkte = t.getNewValue();
        boolean hasCorrectFormat = lebenspunkte.split("/").length == 2;
        if(lebenspunkte == null || !hasCorrectFormat)    {
            refresh(changedItem, changedGegner);
            return;
        }
        try {
            int currentLebenspunkte = Integer.parseInt(t.getNewValue().split("/")[0]);
            if(currentLebenspunkte <= 0) {
                removeFromKampf();
                return;
            }
            ((GegnerEinheit)changedGegner).setCurrentLebenspunkte_(currentLebenspunkte);
        }
        catch (NumberFormatException e) {
        }
        refresh(changedItem, changedGegner); 
    }


    private void refresh(
            TreeItem<SharedGegnerTableEntry> changedItem,
            SharedGegnerTableEntry changedGegner) {
        changedItem.getParent().getChildren().add(new TreeItem<SharedGegnerTableEntry>(changedGegner));
        changedItem.getParent().getChildren().remove(changedItem);
    }
    
    
    
    @FXML
    private void onHelmButtonClick() {
        dealSchaden(Charakter.LOWERBOUND_HELM);
    }

    
    
    @FXML
    private void onRuestungButtonClick() {
        dealSchaden(Charakter.LOWERBOUND_RUESTUNG);
    }
    
    
    
    @FXML
    private void onDirektButtonClick() {
        int schadenModifier = Integer.parseInt(schadenModifierTextField_.getText());
        dealSchaden(Charakter.LOWERBOUND_DIREKT, schadenModifier);
        schadenModifierTextField_.setText("0");
    }


    
    private void dealSchaden(int wuerfelErgebnis) {
        dealSchaden(wuerfelErgebnis, 0);
    }



    private void dealSchaden(int wuerfelErgebnis, int schadenModifier) {
        int selectedIndex = spielerTableView_.getSelectionModel().getSelectedIndex();
        SpielerMitWaffe selectedSpieler = spielerTableView_.getItems().get(selectedIndex);
        
        updateSchadenAmGegnerTable(selectedSpieler, wuerfelErgebnis, schadenModifier);
    }
    
    
    
    private void updateSchadenAmGegnerTable(SpielerMitWaffe spielerMitWaffe, int wuerfelErgebnis, int schadenModifier) {
        for(TreeItem<SharedGegnerTableEntry> gegnerTyp : gegnerTreeTableView_.getRoot().getChildren()) {
            List<TreeItem<SharedGegnerTableEntry>> einheitenList = new ArrayList<TreeItem<SharedGegnerTableEntry>>();
            for(TreeItem<SharedGegnerTableEntry> gegner : gegnerTyp.getChildren()) {
                SharedGegnerTableEntry entry = gegner.getValue();
                GegnerEinheit einheit = (GegnerEinheit) entry;
                einheit.setDealtSchaden_(schadenDealt(einheit, spielerMitWaffe.getWaffe(), wuerfelErgebnis, schadenModifier));
                einheitenList.add(new TreeItem<SharedGegnerTableEntry>(einheit));
            }
            
            gegnerTyp.getChildren().clear();
            for(TreeItem<SharedGegnerTableEntry> item : einheitenList) {
                gegnerTyp.getChildren().add(item);
            }
        }
    }
    
    
    
    private int schadenDealt(GegnerEinheit gegner, Waffen waffe, int wuerfelErgebnis, int schadenModifier) {
        return gegner.getLebensverlust(waffe.getWaffenSchaden_(), wuerfelErgebnis, schadenModifier);
    }

    
    
    private void initializeSpielerTableView(List<Spieler> allSpieler) {
        SpielerrundeController controller = this;
        
        waffenNameColumn_.setCellFactory(
            new Callback<TableColumn<SpielerMitWaffe, String>, TableCell<SpielerMitWaffe, String>>() {
                @Override
                public TableCell<SpielerMitWaffe,String> call(TableColumn<SpielerMitWaffe,String> tableColumn)  {
                    return new KampfWaffenCell(main_, controller);
                }
            }
        );
        
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



    public void changeSpielerWaffe(Spieler spieler_, Waffen selectedWaffe) {
        for(SpielerMitWaffe spielerMitWaffe : spielerTableView_.getItems()) {
            if(spieler_.getID_() == spielerMitWaffe.getSpieler().getID_()) {
                spielerTableView_.getItems().remove(spielerMitWaffe);
                SpielerMitWaffe changedSpieler = new SpielerMitWaffe(spieler_, selectedWaffe);
                spielerTableView_.getItems().add(changedSpieler);
                spielerTableView_.getItems().sort(null);
                spielerTableView_.getSelectionModel().select(changedSpieler);
                break;
            }
        }
    }
    
    
    
    @FXML
    private void deleteFromKampf()  {
        removeGegnerFromTable(false);
    }
    
    
    
    @FXML
    private void removeFromKampf()  {
        removeGegnerFromTable(true);
    }



    private void removeGegnerFromTable(boolean stillLootable) {
        TreeItem<SharedGegnerTableEntry> selectedItem = gegnerTreeTableView_.getSelectionModel().getSelectedItem();
        if(selectedItem == null)    {
            return;
        }
        boolean hasChildren = !selectedItem.isLeaf(); 
        boolean isGegnerTyp = selectedItem.getValue() instanceof GegnerTyp;
        if(isGegnerTyp && hasChildren)    {
            return;
        }
        if(!isGegnerTyp && stillLootable)  {
            removedGegnerEinheiten_.add((GegnerEinheit)selectedItem.getValue());
        }
        selectedItem.getParent().getChildren().remove(selectedItem);
    }
}