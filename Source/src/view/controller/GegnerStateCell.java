package view.controller;

import model.GegnerEinheit;
import javafx.scene.control.TableCell;

public class GegnerStateCell extends TableCell<GegnerEinheit, String> {
    protected void updateItem(String name, boolean empty)   {
        super.updateItem(name, empty);
        setText(name);

        if(name == null || name.isEmpty())  {
            return;
        }

        int currentIndex = getTableRow().getIndex();
        GegnerEinheit currentEinheit = 
                (GegnerEinheit) getTableView().getItems().get(currentIndex);
        
        if(currentEinheit != null) {
            if(currentEinheit.getCurrentLebenspunkte_() <= 0) {
                getStyleClass().add("dead-gegner-cell");
            } else {
                getStyleClass().remove("dead-gegner-cell");
            }
        }
    }
}
