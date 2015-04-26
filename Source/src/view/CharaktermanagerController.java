package view;

import model.Gruppe;
import model.Spieler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

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
	
	
	public CharaktermanagerController() {
	}
	
	@FXML
	private void initialize() {
		playersNotInGroup_.getItems().setAll(Spieler.getAllPlayers());
		playersNotInGroup_.setCellFactory(new PlayerNameCellFactory());
		
		players_.getItems().setAll(Spieler.getAllPlayers());
		players_.setCellFactory(new PlayerNameCellFactory());
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
