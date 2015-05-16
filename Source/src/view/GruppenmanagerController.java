package view;

import java.util.Collection;
import java.util.List;

import controller.GruppenSubject;
import controller.interfaces.GruppenObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Gruppe;
import model.Spieler;

public class GruppenmanagerController implements GruppenObserver{
    private List<Spieler> spielerList_;
    private List<Gruppe> gruppenList_;
    
    private GruppenSubject gruppenSubject_;
    
    @FXML
    private TextField newGruppenNameTextField_;
    @FXML
    private ComboBox<Gruppe> gruppenComboBox_;
    @FXML
    private ListView<Spieler> spielerNotInGruppeListView_; 
    @FXML
    private ListView<Spieler> spielerInGruppeListView_; 
    
    
    
    public void setGruppenSubject_(GruppenSubject gruppenSubject_) {
        this.gruppenSubject_ = gruppenSubject_;
        gruppenComboBox_.getSelectionModel().select(gruppenSubject_.getSelectedGruppe());
    }


    
    void initialize(List<Spieler> spielerList) {
        spielerList_ = spielerList;
        gruppenList_ = Gruppe.getAll();
        spielerNotInGruppeListView_.getItems().setAll(spielerList_);
        gruppenComboBox_.getItems().setAll(gruppenList_);
        gruppenComboBox_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Gruppe>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Gruppe> observable,
                            Gruppe oldValue, Gruppe newValue) {
                        showGruppenName(newValue);
                        updateGruppenListViews(newValue);
                    }
                });
    }
    
    
    
    void updateAllSpieler(List<Spieler> spielerList, Spieler changedSpieler) {
        spielerList_ = spielerList;
        spielerNotInGruppeListView_.getItems().setAll(spielerList_);
        spielerNotInGruppeListView_.getSelectionModel().select(changedSpieler);
    }
    
    
    
    void updateGruppenListViews(Gruppe selectedGruppe) {
        if (selectedGruppe == null) {
            spielerInGruppeListView_.getItems().clear();
            spielerNotInGruppeListView_.getItems().setAll(spielerList_);
            return;
        }

        Collection<Spieler> spielerInGruppe = selectedGruppe.getAllSpieler();
        spielerInGruppeListView_.getItems().setAll(spielerInGruppe);

        spielerNotInGruppeListView_.getItems().clear();
        for (Spieler spieler : spielerList_) {
            boolean isInGruppe = false;
            for (Spieler gruppenSpieler : spielerInGruppe) {
                if(gruppenSpieler.getID_() == spieler.getID_())
                    isInGruppe = true;
            }
            
            if (!isInGruppe) {
                spielerNotInGruppeListView_.getItems().add(spieler);
            }
        }
    }
    

    
    Gruppe getSelectedGruppe() {
        int selectedIndex = gruppenComboBox_.getSelectionModel()
                .getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        Gruppe selectedGruppe = gruppenComboBox_.getItems().get(selectedIndex);

        return selectedGruppe;
    }

    
    
    private void showGruppenName(Gruppe gruppe) {
        if (gruppe == null) {
            newGruppenNameTextField_.setText("");
        }
        else {
            newGruppenNameTextField_.setText(gruppe.getName());
        }
    }

        
    
    @FXML
    private void createGruppe() {
        Gruppe newGruppe = new Gruppe();
        newGruppe.setName_(newGruppenNameTextField_.getText());
        newGruppe.addToDB();
        gruppenList_.add(newGruppe);
        gruppenComboBox_.getItems().add(newGruppe);
        gruppenComboBox_.getSelectionModel().select(newGruppe);
        gruppenSubject_.setGruppen(gruppenList_);
    }
    
    
    
    @FXML
    private void updateGruppe() {
        Gruppe selectedGruppe = getSelectedGruppe();
        if (selectedGruppe == null)
            return;

        selectedGruppe.setName_(newGruppenNameTextField_.getText());
        gruppenComboBox_.getItems().setAll(gruppenList_);
        gruppenComboBox_.getSelectionModel().select(selectedGruppe);
        gruppenSubject_.setGruppen(gruppenList_);
    }
    
    
    
    @FXML
    private void deleteGruppe() {
        Gruppe gruppeToDelete = getSelectedGruppe();
        if (gruppeToDelete != null) {
            gruppeToDelete.deleteFromDB();
            gruppenComboBox_.getItems().remove(gruppeToDelete);
            gruppenComboBox_.getItems();
            gruppenComboBox_.setValue(null);
            gruppenList_.remove(gruppeToDelete);
            gruppenSubject_.setGruppen(gruppenList_);
            
        }
    }
    
         
    
    @FXML
    private void addSpielerToGruppe() {
        Gruppe selectedGruppe = getSelectedGruppe();
        if (selectedGruppe == null)
            return;

        Spieler chosenSpieler = spielerNotInGruppeListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            spielerInGruppeListView_.getItems().add(chosenSpieler);
            spielerNotInGruppeListView_.getItems().remove(chosenSpieler);
            selectedGruppe.addSpieler(chosenSpieler);
            gruppenSubject_.setGruppen(gruppenList_);
        }
    }

    
    
    @FXML
    private void removeSpielerFromGruppe() {
        Spieler chosenSpieler = spielerInGruppeListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            spielerNotInGruppeListView_.getItems().add(chosenSpieler);
            spielerInGruppeListView_.getItems().remove(chosenSpieler);

            Gruppe selectedGruppe = getSelectedGruppe();
            selectedGruppe.removePlayer(chosenSpieler);
            gruppenSubject_.setGruppen(gruppenList_);
        }
    }



    @Override
    public void update() {
        gruppenComboBox_.getItems().setAll(gruppenSubject_.getGruppen());
        gruppenComboBox_.getSelectionModel().select(gruppenSubject_.getSelectedGruppe());
        
    }
}
