package view;

import java.util.ArrayList;
import java.util.List;

import model.Faehigkeiten;
import model.Gruppe;
import model.Spieler;
import model.Waffen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CharaktermanagerController {
	private List<Spieler> playerList_;
	private Gruppe chosenGruppe_;
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
		playerList_= Spieler.getAllPlayers();
		
	}



	private void initializeGroupManager() {
		playersNotInGroupListView_.getItems().setAll(playerList_);
//		List<Gruppe> allGruppen = GruppenManipulator.getAll();
		List<Gruppe> allGruppen = new ArrayList<Gruppe>();
		Gruppe test1 = new Gruppe();
		test1.name_ = "Dienstag";
		Gruppe test2 = new Gruppe();
		test2.name_ = "Freitag";
		allGruppen.add(test1);
		allGruppen.add(test2);
		groupComboBox_.getItems().setAll(allGruppen);
	}
	
	
	
	private void initializePlayerManager() {
		initializePlayerList();
		initializeWaffenList();
		initializeFaehigkeitenList();
	}
	
	
	
	private void initializePlayerList() {
		playersListView_.getItems().setAll(Spieler.getAllPlayers());
		showPlayerDetails(null);
		
		playersListView_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Spieler>() {
            public void changed(ObservableValue<? extends Spieler> observable, Spieler oldValue, Spieler newValue) {
               showPlayerDetails(newValue);
            }
        });
	}
	
	
	
	private void initializeWaffenList() {
//		waffenListView_.setCellFactory(new NameCellFactory<Waffen>());		
		showWaffenDetails(null);		
		
		waffenListView_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Waffen>() {
			public void changed(ObservableValue<? extends Waffen> observable, Waffen oldValue, Waffen newValue) {
				showWaffenDetails(newValue);
			}
		});
	}
	
	
	
	private void initializeFaehigkeitenList() {
//		faehigkeitenListView_.setCellFactory(new NameCellFactory<Faehigkeiten>());
		showFaehigkeitenDetails(null);
		
		faehigkeitenListView_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Faehigkeiten>() {
			public void changed(ObservableValue<? extends Faehigkeiten> observable, Faehigkeiten oldValue, Faehigkeiten newValue) {
				showFaehigkeitenDetails(newValue);
			}
		});
	}
	
	
	
	@FXML
	private void updateGroup() {
	}
	
	@FXML
	private void createGroup() {
		
	}
	
	@FXML
	private void deleteGroup() {
		
	}	
	
	
	
	@FXML
	private void addPlayerToGroup() {	
	}
	
	
	
	@FXML
	private void removePlayerFromGroup() {
	}
	
	
	
	private void showPlayerDetails(Spieler player) {
		if(player == null) {
			showEmptyPlayerDetails();
		}
		else {
			playerNameTextField_.setText(player.name_);
			playerStufeTextField_.setText(Integer.toString(player.level_));
			
			waffenListView_.getItems().setAll(player.getWaffen());
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
			damageTextField_.setText(Integer.toString(waffen.waffenSchaden_));
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
	}
	
	
	
	@FXML
	private void increaseStufe() {
		
	}
	
	
	
	@FXML
	private void decreaseStufe() {
		
	}
}
