package view.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

        if (search.isEmpty())
            listviewToUpdate.getItems().add(defaultEntry);
        
        for (T item : source) {
            if (item.getName_().toLowerCase().contains(search)) {
                listviewToUpdate.getItems().add(item);
            }
        }
    }
    
    
    
    protected void initializeFaehigkeitenList(ListView<Faehigkeiten> view) {
        createEntryForNewFaehigkeit();

        showFaehigkeitenDetails(null);

        view.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Faehigkeiten>() {
                    public void changed(
                            ObservableValue<? extends Faehigkeiten> observable,
                            Faehigkeiten oldValue, Faehigkeiten newValue) {
                        showFaehigkeitenDetails(newValue);
                    }
                });
    }
    
    
    
    protected abstract void createEntryForNewFaehigkeit();
    
    
    
    protected Faehigkeiten getEntryForNewFaehigkeit() {
        Faehigkeiten entryForNewFaehigkeit = new Faehigkeiten();
        entryForNewFaehigkeit.setName_("Neue Fähigkeit");

        return entryForNewFaehigkeit;
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
