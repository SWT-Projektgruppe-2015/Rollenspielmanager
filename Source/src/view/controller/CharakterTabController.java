package view.controller;

import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Charakter;

public abstract class CharakterTabController extends NotificationController {
    protected <T> T getSelected(ListView<T> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        T selected = listView.getItems().get(selectedIndex);

        return selected;
    }
    
    
    
    protected <T extends Charakter> void search(TextField searchField,
            ListView<T> listviewToUpdate, List<T> source, T defaultEntry) {
        String search = searchField.getText().toLowerCase();
        listviewToUpdate.getItems().clear();

        if (search.isEmpty())
            listviewToUpdate.getItems().add(defaultEntry);
        
        for (T item : source) {
            if (item.getName_().toLowerCase().contains(search)) {
                listviewToUpdate.getItems().add(item);
            }
        }
    }
}
