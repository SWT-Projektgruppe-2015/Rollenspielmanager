package view.controller;

import java.util.List;
import java.util.function.Consumer;

import org.controlsfx.control.action.Action;

import view.NotificationTexts;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Charakter;
import model.Faehigkeiten;
import model.Ruestungseffekt;
import model.Spieler;
import model.Waffen;

public class SpielermanagerController extends CharakterTabController{
    private List<Spieler> spielerList_;

    private Spieler entryForNewSpieler_;
    private Waffen entryForNewWaffe_;
    private Ruestungseffekt entryForNewFaehigkeit_;

    private GruppenmanagerController gruppenManagerController;
    
    @FXML
    private ListView<Spieler> spielerListView_;
    @FXML
    private TextField searchSpielerTextField_;

    @FXML
    private TextField spielerNameTextField_;
    @FXML
    private TextField spielerLevelTextField_;
    @FXML
    private Label spielerKreisLabel_;

    @FXML
    private ListView<Waffen> waffenListView_;
    @FXML
    private TextField waffenNameTextField_;
    @FXML
    private TextField waffenDamageTextField_;
    @FXML
    private ComboBox<Waffen.EffektTyp> waffenEffektComboBox_;
    @FXML
    private TextField waffenEffektTextField_;

    @FXML
    private TextField defRTextField_;
    @FXML
    private TextField defHTextField_;
    @FXML
    private TextField defSTextField_;

    @FXML
    private ListView<Ruestungseffekt> ruestungseffektListView_;
    @FXML
    private TextField ruestungsEffektTextField_;
    @FXML
    private ComboBox<Ruestungseffekt> ruestungseffektComboBox_;
    
    
    void initialize(List<Spieler> spielerList, GruppenmanagerController gruppenController) {
        spielerList_ = spielerList;
        gruppenManagerController = gruppenController;
        
        initializeSpielerList();
        initializeWaffenList();
        initializeRuestungseffektList(ruestungseffektListView_);
    }
    
    
    
