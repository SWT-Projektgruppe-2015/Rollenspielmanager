package view;

import model.Spieler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PlayerNameCellFactory implements Callback<ListView<Spieler>, ListCell<Spieler>> {

	@Override
	public ListCell<Spieler> call(ListView<Spieler> param) {
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

}
