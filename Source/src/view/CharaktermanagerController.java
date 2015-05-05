package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Charakter;
import model.Faehigkeiten;
import model.Gegner;
import model.Gruppe;
import model.Spieler;
import model.Waffen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CharaktermanagerController {
    private List<Spieler> spielerList_;
    private List<Gegner> gegnerList_;

    private List<Gruppe> gruppen_;
    private Spieler entryForNewSpieler_;
    private Waffen entryForNewWaffe_;
    private Faehigkeiten entryForNewFaehigkeit_;
    private Gegner entryForNewGegner_;

    @FXML
    private TextField newGroupNameTextField_;
    @FXML
    private ComboBox<Gruppe> groupComboBox_;
    @FXML
    private ListView<Spieler> playersNotInGroupListView_; // linke Liste
    @FXML
    private ListView<Spieler> playersInGroupListView_; // rechte Liste

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
        initializeGroupManager();
        initializePlayerManager();
        initializeGegnerManager();
    }

    private void initializeController() {
        spielerList_ = Spieler.getAllPlayers();
        gegnerList_ = Gegner.getAllGegner();

    }

    private void initializeGroupManager() {
        playersNotInGroupListView_.getItems().setAll(spielerList_);
        // List<Gruppe> allGruppen = GruppenManipulator.getAll();
        gruppen_ = new ArrayList<Gruppe>();
        groupComboBox_.getItems().setAll(gruppen_);
        groupComboBox_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Gruppe>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Gruppe> observable,
                            Gruppe oldValue, Gruppe newValue) {
                        showGroupName(newValue);
                        updateGroupListViews(newValue);
                    }
                });
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

    private void updateGroupListViews(Gruppe selectedGruppe) {
        if (selectedGruppe == null) {
            playersInGroupListView_.getItems().clear();
            playersNotInGroupListView_.getItems().setAll(spielerList_);
            return;
        }

        Collection<Spieler> playersInGroup = selectedGruppe.getAllSpieler();
        playersInGroupListView_.getItems().setAll(playersInGroup);

        playersNotInGroupListView_.getItems().clear();
        for (Spieler player : spielerList_) {
            if (!playersInGroup.contains(player)) {
                playersNotInGroupListView_.getItems().add(player);
            }
        }
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

        playersNotInGroupListView_.getItems().setAll(spielerList_);
        playersNotInGroupListView_.getSelectionModel().select(changedSpieler);
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

    private Gruppe getSelectedGruppe() {
        int selectedIndex = groupComboBox_.getSelectionModel()
                .getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        Gruppe selectedGruppe = groupComboBox_.getItems().get(selectedIndex);

        return selectedGruppe;
    }

    private Spieler getSelectedSpieler() {
        return getSelected(playersListView_);
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

    private void showGroupName(Gruppe gruppe) {
        if (gruppe == null) {
            newGroupNameTextField_.setText("");
        }
        else {
            newGroupNameTextField_.setText(gruppe.getName());
        }
    }

    @FXML
    private void createGroup() {
        Gruppe newGroup = new Gruppe();
        newGroup.setName(newGroupNameTextField_.getText());
        newGroup.add();
        gruppen_.add(newGroup);
        groupComboBox_.getItems().add(newGroup);
        groupComboBox_.getSelectionModel().select(newGroup);
    }

    @FXML
    private void deleteGroup() {
        Gruppe groupToDelete = getSelectedGruppe();
        if (groupToDelete != null) {
            groupComboBox_.getItems().remove(groupToDelete);
            gruppen_.remove(groupToDelete);
            groupToDelete.remove();
        }
    }

    @FXML
    private void updateGroup() {
        Gruppe selectedGruppe = getSelectedGruppe();
        if (selectedGruppe == null)
            return;

        selectedGruppe.setName(newGroupNameTextField_.getText());
        groupComboBox_.getItems().setAll(gruppen_);
        groupComboBox_.getSelectionModel().select(selectedGruppe);
    }

    @FXML
    private void addPlayerToGroup() {
        Gruppe selectedGruppe = getSelectedGruppe();
        if (selectedGruppe == null)
            return;

        Spieler chosenSpieler = playersNotInGroupListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            playersInGroupListView_.getItems().add(chosenSpieler);
            playersNotInGroupListView_.getItems().remove(chosenSpieler);
            selectedGruppe.addSpieler(chosenSpieler);
        }
    }

    @FXML
    private void removePlayerFromGroup() {
        Spieler chosenSpieler = playersInGroupListView_.getSelectionModel()
                .getSelectedItem();
        if (chosenSpieler != null) {
            playersNotInGroupListView_.getItems().add(chosenSpieler);
            playersInGroupListView_.getItems().remove(chosenSpieler);

            Gruppe selectedGruppe = getSelectedGruppe();
            selectedGruppe.removePlayer(chosenSpieler);
        }
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
            
            this.gegnerDefRTextField_.setText(Integer.toString(gegner.getDefR()));
            damageTextField_.setText(Integer.toString(gegner.getDamage()));
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

        faehigkeitenListView_.getItems().clear();
    }

    private void showEmptyGegnerDetails() {
        gegnerNameTextField_.setText("");
        gegnerKreisTextField_.setText("");
        gegnerStufeTextField_.setText("");

        staerkeTextField_.setText("");
        geschickTextField_.setText("");
        
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
            updateGroupListViews(getSelectedGruppe());

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
    private void changeName() {
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

            if (newDefR > 0 && newDefH > 0 && newDefS >= 0) {
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
}