    private void initializeSpielerList() {
        entryForNewSpieler_ = getEntryForNewSpieler();
        spielerListView_.getItems().setAll(entryForNewSpieler_);
        spielerListView_.getItems().addAll(spielerList_);
        showSpielerDetails(null);

        spielerListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Spieler>() {
                    public void changed(
                            ObservableValue<? extends Spieler> observable,
                            Spieler oldValue, Spieler newValue) {
                        showSpielerDetails(newValue);
                    }
                });
    }
    
    
    
    private void initializeWaffenList() {
        entryForNewWaffe_ = getEntryForNewWaffe();
        waffenEffektComboBox_.getItems().setAll(Waffen.EffektTyp.values());
        
        showWaffenDetails(null);

        waffenListView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Waffen>() {
            public void changed(
                    ObservableValue<? extends Waffen> observable,
                    Waffen oldValue, Waffen newValue) {
                showWaffenDetails(newValue);
            }
        });
    }
      

    
    private void updateSpielerLists(Spieler changedSpieler, boolean listHasToBeReloaded) {
        if(listHasToBeReloaded) {
            spielerListView_.getItems().setAll(entryForNewSpieler_);
            spielerList_.sort(null);
            spielerListView_.getItems().addAll(spielerList_);
            spielerListView_.getSelectionModel().select(changedSpieler);
        }

        gruppenManagerController.updateSpieler(spielerList_, changedSpieler);
    }
    
    
    
    private void updateWaffenList(Waffen changedWaffe) {
        waffenListView_.getItems().remove(changedWaffe);
        waffenListView_.getItems().add(changedWaffe);
        waffenListView_.getItems().sort(null);

        if (changedWaffe == entryForNewWaffe_) {
            entryForNewWaffe_ = getEntryForNewWaffe();
        }
        else {
            waffenListView_.getItems().remove(entryForNewWaffe_);
        }
        
        waffenListView_.getItems().add(0, entryForNewWaffe_);
        waffenListView_.getSelectionModel().select(changedWaffe);
    }

    
    
    private void updateRuestungseffektList(Ruestungseffekt changedEffekt) {
        ruestungseffektListView_.getItems().remove(changedEffekt);
        ruestungseffektListView_.getItems().add(changedEffekt);

        if (changedEffekt == entryForNewFaehigkeit_) {
//            entryForNewFaehigkeit_ = getEntryForNewEffekt();
            ruestungseffektListView_.getItems().add(entryForNewFaehigkeit_);
        }
    }

    

    @FXML
    private void searchSpieler() {
        search(searchSpielerTextField_, spielerListView_, spielerList_,
                entryForNewSpieler_);
    }
  

            
    @FXML
    private void deleteSpieler() {
        Spieler spielerToDelete = getSelectedSpieler();
        if (spielerToDelete != null) {
            Action deleteSpieler = new Action(new Consumer<ActionEvent>() {
                @Override
                public void accept(ActionEvent t) {
                    spielerListView_.getItems().remove(spielerToDelete);
                    spielerList_.remove(spielerToDelete);
                    spielerToDelete.deleteFromDB();
                   
                    gruppenManagerController.updateSpieler(spielerList_, spielerToDelete);
                    
                    createNotification(NotificationTexts.textForCharakterDeletion(spielerToDelete));
                }
            });
            
            createReallyDeleteDialog(NotificationTexts.confirmationTextCharakterDeletion(spielerToDelete), deleteSpieler);
        }
    }

    
    
    @FXML
    private void increaseLevel() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.increaseLevel();
        createNotification(NotificationTexts.textForLevelChange(selectedSpieler));

        spielerLevelTextField_.setText(Integer.toString(selectedSpieler
                .getLevel_()));
        spielerKreisLabel_
                .setText(Integer.toString(selectedSpieler.getKreis_()));
        
        updateSpieler();
    }

    
    
    @FXML
    private void decreaseLevel() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.decreaseLevel();
        createNotification(NotificationTexts.textForLevelChange(selectedSpieler));

        spielerLevelTextField_.setText(Integer.toString(selectedSpieler
                .getLevel_()));
        spielerKreisLabel_
                .setText(Integer.toString(selectedSpieler.getKreis_()));
        
        updateSpieler();
    }

    
    
    @FXML
    private void updateSpieler() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;
        
        if (spielerNameTextField_.getText().isEmpty()) {
            createNotification(NotificationTexts.NAME_IS_EMPTY);
            return;
        }
        
        boolean listHasToBeReloaded = updateSpielerName(selectedSpieler);
        boolean defIsUpdated = updateSpielerDef(selectedSpieler);
        
        if (selectedSpieler == entryForNewSpieler_) {
            listHasToBeReloaded = true;
            
            spielerList_.add(selectedSpieler);
            selectedSpieler.addToDB();
            entryForNewSpieler_ = getEntryForNewSpieler();
            createNotification(NotificationTexts.textForNewCharakter(selectedSpieler));
        } else if (defIsUpdated) {
            createNotification(NotificationTexts.textForSpielerUpdate(selectedSpieler));
        }

        //muss nach Speicherung des Spielers gemacht werden, damit Ausruestung in DB ist.
        updateWaffenDetails(selectedSpieler);
