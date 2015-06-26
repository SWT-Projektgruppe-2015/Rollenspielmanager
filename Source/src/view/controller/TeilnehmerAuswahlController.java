package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.GegnerEinheitImKampf;
import controller.GruppenSubject;
import controller.interfaces.GruppenObserver;
import model.GegnerEinheit;
import model.GegnerTyp;
import model.Gruppe;
import model.Spieler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TeilnehmerAuswahlController implements GruppenObserver {
    @FXML
    private CheckBox kreis1CheckBox_;
    @FXML
    private CheckBox kreis2CheckBox_;
    @FXML
    private CheckBox kreis3CheckBox_;
    @FXML
    private CheckBox kreis4CheckBox_;
    
    @FXML
    private ListView<Spieler> spielerNotInKampfListView_; 
    @FXML
    private ListView<Spieler> spielerInKampfListView_;
    @FXML
    private TableView<GegnerEinheitImKampf> gegnerInKampfTableView_;
    @FXML
    private TableView<GegnerTyp> gegnerNotInKampfTableView_; 
    @FXML
    private TextField searchSpielerTextField_;
    @FXML
    private TextField searchGegnerTextField_;
    @FXML
    private ComboBox<Gruppe> gruppenComboBox_;
    @FXML
    private TableColumn<GegnerTyp, Integer> kreisColumn_;
    @FXML
    private TableColumn<GegnerTyp, Integer> levelColumn_;
    @FXML
    private TableColumn<GegnerTyp, String> nameNotInKampfColumn_;
    @FXML
    private TableColumn<GegnerEinheitImKampf, String> numberOfColumn_;
    @FXML
    private TableColumn<GegnerEinheitImKampf, String> nameInKampfColumn_;
    
    private List<Spieler> spielerNotInKampfList_;
    private List<GegnerTyp> gegnerNotInKampfList_;
    private List<GegnerEinheitImKampf> gegnerEinheitImKampfList_;
    
    private Hauptprogramm hauptProgramm_;
    
    private GruppenSubject gruppenSubject_;
    
    
    
    public void setGruppenSubject_(GruppenSubject gruppenSubject_) {
        this.gruppenSubject_ = gruppenSubject_;
        gruppenComboBox_.getSelectionModel().select(gruppenSubject_.getSelectedGruppe());
    }



    @FXML
    void initialize() {        
        spielerNotInKampfList_ = new ArrayList<Spieler>();
        spielerNotInKampfList_.addAll(Spieler.getAll());
        spielerNotInKampfListView_.getItems().setAll(spielerNotInKampfList_);

        gegnerNotInKampfList_ = new ArrayList<GegnerTyp>();
        gegnerNotInKampfList_.addAll(GegnerTyp.getAll());
        gegnerNotInKampfTableView_.getItems().setAll(gegnerNotInKampfList_);
        gegnerEinheitImKampfList_ = new ArrayList<GegnerEinheitImKampf>();
       
        gruppenComboBox_.getItems().setAll(Gruppe.getAll());
        gruppenComboBox_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Gruppe>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Gruppe> observable,
                            Gruppe oldValue, Gruppe newValue) {
                        updateSpielerList(newValue);
                    }

                });
       
        kreisColumn_.setCellValueFactory(new PropertyValueFactory<GegnerTyp, Integer>("kreis_"));
        levelColumn_.setCellValueFactory(new PropertyValueFactory<GegnerTyp, Integer> ("level_"));
        nameNotInKampfColumn_.setCellValueFactory(new PropertyValueFactory<GegnerTyp, String>("name_"));
        
        nameInKampfColumn_.setCellValueFactory(new PropertyValueFactory<GegnerEinheitImKampf, String>("gegnerTypName_"));
        numberOfColumn_.setCellFactory(TextFieldTableCell.forTableColumn());
        numberOfColumn_.setCellValueFactory(new PropertyValueFactory<GegnerEinheitImKampf, String>("countOf_"));
        numberOfColumn_.setOnEditCommit(
                new EventHandler<CellEditEvent<GegnerEinheitImKampf, String>>() {
                    @Override
                    public void handle(CellEditEvent<GegnerEinheitImKampf, String> t) {
                        GegnerEinheitImKampf changedEinheit =
                        ((GegnerEinheitImKampf) t.getTableView().getItems().get(
                            t.getTablePosition().getRow()));
                        changeNumberOfEinheiten(changedEinheit, t.getNewValue());
                    }
                }
            );
    }
    
    @FXML
    private void onKreisFilterClick() {
        List<Integer> kreiseToShow = new ArrayList<Integer>();
        if (kreis1CheckBox_.isSelected()) {
            kreiseToShow.add(1);
        }
        if (kreis2CheckBox_.isSelected()) {
            kreiseToShow.add(2);
        }
        if (kreis3CheckBox_.isSelected()) {
            kreiseToShow.add(3);
        }
        if (kreis4CheckBox_.isSelected()) {
            kreiseToShow.add(4);
        }

        gegnerNotInKampfTableView_.getItems().clear();        
        for (GegnerTyp item : gegnerNotInKampfList_) {
            if (kreiseToShow.contains(item.getKreis_())) {
                gegnerNotInKampfTableView_.getItems().add(item);
            }
        }
    }
    
    private void changeNumberOfEinheiten(GegnerEinheitImKampf changedEinheit, String newValue) {
        int newValueInt = 0;
        try {
            newValueInt = Integer.parseInt(newValue);
        }
        catch(NumberFormatException e) {
            newValueInt = changedEinheit.getCountAsInteger();
        }
        
        for(GegnerEinheitImKampf einheiten : this.gegnerEinheitImKampfList_) {
            if(einheiten.getGegnerTypName_().equals(changedEinheit.getGegnerTypName_())) {
                einheiten.setCountOf_(newValueInt);
            }            
        }
        
        gegnerInKampfTableView_.getItems().setAll(gegnerEinheitImKampfList_);
        gegnerInKampfTableView_.getItems().sort(null);
    }
    
    
    
    private void updateSpielerList(Gruppe newValue) {
        if(newValue == null) {
            spielerInKampfListView_.getItems().clear();
            spielerNotInKampfList_.clear();
            spielerNotInKampfList_.addAll(Spieler.getAll());
            spielerNotInKampfListView_.getItems().setAll(spielerNotInKampfList_);
        }
        else {
            spielerInKampfListView_.getItems().setAll(newValue.getOrderedMemberList());
            spielerNotInKampfListView_.getItems().clear();
            spielerNotInKampfList_.clear();
        }
             
    }
    
    
    @FXML
    private void searchSpieler() {
        String search = searchSpielerTextField_.getText().toLowerCase();
        spielerNotInKampfListView_.getItems().clear();
        
        for (Spieler item : spielerNotInKampfList_) {
            if (item.getName_().toLowerCase().contains(search)) {
                spielerNotInKampfListView_.getItems().add(item);
            }
        }
    }
    
    @FXML
    private void searchGegner() {
        String search = searchGegnerTextField_.getText().toLowerCase();
        gegnerNotInKampfTableView_.getItems().clear();
        
        for (GegnerTyp item : gegnerNotInKampfList_) {
            if ((item.getName_().toLowerCase().contains(search)) || (Integer.toString(item.getKreis_()).contains(search)) ||
                    (Integer.toString(item.getLevel_()).contains(search))) {
                gegnerNotInKampfTableView_.getItems().add(item);
            }
        }
        
    }
    
    
    
    @FXML
    private void removeSpielerFromKampf() {
        Spieler chosenSpieler = spielerInKampfListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            spielerNotInKampfList_.add(chosenSpieler);
            spielerNotInKampfList_.sort(null);
            spielerNotInKampfListView_.getItems().add(chosenSpieler);
            spielerNotInKampfListView_.getItems().sort(null);
            spielerInKampfListView_.getItems().remove(chosenSpieler);

        }
    }
    
    
    
    @FXML
    private void addSpielerToKampf() {
        Spieler chosenSpieler = spielerNotInKampfListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            spielerNotInKampfList_.remove(chosenSpieler);
            spielerInKampfListView_.getItems().add(chosenSpieler);
            spielerInKampfListView_.getItems().sort(null);
            spielerNotInKampfListView_.getItems().remove(chosenSpieler);
        }
    }
    
    
    
    @FXML
    private void removeGegnerFromKampf() {
        GegnerTyp chosenGegner = gegnerInKampfTableView_.getSelectionModel()
                .getSelectedItem().getGegnerTyp();
        if (chosenGegner != null) {
            removeGegnerEinheit(chosenGegner);
        }
    }
    
    
    
    @FXML
    private void addGegnerToKampf() {
        GegnerTyp chosenGegner = gegnerNotInKampfTableView_.getSelectionModel()
                .getSelectedItem();
        if (chosenGegner != null) {
            addGegnerEinheit(chosenGegner);
        }
    }
    
    
    
    private void addGegnerEinheit(GegnerTyp gegnerTyp) {
        boolean alreadyInList = false;
        for(GegnerEinheitImKampf einheiten : gegnerEinheitImKampfList_) {
            if(einheiten.getGegnerTyp().getName_().equals(gegnerTyp.getName_())) {
                einheiten.setCountOf_(einheiten.getCountAsInteger() + 1);
                alreadyInList = true;
            }
        }
        
        if(!alreadyInList) {
            gegnerEinheitImKampfList_.add(new GegnerEinheitImKampf(gegnerTyp));
            gegnerInKampfTableView_.getItems().add(new GegnerEinheitImKampf(gegnerTyp));
        }
       
        gegnerInKampfTableView_.getItems().setAll(gegnerEinheitImKampfList_);
        gegnerInKampfTableView_.getItems().sort(null);
    }
    
    
    
    private void removeGegnerEinheit(GegnerTyp gegnerTyp) {
        for(GegnerEinheitImKampf einheiten : gegnerEinheitImKampfList_) {
            if(einheiten.getGegnerTyp().getName_().equals(gegnerTyp.getName_())) {
                if(einheiten.getCountAsInteger() == 1) {
                    gegnerEinheitImKampfList_.remove(einheiten);
                } else {
                    einheiten.setCountOf_(einheiten.getCountAsInteger() - 1);
                }
                break;
            }
        }
        
        gegnerInKampfTableView_.getItems().setAll(gegnerEinheitImKampfList_);
        gegnerInKampfTableView_.getItems().sort(null);
    }
    
    
    public void setHauptProgramm(Hauptprogramm hauptProgramm) {
        hauptProgramm_ = hauptProgramm;
    }
    
    
    
    @FXML
    private void kampfButton() {
        List<GegnerEinheit> gegnerList = new ArrayList<GegnerEinheit>(); 
        for(GegnerEinheitImKampf einheiten : gegnerInKampfTableView_.getItems()) {
            gegnerList.addAll(GegnerEinheit.createEinheiten(
                    einheiten.getGegnerTyp(), einheiten.getCountAsInteger()));
        }
        hauptProgramm_.startKampf(spielerInKampfListView_.getItems(), FXCollections.observableList(gegnerList));
    }



    @Override
    public void updateGruppenList() {
        gruppenComboBox_.getItems().setAll(gruppenSubject_.getGruppen());
        gruppenComboBox_.getSelectionModel().select(gruppenSubject_.getSelectedGruppe());
        updateSpielerList(gruppenComboBox_.getSelectionModel().getSelectedItem());
    }



    @Override
    public void updateSelectedGruppe() {
        if(gruppenSubject_.getSelectedGruppe() == null)
            gruppenComboBox_.setValue(null);
        else
            gruppenComboBox_.getSelectionModel().select(gruppenSubject_.getSelectedGruppe());        
    }   

}
