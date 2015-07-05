package view.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import org.controlsfx.control.action.Action;

import view.NotificationTexts;
import view.customcell.KampfWaffenCell;
import view.tabledata.SharedGegnerTableEntry;
import view.tabledata.SpielerMitWaffe;
import model.Charakter;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Spieler;
import model.Waffen;
import model.Waffen.EffektTyp;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class SpielerrundeController extends NotificationController {
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
    
    @FXML
    private TextField staerkeWurfTextField_;
    
    @FXML
    private TextField addedSchadenTextField_;
    
    @FXML
    private GridPane effektPane_;
    
    private Hauptprogramm main_;
    private ObservableList<GegnerEinheit> allExpYieldingGegner_;
    private ObservableList<GegnerEinheit> allActiveGegner_;
    
    
    
    public void initialize(Hauptprogramm main, List<Spieler> allSpieler, 
            ObservableList<GegnerEinheit> allActiveGegner, ObservableList<GegnerEinheit> allExpYieldingGegner) {
        main_ = main;
        allExpYieldingGegner_ = allExpYieldingGegner;
        allActiveGegner_ = allActiveGegner;
        
        initializeSpielerTableView(allSpieler);      
        setCellValueFactoriesForSpieler();
        
        initializeGegnerTreeTableView(allActiveGegner);          
        setCellValueFactoriesForGegner();    
        setEditActionForGegner();
        
        
    }
    
    
    
    public Waffen getCurrentWaffeFromSpieler(Spieler spieler)   {
        for(SpielerMitWaffe currentspieler : spielerTableView_.getItems())  {
            if(currentspieler.getSpieler().compareTo(spieler) == 0) {
                return currentspieler.getWaffe();
            }
        }
        return null;
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
        TreeItem<SharedGegnerTableEntry> changedItem = getSelectedGegnerItem();
        SharedGegnerTableEntry changedGegner = getSelectedGegnerItem().getValue();
        if(changedItem == null)    {
            return;
        }
        boolean isGegnerTyp = changedGegner instanceof GegnerTyp;
        if(isGegnerTyp)    {
            return;
        }
        String lebenspunkte = t.getNewValue();
        if(lebenspunkte == null || lebenspunkte == "")    {
            refresh(changedItem, changedGegner);
            createNotification(NotificationTexts.WRONG_LEBENSPUNKTE_FORMAT);
            return;
        }
        if(lebenspunkte.split("/").length != 2){
           lebenspunkte += "/" + String.valueOf(((GegnerEinheit)changedGegner).getMaxLebenspunkte_());
        }
        try {
            int currentLebenspunkte = Integer.parseInt(t.getNewValue().split("/")[0]);
            if(currentLebenspunkte <= 0) {
                removeFromKampf();
                createNotification(NotificationTexts.textForGegnerRemovedDueToLebenspunkte((GegnerEinheit) changedGegner));
                return;
            }
            ((GegnerEinheit)changedGegner).setCurrentLebenspunkte_(currentLebenspunkte);
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.WRONG_LEBENSPUNKTE_FORMAT);
        }
        refresh(changedItem, changedGegner); 
    }


    private void refresh(
            TreeItem<SharedGegnerTableEntry> changedItem,
            SharedGegnerTableEntry changedGegner) {
        TreeItem<SharedGegnerTableEntry> parent = changedItem.getParent();
        TreeItem<SharedGegnerTableEntry> updatedItem = new TreeItem<SharedGegnerTableEntry>(changedGegner);
        
        parent.getChildren().add(updatedItem);
        parent.getChildren().remove(changedItem);
        
        parent.getChildren().sort(new Comparator<TreeItem<SharedGegnerTableEntry>>(){
            public int compare(TreeItem<SharedGegnerTableEntry> s1, 
                    TreeItem<SharedGegnerTableEntry> s2){
                GegnerEinheit einheit1 = (GegnerEinheit) s1.getValue();
                GegnerEinheit einheit2 = (GegnerEinheit) s2.getValue();
                return einheit1.compareTo(einheit2);
          }});
        
        gegnerTreeTableView_.getSelectionModel().select(updatedItem);
    }
    
    
    
    @FXML
    private void onHelmButtonClick() {
        int schadenModifier = Integer.parseInt(schadenModifierTextField_.getText());
        dealSchaden(Charakter.LOWERBOUND_HELM, schadenModifier);
        schadenModifierTextField_.setText("0");
    }

    
    
    @FXML
    private void onRuestungButtonClick() {
        int schadenModifier = Integer.parseInt(schadenModifierTextField_.getText());
        dealSchaden(Charakter.LOWERBOUND_RUESTUNG, schadenModifier);
        schadenModifierTextField_.setText("0");
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
        if(selectedIndex < 0) return;
        SpielerMitWaffe selectedSpieler = spielerTableView_.getItems().get(selectedIndex);
        updateSchadenAmGegnerTable(selectedSpieler, wuerfelErgebnis, schadenModifier);
    }
    
    
    
    private void updateSchadenAmGegnerTable(SpielerMitWaffe spielerMitWaffe, int wuerfelErgebnis, int schadenModifier) {
        for(TreeItem<SharedGegnerTableEntry> gegnerTyp : gegnerTreeTableView_.getRoot().getChildren()) {
            //Liste wird benötigt, um keine ConcurrentModificationException zu erzeugen
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

    
    
    private void initializeGegnerTreeTableView(ObservableList<GegnerEinheit> allGegner) {
        final TreeItem<SharedGegnerTableEntry> dummyRoot = new TreeItem<>(new GegnerTyp());
        dummyRoot.setExpanded(true);
        gegnerTreeTableView_.setRoot(dummyRoot);
        gegnerTreeTableView_.showRootProperty().set(false);

        if(!allGegner.isEmpty()) {
            addGegnerToTable(allGegner, dummyRoot);
        }
    }

    
    
    private void addGegnerToTable(ObservableList<GegnerEinheit> allGegner,
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
        TreeItem<SharedGegnerTableEntry> selectedItem = getSelectedGegnerItem();
        if(selectedItem == null)    {
            return;
        }
        
        Action deleteGegnerFromKampf = new Action(new Consumer<ActionEvent>() {
            @Override
            public void accept(ActionEvent t) {
                String deletedName = removeGegnerFromTable(false, null);
                if(deletedName != null && !deletedName.isEmpty()) {
                    createNotification(NotificationTexts.textForDeletingGegnerFromKampf(deletedName));
                }
            }
        });
        
        if(selectedItem.getValue() instanceof GegnerEinheit) {
            createConfirmationDialog(NotificationTexts.CONFIRMATION_DELETE_GEGNER_FROM_KAMPF, 
                    NotificationTexts.DELETE_FROM_KAMPF_INFORMATION, NotificationTexts.DELETE_TITLE, 
                    deleteGegnerFromKampf);
        } else {
            deleteGegnerFromKampf.handle(new javafx.event.ActionEvent());
        }
    }
    
    
    
    @FXML
    private void removeFromKampf()  {
        TreeItem<SharedGegnerTableEntry> selectedItem = getSelectedGegnerItem();
        if(selectedItem == null)
            return;
        
        SharedGegnerTableEntry gegner = selectedItem.getValue();
        if(gegner instanceof GegnerEinheit) {
            ((GegnerEinheit) gegner).setCurrentLebenspunkte_(0);
        }
        
        String removedName = removeGegnerFromTable(true, null);
        if(removedName != null && !removedName.isEmpty()) {
            createNotification(NotificationTexts.textForRemovingGegnerFromKampf(removedName));
        }
    }



    private String removeGegnerFromTable(boolean stillLootable, TreeItem<SharedGegnerTableEntry> gegnerToBeRemoved) {
        TreeItem<SharedGegnerTableEntry> selectedItem;
        if(gegnerToBeRemoved == null)   {
            selectedItem = getSelectedGegnerItem();
        }else   {
            selectedItem = gegnerToBeRemoved;
        }
        if(selectedItem == null)    {
            return "";
        }
        boolean hasChildren = !selectedItem.isLeaf(); 
        boolean isGegnerTyp = selectedItem.getValue() instanceof GegnerTyp;
        if(isGegnerTyp && hasChildren)    {
            createNotification(NotificationTexts.GEGNER_TYP_CANNOT_BE_REMOVED);
            return "";
        }
        selectedItem.getParent().getChildren().remove(selectedItem);
        if(!isGegnerTyp) {
            GegnerEinheit selectedGegner = (GegnerEinheit)selectedItem.getValue();
            allActiveGegner_.remove(selectedGegner);
            
            if(stillLootable) {
                //force reload
                allExpYieldingGegner_.remove(selectedGegner);
                allExpYieldingGegner_.add(selectedGegner);
            } else {
                allExpYieldingGegner_.remove(selectedGegner);
            }
        }
        
        return selectedItem.getValue().nameProperty().get();
    }



    private TreeItem<SharedGegnerTableEntry> getSelectedGegnerItem() {
        return gegnerTreeTableView_.getSelectionModel().getSelectedItem();
    }
    
    
    
    private SpielerMitWaffe getSelectedSpielerMitWaffe() {
        return spielerTableView_.getSelectionModel().getSelectedItem();
    }
    
    
    
    @FXML
    private void onSchadenClick() {
        SpielerMitWaffe selectedSpieler = getSelectedSpielerMitWaffe();
        int ruaSchaden = 0;
        if(selectedSpieler != null) {
            Waffen waffe = selectedSpieler.getWaffe();
            if(waffe.isRUA())   {
                ruaSchaden  = getAddedSchaden();
            }
        }
        TreeItem<SharedGegnerTableEntry> selectedItem = getSelectedGegnerItem();
        if(isNullOrGegnerTyp(selectedItem)) {
            return;
        }
        
        GegnerEinheit gegner = (GegnerEinheit) selectedItem.getValue();
        int sumOfDealtSchaden = gegner.getDealtSchaden_() + ruaSchaden;
        gegner.setCurrentLebenspunkte_(gegner.getCurrentLebenspunkte_() - sumOfDealtSchaden);
        gegner.setDealtSchaden_(0);
        addedSchadenTextField_.setText("0");
        
        
        if(gegner.getCurrentLebenspunkte_() <= 0) {
            removeGegnerFromTable(true, null);
            createNotification(NotificationTexts.textForGegnerRemovedDueToLebenspunkte(gegner));
        } else {
            refresh(selectedItem, gegner);
            createNotification(NotificationTexts.textForSchadenDealt(gegner));
        }
    }
    
    
    
    private Integer getAddedSchaden() {
        try {
            return Integer.parseInt(addedSchadenTextField_.getText());
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.textForWrongInputIntoAdditionalDamage);
            e.printStackTrace();
            return 0;
        }
    }
    
    
    
    
    @FXML
    private void onAoESchadenClick() {
        SpielerMitWaffe selectedSpieler = getSelectedSpielerMitWaffe();
        int aoESchaden = 0;
        if(selectedSpieler != null) {
            Waffen waffe = selectedSpieler.getWaffe();
            EffektTyp selectedEffect = waffe.getEffektTyp_(); 
            if(selectedEffect == null)
                return;
            aoESchaden  = getAddedSchaden();
            switch(selectedEffect )   {
                case AOE_SCHADEN_RUE:   {
                    
                    for(TreeItem<SharedGegnerTableEntry> gegnerTyp : gegnerTreeTableView_.getRoot().getChildren()) {
                        //Liste wird benötigt, um keine ConcurrentModificationException zu erzeugen
                        List<TreeItem<SharedGegnerTableEntry>> einheitenList = new ArrayList<TreeItem<SharedGegnerTableEntry>>();
                        for(TreeItem<SharedGegnerTableEntry> gegner : gegnerTyp.getChildren()) {
                            SharedGegnerTableEntry entry = gegner.getValue();
                            GegnerEinheit einheit = (GegnerEinheit) entry;
                            applySchaden(aoESchaden, einheit);
                            if(einheit.getCurrentLebenspunkte_() <= 0) {
                                createNotification(NotificationTexts.textForGegnerRemovedDueToLebenspunkte(einheit));
                            }else   {
                                einheitenList.add(new TreeItem<SharedGegnerTableEntry>(einheit));
                            }
                        }
                        
                        gegnerTyp.getChildren().clear();
                        for(TreeItem<SharedGegnerTableEntry> item : einheitenList) {
                            gegnerTyp.getChildren().add(item);
                        }
                    }
                    break;
                }
                case AOE_SCHADEN_RUA:    {
                    for(TreeItem<SharedGegnerTableEntry> gegnerTyp : gegnerTreeTableView_.getRoot().getChildren()) {
                        //Liste wird benötigt, um keine ConcurrentModificationException zu erzeugen
                        List<TreeItem<SharedGegnerTableEntry>> einheitenList = new ArrayList<TreeItem<SharedGegnerTableEntry>>();
                        for(TreeItem<SharedGegnerTableEntry> gegner : gegnerTyp.getChildren()) {
                            SharedGegnerTableEntry entry = gegner.getValue();
                            GegnerEinheit einheit = (GegnerEinheit) entry;
                            einheit.setCurrentLebenspunkte_(einheit.getCurrentLebenspunkte_()-aoESchaden);
                            if(einheit.getCurrentLebenspunkte_() <= 0) {
                                createNotification(NotificationTexts.textForGegnerRemovedDueToLebenspunkte(einheit));
                            }else   {
                                einheitenList.add(new TreeItem<SharedGegnerTableEntry>(einheit));
                            }
                        }
                        
                        gegnerTyp.getChildren().clear();
                        for(TreeItem<SharedGegnerTableEntry> item : einheitenList) {
                            gegnerTyp.getChildren().add(item);
                        }
                    }
                    break;
                }
                default:    {
                    
                }
            }
        }
        addedSchadenTextField_.setText("0");
    }



    private void applySchaden(int aoESchaden, GegnerEinheit einheit) {
        int modifiedSchaden = einheit.getLebensverlust(aoESchaden, Charakter.LOWERBOUND_RUESTUNG, 0);
        einheit.setCurrentLebenspunkte_(einheit.getCurrentLebenspunkte_()-modifiedSchaden);
    }



    private boolean isNullOrGegnerTyp(TreeItem<SharedGegnerTableEntry> selectedItem) {
        if(selectedItem == null)    {
            return true;
        }
        boolean isGegnerTyp = selectedItem.getValue() instanceof GegnerTyp;
        if(isGegnerTyp)    {
            return true;
        }
        
        return false;
    }
    
    
    
    @FXML
    private void onStaerkeWurfClick() {
        SpielerMitWaffe selectedSpieler = getSelectedSpielerMitWaffe();
        int staerkeMalus = 0;
        if(selectedSpieler != null){
            staerkeMalus = selectedSpieler.getSpieler().getTotalStaerkeMalus(selectedSpieler.getWaffe());
        }
        TreeItem<SharedGegnerTableEntry> item = getSelectedGegnerItem();
        if(isNullOrGegnerTyp(item)) {
            return;
        }
        
        GegnerEinheit gegner = (GegnerEinheit) item.getValue();
        int staerkeProbe = gegner.simulateStaerkeProbe(staerkeMalus);
        
        staerkeWurfTextField_.setText(Integer.toString(staerkeProbe));
    }
    
    
    
    @FXML
    private void onBlockClick() {
        for(TreeItem<SharedGegnerTableEntry> gegnerTyp : gegnerTreeTableView_.getRoot().getChildren()) {
            List<TreeItem<SharedGegnerTableEntry>> refreshList = new ArrayList<TreeItem<SharedGegnerTableEntry>>();
            for(TreeItem<SharedGegnerTableEntry> gegner : gegnerTyp.getChildren()) {
                SharedGegnerTableEntry entry = gegner.getValue();
                GegnerEinheit einheit = (GegnerEinheit) entry;
                
                SpielerMitWaffe selectedSpielerMitWaffe = getSelectedSpielerMitWaffe();
                Waffen waffe = selectedSpielerMitWaffe.getWaffe();
                if(einheit.blockIsSuccessful(selectedSpielerMitWaffe.getSpieler().getTotalGeschickMalus(waffe))) {
                    einheit.setDealtSchaden_(0);
                    gegner.setValue(einheit);
                    refreshList.add(gegner);
                }
            }
            
            for(TreeItem<SharedGegnerTableEntry> item : refreshList)
                refresh(item, item.getValue());
        }
    }
}