package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Faehigkeiten;
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
	
	private List<Gruppe> gruppen_;
	private Spieler entryForNewSpieler_;
	private Waffen entryForNewWaffe_;
	
	@FXML
	private TextField newGroupNameTextField_;
	@FXML
	private ComboBox<Gruppe> groupComboBox_;
	@FXML
	private ListView<Spieler> playersNotInGroupListView_; //linke Liste
	@FXML
	private ListView<Spieler> playersInGroupListView_; //rechte Liste
	
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
	private TextField damageTextField_;
	
	@FXML
	private TextField defRTextField_;
	@FXML
	private TextField defHTextField_;
	@FXML
	private TextField defSTextField_;
	
	@FXML
	private ListView<Faehigkeiten> faehigkeitenListView_;
	
	
	public CharaktermanagerController() {
	}
	
	
	
	@FXML
	private void initialize() {
		initializeController();
		initializeGroupManager();	
		initializePlayerManager();
	}
	
	
	
	private void initializeController() {
		spielerList_= Spieler.getAllPlayers();
		
	}
	
	private void updateSpielerLists(Spieler changedSpieler) {
		if(changedSpieler == entryForNewSpieler_) {
			spielerList_.add(changedSpieler);
			entryForNewSpieler_ = getEntryForNewSpieler();
		}
		
		playersListView_.getItems().setAll(spielerList_);		
		playersListView_.getItems().add(entryForNewSpieler_);		
		playersListView_.getSelectionModel().select(changedSpieler);
		
		playersNotInGroupListView_.getItems().setAll(spielerList_);
		playersNotInGroupListView_.getSelectionModel().select(changedSpieler);
	}



	private void initializeGroupManager() {
		playersNotInGroupListView_.getItems().setAll(spielerList_);
//		List<Gruppe> allGruppen = GruppenManipulator.getAll();
		gruppen_ = new ArrayList<Gruppe>();
		Gruppe test1 = new Gruppe();
		test1.setName("Dienstag");
		Gruppe test2 = new Gruppe();
		test2.setName("Freitag");
		gruppen_.add(test1);
		gruppen_.add(test2);
		groupComboBox_.getItems().setAll(gruppen_);
		groupComboBox_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gruppe>() {
			@Override
			public void changed(ObservableValue<? extends Gruppe> observable, Gruppe oldValue, Gruppe newValue) {
				showGroupName(newValue);
				updateGroupListViews(newValue);
			}
		});
	}
	
	
	
	private void showGroupName(Gruppe gruppe) {
		if(gruppe == null) {
			newGroupNameTextField_.setText("");
		}
		else {
			newGroupNameTextField_.setText(gruppe.getName());
		}
	}
	
	
	
	private void updateGroupListViews(Gruppe gruppe) {
		if(gruppe == null) {
			playersInGroupListView_.getItems().clear();
			playersNotInGroupListView_.getItems().setAll(spielerList_);
			return;
		}
		
		Collection<Spieler> playersInGroup = gruppe.getAllSpieler();
		playersInGroupListView_.getItems().setAll(playersInGroup);
		
		playersNotInGroupListView_.getItems().clear();
		for(Spieler player : spielerList_) {
			if(!playersInGroup.contains(player)) {
				playersNotInGroupListView_.getItems().add(player);
			}
		}
	}
	
	
	
	private void initializePlayerManager() {
		initializePlayerList();
		initializeWaffenList();
		initializeFaehigkeitenList();
	}
	
	
	
	private void initializePlayerList() {
		playersListView_.getItems().setAll(Spieler.getAllPlayers());
		entryForNewSpieler_ = getEntryForNewSpieler();
		playersListView_.getItems().add(entryForNewSpieler_);
		
		showPlayerDetails(null);
		
		playersListView_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Spieler>() {
            public void changed(ObservableValue<? extends Spieler> observable, Spieler oldValue, Spieler newValue) {
               showPlayerDetails(newValue);
            }
        });
	}
	
	private Spieler getEntryForNewSpieler() {
		Spieler entryForNewSpieler = new Spieler();
		entryForNewSpieler.setName_("Neuer Spieler");
		
		return entryForNewSpieler;
	}
	
	private void initializeWaffenList() {
		entryForNewWaffe_ = getEntryForNewWaffe();
		
		showWaffenDetails(null);		
		
		waffenListView_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Waffen>() {
			public void changed(ObservableValue<? extends Waffen> observable, Waffen oldValue, Waffen newValue) {
				showWaffenDetails(newValue);
			}
		});
	}
	
	private Waffen getEntryForNewWaffe() {
		Waffen entryForNewWaffe = new Waffen();
		entryForNewWaffe.setWaffenName_("Neue Waffe");
		
		return entryForNewWaffe;
	}
	
	
	
	private void initializeFaehigkeitenList() {
		showFaehigkeitenDetails(null);
		
		faehigkeitenListView_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Faehigkeiten>() {
			public void changed(ObservableValue<? extends Faehigkeiten> observable, Faehigkeiten oldValue, Faehigkeiten newValue) {
				showFaehigkeitenDetails(newValue);
			}
		});
	}
	
	
	
	@FXML
	private void updateGroup() {		
		Gruppe selectedGruppe = getSelectedGruppe();
		if(selectedGruppe == null)
			return;
		
		selectedGruppe.setName(newGroupNameTextField_.getText());
		groupComboBox_.getItems().setAll(gruppen_);
		groupComboBox_.getSelectionModel().select(selectedGruppe);
	}
	
	@FXML
	private void createGroup() {
		Gruppe newGroup = new Gruppe();
		newGroup.setName(newGroupNameTextField_.getText());
		gruppen_.add(newGroup);
		groupComboBox_.getItems().add(newGroup);
		groupComboBox_.getSelectionModel().select(newGroup);
	}
	
	@FXML
	private void deleteGroup() {
		Gruppe groupToDelete = getSelectedGruppe();
		if(groupToDelete != null) {
			groupComboBox_.getItems().remove(groupToDelete);
			gruppen_.remove(groupToDelete);
			groupToDelete.remove();
		}
	}



	private Gruppe getSelectedGruppe() {
		int selectedIndex = groupComboBox_.getSelectionModel().getSelectedIndex();
		if(selectedIndex < 0)
			return null;
					
		Gruppe selectedGruppe = groupComboBox_.getItems().get(selectedIndex);
		
		return selectedGruppe;
	}	
	
	
	
	@FXML
	private void addPlayerToGroup() {	
		Gruppe selectedGruppe = getSelectedGruppe();
		if(selectedGruppe == null)
			return;
		
		Spieler chosenSpieler = playersNotInGroupListView_.getSelectionModel().getSelectedItem();
		if(chosenSpieler != null) {
			playersInGroupListView_.getItems().add(chosenSpieler);
			playersNotInGroupListView_.getItems().remove(chosenSpieler);
			selectedGruppe.addSpieler(chosenSpieler);
		}
	}
	
	
	
	@FXML
	private void removePlayerFromGroup() {
		Spieler chosenSpieler = this.playersInGroupListView_.getSelectionModel().getSelectedItem();
		if(chosenSpieler != null) {
			playersNotInGroupListView_.getItems().add(chosenSpieler);
			playersInGroupListView_.getItems().remove(chosenSpieler);
			
			Gruppe selectedGruppe = getSelectedGruppe();
			selectedGruppe.removePlayer(chosenSpieler);
		}
	}
	
	
	
	private void showPlayerDetails(Spieler player) {
		if(player == null) {
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
			faehigkeitenListView_.getSelectionModel().select(0);
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
	
	
	
	private void showWaffenDetails(Waffen waffen) {
		if(waffen == null) {
			showEmptyWaffenDetails();
		} 
		else {
			damageTextField_.setText(Integer.toString(waffen.getWaffenSchaden_()));
		}
	}
	
	
	
	private void showEmptyWaffenDetails() {
		damageTextField_.setText("");
	}
	
	
	
	private void showFaehigkeitenDetails(Faehigkeiten faehigkeit) {
		if(faehigkeit == null) {
			showEmptyFaehigkeitenDetails();
		}
		else {
			
		}
	}
	
	
	
	private void showEmptyFaehigkeitenDetails() {
		
	}
	
	
	
	@FXML
	private void deletePlayer() {	
		Spieler spielerToDelete = getSelectedSpieler();
		if(spielerToDelete != null) {
			playersListView_.getItems().remove(spielerToDelete);
			spielerList_.remove(spielerToDelete);
			spielerToDelete.remove();
		}
	}
	
	
	
	private Spieler getSelectedSpieler() {
		int selectedIndex = playersListView_.getSelectionModel().getSelectedIndex();
		if(selectedIndex < 0)
			return null;
					
		Spieler selectedSpieler = playersListView_.getItems().get(selectedIndex);
		
		return selectedSpieler;
	}



	@FXML
	private void increaseStufe() {
		Spieler selectedSpieler = getSelectedSpieler();
		if(selectedSpieler == null)
			return;
		
		selectedSpieler.increaseLevel();
		
		playerStufeTextField_.setText(Integer.toString(selectedSpieler.getLevel_()));
		playerKreisLabel_.setText(Integer.toString(selectedSpieler.getKreis_()));
	}
	
	
	
	@FXML
	private void decreaseStufe() {
		Spieler selectedSpieler = getSelectedSpieler();
		if(selectedSpieler == null)
			return;
		
		selectedSpieler.decreaseLevel();
		
		playerStufeTextField_.setText(Integer.toString(selectedSpieler.getLevel_()));
		playerKreisLabel_.setText(Integer.toString(selectedSpieler.getKreis_()));
	}
	
	
	
	@FXML
	private void searchSpieler() {
		String search = searchPlayerTextField_.getText().toLowerCase();
		playersListView_.getItems().clear();
		
		for(Spieler player : spielerList_) {
			if(player.getName_().toLowerCase().contains(search)) {
				playersListView_.getItems().add(player);
			}
		}
		
		if(search.isEmpty())
			playersListView_.getItems().add(entryForNewSpieler_);
	}
	
	
	
	@FXML
	private void changeName() {
		Spieler selectedSpieler = getSelectedSpieler();
		if(selectedSpieler == null)
			return;
		
		String newName = playerNameTextField_.getText();
		selectedSpieler.setName_(newName);
		
		updateSpielerLists(selectedSpieler);
	}
	
	@FXML
	private void changeDef() {
		Spieler selectedSpieler = getSelectedSpieler();
		if(selectedSpieler == null)
			return;
		
		try {
			int newDefR = Integer.parseInt(defRTextField_.getText());
			int newDefH = Integer.parseInt(defHTextField_.getText());
			int newDefS = Integer.parseInt(defSTextField_.getText());
			
			if(newDefR > 0 && newDefH > 0 && newDefS >= 0) {
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
	private void changeWaffenDamage() {
		Waffen selectedWaffe = getSelectedWaffe();
		if(selectedWaffe == null)
			return;
		
		try {
			int newDamage = Integer.parseInt(damageTextField_.getText());
			
			if(newDamage >= 0) {
				selectedWaffe.setWaffenSchaden_(newDamage);
				if(selectedWaffe == entryForNewWaffe_) {
					Spieler selectedSpieler = getSelectedSpieler();
					selectedSpieler.addWaffe(selectedWaffe);
				}				
				updateWaffenList(selectedWaffe);
			}
		}
		catch (NumberFormatException e) {
			
		}
	}

	@FXML
	private void deleteWaffe() {
		Waffen selectedWaffe = getSelectedWaffe();
		if(selectedWaffe == null || selectedWaffe == entryForNewWaffe_)
			return;
		
		Spieler selectedSpieler = getSelectedSpieler();
		if(selectedSpieler == null)
			return;
		
		selectedSpieler.deleteWaffe(selectedWaffe);
		waffenListView_.getItems().remove(selectedWaffe);
	}

	private void updateWaffenList(Waffen changedWaffe) {
		waffenListView_.getItems().remove(changedWaffe);
		waffenListView_.getItems().add(changedWaffe);
		
		if(changedWaffe == entryForNewWaffe_) {
			entryForNewWaffe_ = getEntryForNewWaffe();
			waffenListView_.getItems().add(entryForNewWaffe_);
		}
	}



	private Waffen getSelectedWaffe() {
		int selectedIndex = waffenListView_.getSelectionModel().getSelectedIndex();
		if(selectedIndex < 0)
			return null;
					
		Waffen selectedWaffe = waffenListView_.getItems().get(selectedIndex);
		
		return selectedWaffe;
	}
}
