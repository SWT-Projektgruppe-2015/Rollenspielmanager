package view.controller;

import java.util.ArrayList;
import java.util.List;

import controller.GruppenSubject;
import controller.Hauptprogramm;
import controller.interfaces.GruppenObserver;
import model.GegnerTyp;
import model.Gruppe;
import model.Spieler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeilnehmerAuswahlController implements GruppenObserver {
    
    
    @FXML
    private ListView<Spieler> spielerNotInKampfListView_; 
    @FXML
    private ListView<Spieler> spielerInKampfListView_; 
    @FXML
    private ListView<GegnerTyp> gegnerInKampfListView_; 
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
    private TableColumn<GegnerTyp, String> nameColumn_;
    
    private List<Spieler> spielerNotInKampfList_;
    private List<GegnerTyp> gegnerNotInKampfList_;
    
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
        nameColumn_.setCellValueFactory(new PropertyValueFactory<GegnerTyp, String>("name_"));

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
        GegnerTyp chosenGegner = gegnerInKampfListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenGegner != null) {
            gegnerNotInKampfList_.add(chosenGegner);
            gegnerNotInKampfList_.sort(null);
            gegnerNotInKampfTableView_.getItems().add(chosenGegner);
            gegnerNotInKampfTableView_.getItems().sort(null);
            gegnerInKampfListView_.getItems().remove(chosenGegner);
        }
    }
    
    
    
    @FXML
    private void addGegnerToKampf() {
        GegnerTyp chosenGegner = gegnerNotInKampfTableView_.getSelectionModel()
                .getSelectedItem();
        if (chosenGegner != null) {
            gegnerNotInKampfList_.remove(chosenGegner);
            gegnerInKampfListView_.getItems().add(chosenGegner);
            gegnerInKampfListView_.getItems().sort(null);
            gegnerNotInKampfTableView_.getItems().remove(chosenGegner);
        }
    }
    
    
    public void setHauptProgramm(Hauptprogramm hauptProgramm) {
        hauptProgramm_ = hauptProgramm;
    }
    
    
    
    @FXML
    private void kampfButton() {
        hauptProgramm_.startKampf(spielerInKampfListView_.getItems(), gegnerInKampfListView_.getItems());
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
