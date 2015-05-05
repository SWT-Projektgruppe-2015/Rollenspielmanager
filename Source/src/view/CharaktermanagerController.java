package view;

import java.util.List;

import model.Charakter;
import model.Faehigkeiten;
import model.Gegner;
import model.Spieler;
import model.Waffen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CharaktermanagerController {
    private List<Spieler> spielerList_;
    private List<Gegner> gegnerList_;
    
    private Spieler entryForNewSpieler_;
    private Waffen entryForNewWaffe_;
    private Faehigkeiten entryForNewFaehigkeit_;
    private Gegner entryForNewGegner_;

    @FXML
    private GruppenmanagerController gruppenManagerController;
    
    @FXML
    private ListView<Spieler> playersListView_;
    @FXML
    private TextField searchPlayerTextField_;

    @FXML
    private TextField playerNameTextField_;
    @FXML
    private TextField playerStufeTextField_;
    @FXML
    private Label playerKreisLabel_;

    @FXML
    private ListView<Waffen> waffenListView_;
    @FXML
    private TextField waffenNameTextField_;
    @FXML
    private TextField damageTextField_;

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

    @FXML
    private ListView<Gegner> gegnerListView_;
    @FXML
    private TextField searchGegnerTextField_;

    @FXML
    private TextField gegnerNameTextField_;
    @FXML
    private TextField gegnerStufeTextField_;
    @FXML
    private TextField gegnerKreisTextField_;
    @FXML
    private TextField geschickTextField_;
    @FXML
    private TextField staerkeTextField_;
    @FXML
    private TextField erfahrungsTextField_;

    @FXML
    private TextField gegnerDefRTextField_;
    @FXML
    private TextField gegnerDefHTextField_;
    @FXML
    private TextField gegnerDefSTextField_;
    @FXML
    private TextField gegnerDamageTextField_;

    @FXML
    private ListView<Faehigkeiten> gegnerFaehigkeitenListView_;
    @FXML
    private TextField gegnerFaehigkeitenNameTextField_;

    public CharaktermanagerController() {
    }

    @FXML
    private void initialize() {
        initializeController();
        gruppenManagerController.initialize();
        initializePlayerManager();
        initializeGegnerManager();
    }

    private void initializeController() {
        spielerList_ = Spieler.getAllPlayers();
        gruppenManagerController.setAllSpielerList(spielerList_);
        gegnerList_ = Gegner.getAllGegner();

    }

    private void initializePlayerManager() {
        initializePlayerList();
        initializeWaffenList();
        initializeFaehigkeitenList(faehigkeitenListView_);
    }

    private void initializeGegnerManager() {
        initializeGegnerList();
        initializeFaehigkeitenList(gegnerFaehigkeitenListView_);
    }

    private void initializePlayerList() {
        playersListView_.getItems().setAll(spielerList_);
        entryForNewSpieler_ = getEntryForNewSpieler();
        playersListView_.getItems().add(entryForNewSpieler_);

        showPlayerDetails(null);

        playersListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Spieler>() {
                    public void changed(
                            ObservableValue<? extends Spieler> observable,
                            Spieler oldValue, Spieler newValue) {
                        showPlayerDetails(newValue);
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

    private void initializeFaehigkeitenList(ListView<Faehigkeiten> view) {
        entryForNewFaehigkeit_ = getEntryForNewFaehigkeit();

        showFaehigkeitenDetails(null);

        view.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Faehigkeiten>() {
                    public void changed(
                            ObservableValue<? extends Faehigkeiten> observable,
                            Faehigkeiten oldValue, Faehigkeiten newValue) {
                        showFaehigkeitenDetails(newValue);
                    }
                });
    }

    private void initializeGegnerList() {
        gegnerListView_.getItems().setAll(gegnerList_);
        entryForNewGegner_ = getEntryForNewGegner();
        gegnerListView_.getItems().add(entryForNewGegner_);

        showGegnerDetails(null);

        gegnerListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Gegner>() {
                    public void changed(
                            ObservableValue<? extends Gegner> observable,
                            Gegner oldValue, Gegner newValue) {
                        showGegnerDetails(newValue);
                    }
                });
    }

    private Spieler getEntryForNewSpieler() {
        Spieler entryForNewSpieler = new Spieler();
        entryForNewSpieler.setName_("Neuer Spieler");

        return entryForNewSpieler;
    }

    private Gegner getEntryForNewGegner() {
        Gegner entryForNewGegner = new Gegner();
        entryForNewGegner.setName_("Neuer Gegner");

        return entryForNewGegner;
    }

    private Waffen getEntryForNewWaffe() {
        Waffen entryForNewWaffe = new Waffen();
        entryForNewWaffe.setWaffenName_("Neue Waffe");

        return entryForNewWaffe;
    }

    private Faehigkeiten getEntryForNewFaehigkeit() {
        Faehigkeiten entryForNewFaehigkeit = new Faehigkeiten();
        entryForNewFaehigkeit.setName_("Neue Fähigkeit");

        return entryForNewFaehigkeit;
    }

    private void updateSpielerLists(Spieler changedSpieler) {
        if (changedSpieler == entryForNewSpieler_) {
            spielerList_.add(changedSpieler);
            changedSpieler.add();
            entryForNewSpieler_ = getEntryForNewSpieler();
        }

        playersListView_.getItems().setAll(spielerList_);
        playersListView_.getItems().add(entryForNewSpieler_);
        playersListView_.getSelectionModel().select(changedSpieler);

        gruppenManagerController.updateAllSpieler(spielerList_, changedSpieler);
    }
    
    
    
    private void handleGegnerUpdate(Gegner changedGegner) {
        if (changedGegner == entryForNewGegner_)
            addNewGegner(changedGegner);
        updateGegnerList(changedGegner);
    }

    
    
    private void updateGegnerList(Gegner changedGegner) {
        gegnerListView_.getItems().setAll(gegnerList_);
        gegnerListView_.getItems().add(entryForNewGegner_);
        gegnerListView_.getSelectionModel().select(changedGegner);
    }

    
    
    private void addNewGegner(Gegner changedGegner) {
        gegnerList_.add(changedGegner);
        changedGegner.addToDB();
        entryForNewGegner_ = getEntryForNewGegner();
    }
    
    
    
    private void updateWaffenList(Waffen changedWaffe) {
        waffenListView_.getItems().remove(changedWaffe);
        waffenListView_.getItems().add(changedWaffe);

        if (changedWaffe == entryForNewWaffe_) {
            entryForNewWaffe_ = getEntryForNewWaffe();
            waffenListView_.getItems().add(entryForNewWaffe_);
        }
    }

    private void updateFaehigkeitList(Faehigkeiten changedFaehigkeit) {
        faehigkeitenListView_.getItems().remove(changedFaehigkeit);
        faehigkeitenListView_.getItems().add(changedFaehigkeit);

        if (changedFaehigkeit == entryForNewFaehigkeit_) {
            entryForNewFaehigkeit_ = getEntryForNewFaehigkeit();
            faehigkeitenListView_.getItems().add(entryForNewFaehigkeit_);
        }
    }

    

    private Spieler getSelectedSpieler() {
        return getSelected(playersListView_);
    }
    
    private Gegner getSelectedGegner() {
        return getSelected(gegnerListView_);
    }

    private Waffen getSelectedWaffe() {
        return getSelected(waffenListView_);
    }

    private Faehigkeiten getSelectedFaehigkeit() {
        return getSelected(faehigkeitenListView_);
    }

    private <T> T getSelected(ListView<T> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        T selected = listView.getItems().get(selectedIndex);

        return selected;
    }

    

    private void showPlayerDetails(Spieler player) {
        if (player == null) {
            showEmptyPlayerDetails();
        }
        else {
            playerNameTextField_.setText(player.getName_());
            playerStufeTextField_.setText(Integer.toString(player.getLevel_()));
            playerKreisLabel_.setText(Integer.toString(player.getKreis_()));

            waffenListView_.getItems().setAll(player.getWaffen());
            waffenListView_.getItems().add(entryForNewWaffe_);
            waffenListView_.getSelectionModel().select(0);

            defRTextField_.setText(Integer.toString(player.getDefR()));
            defHTextField_.setText(Integer.toString(player.getDefH()));
            defSTextField_.setText(Integer.toString(player.getDefS()));

            faehigkeitenListView_.getItems().setAll(player.getFaehigkeiten());
            faehigkeitenListView_.getItems().add(entryForNewFaehigkeit_);
            faehigkeitenListView_.getSelectionModel().select(0);
        }
    }

    private void showGegnerDetails(Gegner gegner) {
        if (gegner == null) {
            showEmptyGegnerDetails();
        }
        else {
            gegnerNameTextField_.setText(gegner.getName_());
            gegnerKreisTextField_.setText(Integer.toString(gegner.getKreis_()));
            gegnerStufeTextField_.setText(Integer.toString(gegner.getLevel_()));

            staerkeTextField_.setText(Integer.toString(gegner.getStaerke_()));
            geschickTextField_.setText(Integer.toString(gegner.getGeschick_()));
            erfahrungsTextField_.setText(Integer.toString(gegner.getErfahrung_()));
            
            gegnerDefRTextField_.setText(Integer.toString(gegner.getDefR()));
            gegnerDefHTextField_.setText(Integer.toString(gegner.getDefH()));
            gegnerDefSTextField_.setText(Integer.toString(gegner.getDefS()));
            gegnerDamageTextField_.setText(Integer.toString(gegner.getDamage()));
        }
    }

    private void showEmptyPlayerDetails() {
        playerNameTextField_.setText("");
        playerStufeTextField_.setText("");
        playerKreisLabel_.setText("");

        waffenListView_.getItems().clear();

        defRTextField_.setText("");
        defHTextField_.setText("");
        defSTextField_.setText("");
        gegnerDamageTextField_.setText("");

        faehigkeitenListView_.getItems().clear();
    }

    private void showEmptyGegnerDetails() {
        gegnerNameTextField_.setText("");
        gegnerKreisTextField_.setText("");
        gegnerStufeTextField_.setText("");

        staerkeTextField_.setText("");
        geschickTextField_.setText("");
        
        gegnerDefRTextField_.setText("");
        gegnerDefHTextField_.setText("");
        gegnerDefSTextField_.setText("");        
        
        erfahrungsTextField_.setText("");

        damageTextField_.setText("");
    }

    private void showWaffenDetails(Waffen waffen) {
        if (waffen == null) {
            showEmptyWaffenDetails();
        }
        else {
            waffenNameTextField_.setText(waffen.getWaffenName_());
            damageTextField_.setText(Integer.toString(waffen
                    .getWaffenSchaden_()));
        }
    }

    private void showEmptyWaffenDetails() {
        waffenNameTextField_.setText("");
        damageTextField_.setText("");
    }

    private void showFaehigkeitenDetails(Faehigkeiten faehigkeit) {
        if (faehigkeit == null) {
            showEmptyFaehigkeitenDetails();
        }
        else {
            faehigkeitenNameTextField_.setText(faehigkeit.getName_());
        }
    }

    private void showEmptyFaehigkeitenDetails() {
        faehigkeitenNameTextField_.setText("");
    }

    @FXML
    private void searchSpieler() {
        search(searchPlayerTextField_, playersListView_, spielerList_,
                entryForNewSpieler_);
    }

    @FXML
    private void searchGegner() {
        search(searchGegnerTextField_, gegnerListView_, gegnerList_,
                entryForNewGegner_);
    }

    private <T extends Charakter> void search(TextField searchField,
            ListView<T> listviewToUpdate, List<T> source, T defaultEntry) {
        String search = searchField.getText().toLowerCase();
        listviewToUpdate.getItems().clear();

        for (T item : source) {
            if (item.getName_().toLowerCase().contains(search)) {
                listviewToUpdate.getItems().add(item);
            }
        }

        if (search.isEmpty())
            listviewToUpdate.getItems().add(defaultEntry);
    }

    @FXML
    private void deletePlayer() {
        Spieler spielerToDelete = getSelectedSpieler();
        if (spielerToDelete != null) {
            playersListView_.getItems().remove(spielerToDelete);
            spielerList_.remove(spielerToDelete);
            gruppenManagerController.updateGruppenListViews(gruppenManagerController.getSelectedGruppe());

            spielerToDelete.remove();
        }
    }

    @FXML
    private void increaseStufe() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.increaseLevel();

        playerStufeTextField_.setText(Integer.toString(selectedSpieler
                .getLevel_()));
        playerKreisLabel_
                .setText(Integer.toString(selectedSpieler.getKreis_()));
    }

    @FXML
    private void decreaseStufe() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        selectedSpieler.decreaseLevel();

        playerStufeTextField_.setText(Integer.toString(selectedSpieler
                .getLevel_()));
        playerKreisLabel_
                .setText(Integer.toString(selectedSpieler.getKreis_()));
    }

    @FXML
    private void changeSpielerName() {
        Spieler selectedSpieler = getSelectedSpieler();
        if (selectedSpieler == null)
            return;

        String newName = playerNameTextField_.getText();
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
            int newDamage = Integer.parseInt(damageTextField_.getText());

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

        updateFaehigkeitList(selectedFaehigkeit);
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
    
    @FXML
    private void updateGegnerDetails(){
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null)
            return;
        
        try {
            String newName = gegnerNameTextField_.getText();
            int newStufe = Integer.parseInt(this.gegnerStufeTextField_.getText());
            int newKreis = Integer.parseInt(this.gegnerKreisTextField_.getText());
            int newGeschick = Integer.parseInt(this.geschickTextField_.getText());
            int newStaerke = Integer.parseInt(this.staerkeTextField_.getText());
            int newErfahrung = Integer.parseInt(this.erfahrungsTextField_.getText());
      
            if(Gegner.detailsAreValid(newStufe, newKreis, newGeschick, newStaerke, newErfahrung)){
                selectedGegner.setName_(newName);
                selectedGegner.setLevel_(newStufe);
                selectedGegner.setKreis_(newKreis);
                selectedGegner.setGeschick_(newGeschick);
                selectedGegner.setStaerke_(newStaerke);
                selectedGegner.setErfahrung_(newErfahrung);
                this.handleGegnerUpdate(selectedGegner);
            }
        }
        catch (NumberFormatException e) {
            
        }
    }
    
    
    
    @FXML
    private void updateGegnerAusruestung() {
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null)
            return;
        
        try {
            int newDefR = Integer.parseInt(gegnerDefRTextField_.getText());
            int newDefH = Integer.parseInt(gegnerDefHTextField_.getText());
            int newDefS = Integer.parseInt(gegnerDefSTextField_.getText());
            int newDamage = Integer.parseInt(gegnerDamageTextField_.getText());
            
            if(Charakter.ausruestungIsValid(newDefR, newDefH, newDefS) || newDamage >= 0) {
                selectedGegner.setDefR(newDefR);
                selectedGegner.setDefH(newDefH);
                selectedGegner.setDefS(newDefS);
                selectedGegner.setDamage(newDamage);
                handleGegnerUpdate(selectedGegner);
            }
        }
        catch (NumberFormatException e) {
            
        }

    }
    
    
    
    @FXML
    private void deleteGegner() {
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null || selectedGegner == entryForNewGegner_)
            return;
        
        selectedGegner.deleteFromDB();
        gegnerList_.remove(selectedGegner);
        gegnerListView_.getItems().remove(selectedGegner);
    }
}


