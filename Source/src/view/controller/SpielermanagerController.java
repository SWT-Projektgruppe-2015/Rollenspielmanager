package view.controller;

import java.util.List;
import java.util.function.Consumer;

import org.controlsfx.control.action.Action;

import view.NotificationTexts;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Charakter;
import model.Gruppe;
import model.Ruestungseffekt;
import model.Spieler;
import model.Waffen;

public class SpielermanagerController extends CharakterTabController{
    private List<Spieler> spielerList_;

    private Spieler entryForNewSpieler_;
    private Waffen entryForNewWaffe_;
    
    private EventHandler<ActionEvent> selectedEffektTypObserver_;
    
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
    private TextField geschickMalusTextField_;
    @FXML
    private TextField staerkeMalusTextField_;
    @FXML
    private TextField expBoostTextField_;
    
    
    void initialize(List<Spieler> spielerList, GruppenmanagerController gruppenController) {
        spielerList_ = spielerList;
        gruppenManagerController = gruppenController;
        
        spielerNameTextField_.textProperty().addListener(new MaxTextLengthListener(spielerNameTextField_, this, Spieler.MAX_NAME_LENGTH));
        waffenNameTextField_.textProperty().addListener(new MaxTextLengthListener(waffenNameTextField_, this, Waffen.MAX_NAME_LENGTH));
        
        selectedEffektTypObserver_ = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                updateSpieler();
            }
        };
        waffenEffektComboBox_.setOnAction(selectedEffektTypObserver_);
        
        initializeSpielerList();
        initializeWaffenList();
        initializeRuestungseffekts();
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
    
    
    
    protected void initializeRuestungseffekts() {
        initializeEffektTextFields();
    }



    private void initializeEffektTextFields() {
        setIntTextField(0, geschickMalusTextField_);
        setIntTextField(0, staerkeMalusTextField_);
        setIntTextField(0, expBoostTextField_);
    }



    private void setIntTextField(int value, TextField textField) {
        textField.setText(Integer.toString(value));
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

    
    
    private void updateRuestungseffekt(Ruestungseffekt changedEffekt) {
        switch(changedEffekt.getEffektTyp_())    {
            case MALUS_GESCHICK: {
                setIntTextField(changedEffekt.getEffektWert_(), geschickMalusTextField_);
                break;
            }
            case MALUS_STAERKE: {
                setIntTextField(changedEffekt.getEffektWert_(), staerkeMalusTextField_);
                break;
            }
            case EXP_BOOST: {
                setIntTextField(changedEffekt.getEffektWert_(), expBoostTextField_);
                break;
            }
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
        updateRuestungsEffekteDetails(selectedSpieler);
        updateSpielerLists(selectedSpieler, listHasToBeReloaded);
    }
    
    

    private void updateRuestungsEffekteDetails(Spieler selectedSpieler) {
        for (Ruestungseffekt changedEffekt : selectedSpieler.getEffekte()) {
            try {
                switch (changedEffekt.getEffektTyp_()) {
                    case MALUS_GESCHICK: {
                        getIntFromTextField(changedEffekt,
                                geschickMalusTextField_);
                        updateRuestungseffekt(changedEffekt);
                        break;
                    }
                    case MALUS_STAERKE: {
                        getIntFromTextField(changedEffekt,
                                staerkeMalusTextField_);
                        updateRuestungseffekt(changedEffekt);
                        break;
                    }
                    case EXP_BOOST: {
                        getIntFromTextField(changedEffekt,
                                expBoostTextField_);
                        updateRuestungseffekt(changedEffekt);
                        break;
                    }
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                createNotification(NotificationTexts.textForAusruestungUpdateFailed(selectedSpieler));
            }
        }
    }



    private void getIntFromTextField(Ruestungseffekt changedEffekt,
            TextField textField) throws NumberFormatException {
            changedEffekt.setEffektWert_(Integer.parseInt(textField.getText()));
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
            
            showEffectDetails(spieler);
        }
    }



    private void showEffectDetails(Spieler spieler) {
        geschickMalusTextField_.setText("0");
        staerkeMalusTextField_.setText("0");
        expBoostTextField_.setText("0");
        for (Ruestungseffekt selectedEffekt : spieler.getEffekte()) {
            switch (selectedEffekt.getEffektTyp_()) {
                case MALUS_GESCHICK: {
                    setIntTextField(selectedEffekt.getEffektWert_(),
                            geschickMalusTextField_);
                    break;
                }
                case MALUS_STAERKE: {
                    setIntTextField(selectedEffekt.getEffektWert_(),
                            staerkeMalusTextField_);
                    break;
                }
                case EXP_BOOST: {
                    setIntTextField(selectedEffekt.getEffektWert_(),
                            expBoostTextField_);
                    break;
                }
            }
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
        initializeEffektTextFields();
        waffenDamageTextField_.setText("");
    }
    
        
    
    private void showWaffenDetails(Waffen waffen) {
        waffenEffektComboBox_.setOnAction(null);
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
        waffenEffektComboBox_.setOnAction(selectedEffektTypObserver_);
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
}
