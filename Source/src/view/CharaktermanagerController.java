package view;

import model.Gruppe;
import model.Spieler;
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
	private ListView<Spieler> players_;
	
	@FXML
	private ListView<Spieler> playersInGroup_;
	
	public CharaktermanagerController() {
	}
	
	@FXML
	private void initialize() {
		players_.getItems().setAll(Spieler.getAllPlayers());
	}
}
