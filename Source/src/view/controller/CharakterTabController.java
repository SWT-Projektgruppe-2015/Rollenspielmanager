package view.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Charakter;
import model.Faehigkeiten;
import model.Ruestungseffekt;

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
    
    
    
    protected void initializeRuestungseffektList(ListView<Ruestungseffekt> view) {
        createEntryForNewFaehigkeit();

        showRuestungseffektDetails(null);

        view.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Ruestungseffekt>() {
                    public void changed(
                            ObservableValue<? extends Ruestungseffekt> observable,
                            Ruestungseffekt oldValue, Ruestungseffekt newValue) {
                        showRuestungseffektDetails(newValue);
                    }
                });
    }
    
    
    
    protected abstract void createEntryForNewFaehigkeit();
    
    
    
    protected Ruestungseffekt getEntryForNewEffekt() {
        Ruestungseffekt entryForNewEffekt = new Ruestungseffekt();
//        entryForNewEffekt.setName_("Neuer Effekt");

        return entryForNewEffekt;
    }
    
    
    
    protected void showRuestungseffektDetails(Ruestungseffekt effekt) {
        if (effekt == null) {
            showEmptyRuestungseffektDetails();
        }
        else {
            getRuestungseffektNameTextField().setText(effekt.getEffektTyp_().toString());
        }
    }

    
    
    protected void showEmptyRuestungseffektDetails() {
        //getRuestungseffektNameTextField().setText("");
    }
    
    
   
    protected abstract TextField getRuestungseffektNameTextField();
}
