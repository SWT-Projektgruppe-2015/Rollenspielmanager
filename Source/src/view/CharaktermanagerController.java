package view;

import model.Gruppe;
import model.Spieler;
import javafx.fxml.FXML;
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
	private ListView<Spieler> players_;
	
	@FXML
	private ListView<Spieler> playersInGroup_;
	
	public CharaktermanagerController() {
	}
	
	@FXML
	private void initialize() {
		players_.getItems().setAll(Spieler.getAllPlayers());
		
		players_.setCellFactory(new Callback<ListView<Spieler>, ListCell<Spieler>>() {
			@Override
            public ListCell<Spieler> call(ListView<Spieler> p) {
                 
                ListCell<Spieler> cell = new ListCell<Spieler>(){
 
                    @Override
                    protected void updateItem(Spieler spieler, boolean bln) {
                        super.updateItem(spieler, bln);
                        if (spieler != null) {
                            setText(spieler.name_);
                        }
                    }
 
                };
                 
                return cell;
            }
		});
	}
}
