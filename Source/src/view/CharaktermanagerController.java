package view;

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
	@FXML
	private TextField newGroupName_;
	@FXML
	private ComboBox<Gruppe> groupDropdown_;
	@FXML
	private ListView<Spieler> playersNotInGroup_;	
	@FXML
	private ListView<Spieler> playersInGroup_;
	
	@FXML
	private ListView<Spieler> players_;
	@FXML
	private TextField searchPlayer_;
	
	@FXML
	private TextField playerName_;
	@FXML
	private TextField playerStufe_;
	
	@FXML
	private ListView<Waffen> waffen_;
	@FXML
	private TextField damage_;
	
	@FXML
	private TextField defR_;
	@FXML
	private TextField defH_;
	@FXML
	private TextField defS_;
	
	@FXML
	private ListView<Faehigkeiten> faehigkeiten_;
	
	
	public CharaktermanagerController() {
	}
	
	
	
	@FXML
	private void initialize() {
		intializeGroupManager();	
		initializePlayerManager();
	}
	
	
	
	private void intializeGroupManager() {
		playersNotInGroup_.getItems().setAll(Spieler.getAllPlayers());
		playersNotInGroup_.setCellFactory(new NameCellFactory<Spieler>());
	}
	
	
	
	private void initializePlayerManager() {
		initializePlayerList();
		initializeWaffenList();
		initializeFaehigkeitenList();
	}
	
	
	
	private void initializePlayerList() {
		players_.getItems().setAll(Spieler.getAllPlayers());
		players_.setCellFactory(new NameCellFactory<Spieler>());		
		showPlayerDetails(null);
		
		players_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Spieler>() {
            public void changed(ObservableValue<? extends Spieler> observable, Spieler oldValue, Spieler newValue) {
               showPlayerDetails(newValue);
            }
        });
	}
	
	
	
	private void initializeWaffenList() {
		waffen_.setCellFactory(new NameCellFactory<Waffen>());		
		showWaffenDetails(null);		
		
		waffen_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Waffen>() {
			public void changed(ObservableValue<? extends Waffen> observable, Waffen oldValue, Waffen newValue) {
				showWaffenDetails(newValue);
			}
		});
	}
	
	
	
	private void initializeFaehigkeitenList() {
		faehigkeiten_.setCellFactory(new NameCellFactory<Faehigkeiten>());
		showFaehigkeitenDetails(null);
		
		faehigkeiten_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Faehigkeiten>() {
			public void changed(ObservableValue<? extends Faehigkeiten> observable, Faehigkeiten oldValue, Faehigkeiten newValue) {
				showFaehigkeitenDetails(newValue);
			}
		});
	}
	
	
	
	@FXML
	private void changeGroup() {
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
			playerName_.setText(player.name_);
			playerStufe_.setText(Integer.toString(player.level_));
			
			waffen_.getItems().setAll(player.getWaffen());
			waffen_.getSelectionModel().select(0);
			
			defR_.setText(Integer.toString(player.getDefR()));
			defH_.setText(Integer.toString(player.getDefH()));
			defS_.setText(Integer.toString(player.getDefS()));
			
			faehigkeiten_.getItems().setAll(player.getFaehigkeiten());
			faehigkeiten_.getSelectionModel().select(0);
		}
	}
	
	
	
	private void showEmptyPlayerDetails() {
		playerName_.setText("");
		playerStufe_.setText("");
		
		waffen_.getItems().clear();
		
		defR_.setText("");
		defH_.setText("");
		defS_.setText("");
		
		faehigkeiten_.getItems().clear();
	}
	
	
	
	private void showWaffenDetails(Waffen waffen) {
		if(waffen == null) {
			showEmptyWaffenDetails();
		} 
		else {
			damage_.setText(Integer.toString(waffen.waffenSchaden_));
		}
	}
	
	
	
	private void showEmptyWaffenDetails() {
		damage_.setText("");
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