//        updateFaehigkeitName(selectedSpieler);  
        
        updateSpielerLists(selectedSpieler, listHasToBeReloaded);
    }
    
    

    private boolean updateSpielerName(Spieler selectedSpieler) {
        String newName = spielerNameTextField_.getText();
        String oldName = selectedSpieler.getName_();
        if(!newName.isEmpty() && !newName.equals(oldName)) {
            selectedSpieler.setName_(newName);
            return true;
        }
        return false;
    }

    
    
    private boolean updateSpielerDef(Spieler selectedSpieler) {
        try {
            int newDefR = Integer.parseInt(defRTextField_.getText());
            int newDefH = Integer.parseInt(defHTextField_.getText());
            int newDefS = Integer.parseInt(defSTextField_.getText());

            if (Charakter.ausruestungIsValid(newDefR, newDefH, newDefS)) {
                selectedSpieler.setDefR(newDefR);
                selectedSpieler.setDefH(newDefH);
                selectedSpieler.setDefS(newDefS);
                return true;
            } else {
                createNotification(NotificationTexts.textForAusruestungUpdateFailed(selectedSpieler));
            }
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.textForAusruestungUpdateFailed(selectedSpieler));
        }
        return false;
    }

    
    
    private void updateWaffenDetails(Spieler selectedSpieler) {
        Waffen selectedWaffe = getSelectedWaffe();
        if (selectedWaffe == null)
            return;

        boolean waffeChanged = false;
        waffeChanged = updateWaffenName(selectedWaffe);
        waffeChanged = updateWaffenDamage(selectedWaffe) || waffeChanged;
        waffeChanged = updateWaffenEffektTyp(selectedWaffe) || waffeChanged;
        waffeChanged = updateWaffenEffektWert(selectedWaffe)  || waffeChanged;

        if (waffeChanged) {
            if (selectedWaffe == entryForNewWaffe_) {
                selectedSpieler.addWaffe(selectedWaffe);
            }

            updateWaffenList(selectedWaffe);
        }
    }

    
    
    private boolean updateWaffenEffektWert(Waffen selectedWaffe) {
        try {
            int newEffektWert = Integer.parseInt(waffenEffektTextField_.getText());

            if (newEffektWert >= 0 && newEffektWert != selectedWaffe.getEffektWert_() && selectedWaffe.getEffektTyp_() != null) {
                selectedWaffe.setEffektWert_(newEffektWert);
                return true;
            } else if(newEffektWert < 0) {
                createNotification(NotificationTexts.textForWaffenUpdateFailed(selectedWaffe));
            }
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.textForWaffenUpdateFailed(selectedWaffe));
        }
        return false;
    }



    private boolean updateWaffenEffektTyp(Waffen selectedWaffe) {
        Waffen.EffektTyp effektTyp = waffenEffektComboBox_.getValue();
        if(effektTyp != null && !effektTyp.equals(selectedWaffe.getEffektTyp_())) {
            selectedWaffe.setEffektTyp_(effektTyp);
            return true;
        }
        
        return false;
    }



    private boolean updateWaffenName(Waffen selectedWaffe) {
        String newName = waffenNameTextField_.getText();
        if (!newName.equals(selectedWaffe.getWaffenName_())) {
            selectedWaffe.setWaffenName_(newName);
            return true;
        }

        return false;
    }

    
    
    private boolean updateWaffenDamage(Waffen selectedWaffe) {
        try {
            int newDamage = Integer.parseInt(waffenDamageTextField_.getText());

            if (newDamage >= 0
                    && newDamage != selectedWaffe.getWaffenSchaden_()) {
                selectedWaffe.setWaffenSchaden_(newDamage);
                return true;
            } else if(newDamage < 0) {
                createNotification(NotificationTexts.textForWaffenUpdateFailed(selectedWaffe));
            }
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.textForWaffenUpdateFailed(selectedWaffe));
        }
        return false;
    }

    
    

//    private void updateFaehigkeitName(Spieler selectedSpieler) {
//        Faehigkeiten selectedFaehigkeit = getSelectedRuestungsEffekt();
//        if (selectedFaehigkeit == null)
//            return;
//
//        String newName = faehigkeitenNameTextField_.getText();
//        
//        if(!newName.equals(selectedFaehigkeit.getName_())) {
//            selectedFaehigkeit.setName_(newName);
//    
//            if (selectedFaehigkeit == entryForNewFaehigkeit_) {
//                selectedSpieler.addFaehigkeit(selectedFaehigkeit);
//            }
//    
//            updateRuestungseffektList(selectedFaehigkeit);
//        }
//    }
    
    
    
    @FXML
    private void deleteWaffe() {
        Waffen selectedWaffe = getSelectedWaffe();
        if (selectedWaffe == null || selectedWaffe == entryForNewWaffe_)
            return;

        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        Action deleteWaffe = new Action(new Consumer<ActionEvent>() {
            @Override
            public void accept(ActionEvent t) {
                selectedSpieler.deleteWaffe(selectedWaffe);
                waffenListView_.getItems().remove(selectedWaffe);
                createNotification(NotificationTexts.textForWaffenDeletion(selectedWaffe));
            }
        });
        
        createReallyDeleteDialog(NotificationTexts.confirmationTextWaffenDeletion(selectedWaffe), deleteWaffe);
    }

    
    
    

    
    
