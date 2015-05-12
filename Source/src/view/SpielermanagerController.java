package view;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Charakter;
import model.Faehigkeiten;
import model.Spieler;
import model.Waffen;

public class SpielermanagerController extends CharakterTabController{
    private List<Spieler> spielerList_;

    private Spieler entryForNewSpieler_;
    private Waffen entryForNewWaffe_;
    private Faehigkeiten entryForNewFaehigkeit_;

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
    private TextField defRTextField_;
    @FXML
    private TextField defHTextField_;
    @FXML
    private TextField defSTextField_;

    @FXML
    private ListView<Faehigkeiten> faehigkeitenListView_;
    @FXML
    private TextField faehigkeitenNameTextField_;
    
    void initialize(List<Spieler> spielerList, GruppenmanagerController gruppenController) {
        spielerList_ = spielerList;
        gruppenManagerController = gruppenController;
        
        initializeSpielerList();
        initializeWaffenList();
        initializeFaehigkeitenList(faehigkeitenListView_);
    }
    
    
    
    private void initializeSpielerList() {
        spielerListView_.getItems().setAll(spielerList_);
        entryForNewSpieler_ = getEntryForNewSpieler();
        spielerListView_.getItems().add(entryForNewSpieler_);

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
      

    
    private void updateSpielerLists(Spieler changedSpieler) {
        if (changedSpieler == entryForNewSpieler_) {
            spielerList_.add(changedSpieler);
            changedSpieler.addToDB();
            entryForNewSpieler_ = getEntryForNewSpieler();
        }

        spielerListView_.getItems().setAll(spielerList_);
        spielerListView_.getItems().add(entryForNewSpieler_);
        spielerListView_.getSelectionModel().select(changedSpieler);

        gruppenManagerController.updateAllSpieler(spielerList_, changedSpieler);
    }
    
    
    
    private void updateWaffenList(Waffen changedWaffe) {
        waffenListView_.getItems().remove(changedWaffe);
        waffenListView_.getItems().add(changedWaffe);

        if (changedWaffe == entryForNewWaffe_) {
            entryForNewWaffe_ = getEntryForNewWaffe();
            waffenListView_.getItems().add(entryForNewWaffe_);
        }
    }

    
    
    private void updateFaehigkeitenList(Faehigkeiten changedFaehigkeit) {
        faehigkeitenListView_.getItems().remove(changedFaehigkeit);
        faehigkeitenListView_.getItems().add(changedFaehigkeit);

        if (changedFaehigkeit == entryForNewFaehigkeit_) {
            entryForNewFaehigkeit_ = getEntryForNewFaehigkeit();
            faehigkeitenListView_.getItems().add(entryForNewFaehigkeit_);
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
            spielerListView_.getItems().remove(spielerToDelete);
            spielerList_.remove(spielerToDelete);
            gruppenManagerController.updateGruppenListViews(gruppenManagerController.getSelectedGruppe());

            spielerToDelete.deleteFromDB();
        }
    }

    
    
    @FXML
    private void increaseLevel() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.increaseLevel();

        spielerLevelTextField_.setText(Integer.toString(selectedSpieler
                .getLevel_()));
        spielerKreisLabel_
                .setText(Integer.toString(selectedSpieler.getKreis_()));
    }

    
    
    @FXML
    private void decreaseLevel() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.decreaseLevel();

