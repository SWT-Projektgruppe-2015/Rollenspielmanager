package view;

import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Charakter;
import model.Faehigkeiten;

public abstract class CharakterTabController {
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

        for (T item : source) {
            if (item.getName_().toLowerCase().contains(search)) {
                listviewToUpdate.getItems().add(item);
            }
        }

        if (search.isEmpty())
            listviewToUpdate.getItems().add(defaultEntry);
    }
    
    
    
    protected void showFaehigkeitenDetails(Faehigkeiten faehigkeit) {
        if (faehigkeit == null) {
            showEmptyFaehigkeitenDetails();
        }
        else {
            getFaehigkeitenNameTextField().setText(faehigkeit.getName_());
        }
    }

    
    
    protected void showEmptyFaehigkeitenDetails() {
        getFaehigkeitenNameTextField().setText("");
    }
    
    
   
    protected abstract TextField getFaehigkeitenNameTextField();
}