//    @FXML
//    private void deleteFaehigkeit() {
//        Faehigkeiten selectedFaehigkeit = getSelectedRuestungsEffekt();
//        if (selectedFaehigkeit == null
//                || selectedFaehigkeit == entryForNewFaehigkeit_)
//            return;
//
//        Spieler selectedSpieler = getSelectedSpieler();
//        if (selectedSpieler == null)
//            return;
//
//        selectedSpieler.deleteFaehigkeit(selectedFaehigkeit);
//        ruestungseffektListView_.getItems().remove(selectedFaehigkeit);
//    }
   
        
    
    private void showSpielerDetails(Spieler spieler) {
        if (spieler == null) {
            showEmptySpielerDetails();
        }
        else {
            spielerNameTextField_.setText(spieler.getName_());
            spielerLevelTextField_.setText(Integer.toString(spieler.getLevel_()));
            spielerKreisLabel_.setText(Integer.toString(spieler.getKreis_()));

            waffenListView_.getItems().setAll(entryForNewWaffe_);
            waffenListView_.getItems().addAll(spieler.getWaffen());
            waffenListView_.getSelectionModel().select(0);

            defRTextField_.setText(Integer.toString(spieler.getDefR()));
            defHTextField_.setText(Integer.toString(spieler.getDefH()));
            defSTextField_.setText(Integer.toString(spieler.getDefS()));

            ruestungseffektListView_.getItems().setAll(entryForNewFaehigkeit_);
//            ruestungseffektListView_.getItems().addAll(spieler.getFaehigkeiten());
            ruestungseffektListView_.getSelectionModel().select(0);
        }
    }
    
    
    
    private void showEmptySpielerDetails() {
        spielerNameTextField_.setText("");
        spielerLevelTextField_.setText("");
        spielerKreisLabel_.setText("");

        waffenListView_.getItems().clear();

        defRTextField_.setText("");
        defHTextField_.setText("");
        defSTextField_.setText("");
        waffenDamageTextField_.setText("");

        ruestungseffektListView_.getItems().clear();
    }
    
        
    
    private void showWaffenDetails(Waffen waffen) {
        if (waffen == null) {
            showEmptyWaffenDetails();
        }
        else {
            waffenNameTextField_.setText(waffen.getWaffenName_());
            waffenDamageTextField_.setText(Integer.toString(waffen
                    .getWaffenSchaden_()));
            waffenEffektComboBox_.getSelectionModel().select(waffen.getEffektTyp_());
            waffenEffektTextField_.setText(Integer.toString(waffen.getEffektWert_()));
        }
    }

    
    
    private void showEmptyWaffenDetails() {
        waffenNameTextField_.setText("");
        waffenDamageTextField_.setText("");
        waffenEffektComboBox_.getSelectionModel().select(null);
        waffenEffektTextField_.setText("");
    }
    
    
    
    private Spieler getEntryForNewSpieler() {
        Spieler entryForNewSpieler = new Spieler();
        entryForNewSpieler.setName_("Neuer Spieler");

        return entryForNewSpieler;
    }
    
    
    
    private Waffen getEntryForNewWaffe() {
        Waffen entryForNewWaffe = new Waffen();
        entryForNewWaffe.setWaffenName_("Neue Waffe");

        return entryForNewWaffe;
    }

    
    
    private Spieler getSelectedSpieler() {
        return getSelected(spielerListView_);
    }
    
    
    
    private Waffen getSelectedWaffe() {
        return getSelected(waffenListView_);
    }

    
    
    private Ruestungseffekt getSelectedRuestungseffekt() {
        return getSelected(ruestungseffektListView_);
    }

    
    
    @Override
    protected TextField getRuestungseffektNameTextField() {
//        return ruestungseffektTextField_;
        return null;
    }



    @Override
    protected void createEntryForNewFaehigkeit() {
//        entryForNewFaehigkeit_ = getEntryForNewEffekt();
    }
    
}
