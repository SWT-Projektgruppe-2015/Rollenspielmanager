package view.controller;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.AbstractAction;

import org.controlsfx.control.action.Action;

import view.NotificationTexts;
import controller.GruppenSubject;
import controller.interfaces.GruppenObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Gruppe;
import model.Spieler;

public class GruppenmanagerController extends NotificationController implements GruppenObserver{
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
    
    
    
    void updateSpieler(List<Spieler> spielerList, Spieler changedSpieler) {
        spielerList_ = spielerList;

        Gruppe selected = gruppenComboBox_.getSelectionModel().getSelectedItem();
        
        if(selected == null) {
            spielerNotInGruppeListView_.getItems().setAll(spielerList_);
            return;
        }
        
        gruppenList_ = Gruppe.getAll();
        gruppenSubject_.setGruppen(gruppenList_);
        
        for(Gruppe gruppe : gruppenList_) {
            if(gruppe.getID_() == selected.getID_()) {
                updateGruppenListViews(gruppe);
                break;
            }
        }
    }
    
    
    
    void updateGruppenListViews(Gruppe selectedGruppe) {
        if (selectedGruppe == null) {
            spielerInGruppeListView_.getItems().clear();
            spielerNotInGruppeListView_.getItems().setAll(spielerList_);
            return;
        }

        Collection<Spieler> spielerInGruppe = selectedGruppe.getOrderedMemberList();
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
        gruppenList_.sort(null);
        createNotification(NotificationTexts.textForNewGruppe(newGruppe));
        
        gruppenComboBox_.getItems().add(newGruppe);
        gruppenComboBox_.getItems().sort(null);
        gruppenComboBox_.getSelectionModel().select(newGruppe);
        gruppenSubject_.setGruppen(gruppenList_);
    }
    
    
    
    @FXML
    private void updateGruppe() {
        Gruppe selectedGruppe = getSelectedGruppe();
        if (selectedGruppe == null)
            return;

        String oldName = selectedGruppe.getName_();
        String newName = newGruppenNameTextField_.getText();
        if(!oldName.equals(newName)) {
            selectedGruppe.setName_(newName);
            gruppenList_.sort(null);
            gruppenComboBox_.getItems().setAll(gruppenList_);
            gruppenComboBox_.getSelectionModel().select(selectedGruppe);
            gruppenSubject_.setGruppen(gruppenList_);
            createNotification(NotificationTexts.textForGruppenRenaming(oldName, newName));
        }
    }
    
    
    
    @FXML
    private void deleteGruppe() {
        Gruppe gruppeToDelete = getSelectedGruppe();
        if (gruppeToDelete != null) {
            Action deleteGruppe = new Action(new Consumer<ActionEvent>() {
                @Override
                public void accept(ActionEvent t) {
                    gruppeToDelete.deleteFromDB();
                    gruppenComboBox_.getItems().remove(gruppeToDelete);
                    gruppenComboBox_.getItems();
                    gruppenComboBox_.setValue(null);
                    gruppenList_.remove(gruppeToDelete);
                    gruppenSubject_.setGruppen(gruppenList_);
                    createNotification(NotificationTexts.textForGruppenDeletion(gruppeToDelete));
                }
            });
            
            createConfirmMessage(NotificationTexts.confirmationTextGruppenDeletion(gruppeToDelete), NotificationTexts.REALLY_DELETE, deleteGruppe);
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
            spielerInGruppeListView_.getItems().sort(null);
            spielerNotInGruppeListView_.getItems().remove(chosenSpieler);
            selectedGruppe.addSpieler(chosenSpieler);
            gruppenSubject_.setGruppen(gruppenList_);
            createNotification(NotificationTexts.textForAddingSpielerToGruppe(chosenSpieler, selectedGruppe));
        }
    }

    
    
    @FXML
    private void removeSpielerFromGruppe() {
        Spieler chosenSpieler = spielerInGruppeListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            spielerNotInGruppeListView_.getItems().add(chosenSpieler);
            spielerNotInGruppeListView_.getItems().sort(null);
            spielerInGruppeListView_.getItems().remove(chosenSpieler);

            Gruppe selectedGruppe = getSelectedGruppe();
            selectedGruppe.removePlayer(chosenSpieler);
            gruppenSubject_.setGruppen(gruppenList_);
            createNotification(NotificationTexts.textForRemovingSpielerFromGruppe(chosenSpieler, selectedGruppe));
        }
    }
    


    @Override
    public void updateGruppenList() {       
    }



    @Override
    public void updateSelectedGruppe() {
        gruppenComboBox_.getSelectionModel().select(gruppenSubject_.getSelectedGruppe());        
    }
}