        spielerLevelTextField_.setText(Integer.toString(selectedSpieler
                .getLevel_()));
        spielerKreisLabel_
                .setText(Integer.toString(selectedSpieler.getKreis_()));
    }

    
    
    @FXML
    private void changeSpielerName() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        String newName = spielerNameTextField_.getText();
        selectedSpieler.setName_(newName);

        updateSpielerLists(selectedSpieler);
    }

    
    
    @FXML
    private void changeDef() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        try {
            int newDefR = Integer.parseInt(defRTextField_.getText());
            int newDefH = Integer.parseInt(defHTextField_.getText());
            int newDefS = Integer.parseInt(defSTextField_.getText());

            if (Charakter.ausruestungIsValid(newDefR, newDefH, newDefS)) {
                selectedSpieler.setDefR(newDefR);
                selectedSpieler.setDefH(newDefH);
                selectedSpieler.setDefS(newDefS);

                updateSpielerLists(selectedSpieler);
            }
        }
        catch (NumberFormatException e) {

        }
    }

    
    
    @FXML
    private void deleteWaffe() {
        Waffen selectedWaffe = getSelectedWaffe();
        if (selectedWaffe == null || selectedWaffe == entryForNewWaffe_)
            return;

        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.deleteWaffe(selectedWaffe);
        waffenListView_.getItems().remove(selectedWaffe);
    }

    
    
    @FXML
    private void changeWaffenDetails() {
        Waffen selectedWaffe = getSelectedWaffe();
        if (selectedWaffe == null)
            return;

        boolean waffeChanged = false;
        waffeChanged = changeWaffenName(selectedWaffe);
        waffeChanged = changeWaffenDamage(selectedWaffe) || waffeChanged;

        if (waffeChanged) {
            if (selectedWaffe == entryForNewWaffe_) {
                Spieler selectedSpieler = getSelectedSpieler();
                selectedSpieler.addWaffe(selectedWaffe);
            }

            updateWaffenList(selectedWaffe);
        }
    }

    
    
    private boolean changeWaffenName(Waffen selectedWaffe) {
        String newName = waffenNameTextField_.getText();
        if (!newName.equals(selectedWaffe.getWaffenName_())) {
            selectedWaffe.setWaffenName_(newName);
            return true;
        }

        return false;
    }

    
    
    private boolean changeWaffenDamage(Waffen selectedWaffe) {
        try {
            int newDamage = Integer.parseInt(waffenDamageTextField_.getText());

            if (newDamage >= 0
                    && newDamage != selectedWaffe.getWaffenSchaden_()) {
                selectedWaffe.setWaffenSchaden_(newDamage);
                return true;
            }
        }
        catch (NumberFormatException e) {
        }
        return false;
    }

    
    
    @FXML
    private void changeFaehigkeitName() {
        Faehigkeiten selectedFaehigkeit = getSelectedFaehigkeit();
        if (selectedFaehigkeit == null)
            return;

        String newName = faehigkeitenNameTextField_.getText();
        selectedFaehigkeit.setName_(newName);

        if (selectedFaehigkeit == entryForNewFaehigkeit_) {
            Spieler selectedSpieler = getSelectedSpieler();
            selectedSpieler.addFaehigkeit(selectedFaehigkeit);
        }

        updateFaehigkeitenList(selectedFaehigkeit);
    }

    
    
    @FXML
    private void deleteFaehigkeit() {
        Faehigkeiten selectedFaehigkeit = getSelectedFaehigkeit();
        if (selectedFaehigkeit == null
                || selectedFaehigkeit == entryForNewFaehigkeit_)
            return;

        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.deleteFaehigkeit(selectedFaehigkeit);
        faehigkeitenListView_.getItems().remove(selectedFaehigkeit);
    }
   
        
    
    private void showSpielerDetails(Spieler spieler) {
        if (spieler == null) {
            showEmptySpielerDetails();
        }
        else {
            spielerNameTextField_.setText(spieler.getName_());
            spielerLevelTextField_.setText(Integer.toString(spieler.getLevel_()));
            spielerKreisLabel_.setText(Integer.toString(spieler.getKreis_()));

            waffenListView_.getItems().setAll(spieler.getWaffen());
            waffenListView_.getItems().add(entryForNewWaffe_);
            waffenListView_.getSelectionModel().select(0);

            defRTextField_.setText(Integer.toString(spieler.getDefR()));
            defHTextField_.setText(Integer.toString(spieler.getDefH()));
            defSTextField_.setText(Integer.toString(spieler.getDefS()));

            faehigkeitenListView_.getItems().setAll(spieler.getFaehigkeiten());
            faehigkeitenListView_.getItems().add(entryForNewFaehigkeit_);
            faehigkeitenListView_.getSelectionModel().select(0);
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

        faehigkeitenListView_.getItems().clear();
    }
    
        
    
    private void showWaffenDetails(Waffen waffen) {
        if (waffen == null) {
            showEmptyWaffenDetails();
        }
        else {
            waffenNameTextField_.setText(waffen.getWaffenName_());
            waffenDamageTextField_.setText(Integer.toString(waffen
                    .getWaffenSchaden_()));
        }
    }

    
    
    private void showEmptyWaffenDetails() {
        waffenNameTextField_.setText("");
        waffenDamageTextField_.setText("");
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

    
    
    private Faehigkeiten getSelectedFaehigkeit() {
        return getSelected(faehigkeitenListView_);
    }

    
    
    @Override
    protected TextField getFaehigkeitenNameTextField() {
        return faehigkeitenNameTextField_;
    }



    @Override
    protected void createEntryForNewFaehigkeit() {
        entryForNewFaehigkeit_ = getEntryForNewFaehigkeit();
    }
    
}
