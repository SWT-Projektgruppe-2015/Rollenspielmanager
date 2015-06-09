package view.controller;

import java.util.List;

import view.tabledata.SharedGegnerTableEntry;
import view.tabledata.SpielerMitWaffe;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Spieler;
import model.Waffen;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
   
    private Hauptprogramm main_;
    
    
    
    public void initialize(Hauptprogramm main, List<Spieler> allSpieler, List<GegnerEinheit> allGegner) {
        main_ = main;
        initializeSpielerTableView(allSpieler);      
        setCellValueFactoriesForSpieler();
        
        initializeGegnerTreeTableView(allGegner);          
        setCellValueFactoriesForGegner();        
    }

    
    
    private void initializeSpielerTableView(List<Spieler> allSpieler) {
        SpielerrundeController controller = this;
        waffenNameColumn_.setCellFactory(
            new Callback<TableColumn<SpielerMitWaffe, String>, TableCell<SpielerMitWaffe, String>>() {
                @Override
                public TableCell<SpielerMitWaffe,String> call(TableColumn<SpielerMitWaffe,String> tableColumn)  {
                    TableCell<SpielerMitWaffe,String> cell = new TableCell<SpielerMitWaffe, String>()   {
                        @Override
                        protected void updateItem(String item, boolean empty)   {
                            super.updateItem(item, empty);
                            setText(item);
                            
                            if(!empty)  {
                                SpielerMitWaffe currentSpieler = getTableView().getItems().get(getTableRow().getIndex());
                                if(currentSpieler.isArmed())
                                    setTooltip(new Tooltip("Schaden: " + Integer.toString(currentSpieler.getWaffe().getWaffenSchaden_())));
                                else
                                    setTooltip(new Tooltip("Spieler hat keine Waffe"));
                            }
                        }
                    };
                    
                    cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() > 1) {
                                TableCell<SpielerMitWaffe,String> c = 
                                        (TableCell<SpielerMitWaffe, String>) event.getSource();
                                SpielerMitWaffe currentSpieler = (SpielerMitWaffe) c.getTableView().getItems().get(c.getTableRow().getIndex());
                                if(currentSpieler.isArmed())
                                    main_.openWaffenwechsel(controller, currentSpieler);
                            }
                        }
                    });
                    
                    
                    
                    return cell;
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
    
}
