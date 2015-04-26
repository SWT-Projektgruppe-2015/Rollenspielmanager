package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Named;

/**
 * Zeigt in einem ListView nur den Namen des Objekts an.
 * @author Britta Heymann
 *
 * @param <N> Muss das Interface Named implementieren
 */
public class NameCellFactory<N extends Named> implements Callback<ListView<N>, ListCell<N>> {

	@Override
	public ListCell<N> call(ListView<N> param) {
		ListCell<N> cell = new ListCell<N>(){
			
			@Override
			protected void updateItem(N namedItem, boolean bln) {
				super.updateItem(namedItem, bln);
				if (namedItem != null) {
					setText(namedItem.getName());
				}
			}
		};
		
        return cell;
	}

}
