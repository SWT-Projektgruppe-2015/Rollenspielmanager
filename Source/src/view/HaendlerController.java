package view;

import java.util.List;

import model.EinfacherGegenstand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HaendlerController {
    EinfacherGegenstand entryForNewGegenstand_;
    List<EinfacherGegenstand> allGegenstaende_;
    
    @FXML
    private TextField searchTextField_;
    @FXML
    private ListView<EinfacherGegenstand> gegenstandListView_;
    
    @FXML
    private TextField gegenstandNameTextField_;
    @FXML
    private TextField gegenstandKostenTextField_;
    @FXML
    private TextArea gegenstandBeschreibungTextField_;
    
    @FXML
    void initialize() {
        allGegenstaende_ = EinfacherGegenstand.getAll();
        entryForNewGegenstand_ = new EinfacherGegenstand();
        entryForNewGegenstand_.setName_("Neuer Gegenstand");
        gegenstandListView_.getItems().setAll(allGegenstaende_);
        gegenstandListView_.getItems().add(entryForNewGegenstand_);
        
        gegenstandListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<EinfacherGegenstand>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends EinfacherGegenstand> observable,
                            EinfacherGegenstand oldValue, EinfacherGegenstand newValue) {
                        showGegenstandDetails(newValue);
                    }
                });
    }
    
    
    
    @FXML
    private void changeGegenstand() {
        EinfacherGegenstand selectedEinfacherGegenstand = getSelectedEinfacherGegenstand();
        if (selectedEinfacherGegenstand == null)
            return;

        try {
            String newName = gegenstandNameTextField_.getText();
            int newKosten = Integer.parseInt(gegenstandKostenTextField_.getText());
            String newBeschreibung = gegenstandBeschreibungTextField_.getText();

            if (newKosten >= 0 && !newName.isEmpty()) {
                selectedEinfacherGegenstand.setName_(newName);
                selectedEinfacherGegenstand.setKosten_(newKosten);
                selectedEinfacherGegenstand.setBeschreibung_(newBeschreibung);

                updateGegenstandList(selectedEinfacherGegenstand);
            }
        }
        catch (NumberFormatException e) {

        }
    }
    
    
    
    @FXML
    private void deleteGegenstand() {
        EinfacherGegenstand gegenstandToDelete = getSelectedEinfacherGegenstand();
        if (gegenstandToDelete != null) {
            gegenstandListView_.getItems().remove(gegenstandToDelete);
            allGegenstaende_.remove(gegenstandToDelete);

            gegenstandToDelete.deleteFromDB();
        }
    }
    
    
    private void updateGegenstandList(EinfacherGegenstand changedEinfacherGegenstand) {
        if (changedEinfacherGegenstand == entryForNewGegenstand_) {
            allGegenstaende_.add(changedEinfacherGegenstand);
            changedEinfacherGegenstand.addToDB();
            entryForNewGegenstand_ = new EinfacherGegenstand();
            entryForNewGegenstand_.setName_("Neuer Gegenstand");
        }

        gegenstandListView_.getItems().setAll(allGegenstaende_);
        gegenstandListView_.getItems().add(entryForNewGegenstand_);
        gegenstandListView_.getSelectionModel().select(changedEinfacherGegenstand);
    }



    private EinfacherGegenstand getSelectedEinfacherGegenstand() {
        int selectedIndex = gegenstandListView_.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        EinfacherGegenstand selected = gegenstandListView_.getItems().get(selectedIndex);

        return selected;
    }



    @FXML
    private void search() {
        String search = searchTextField_.getText().toLowerCase();
        gegenstandListView_.getItems().clear();

        for (EinfacherGegenstand item : allGegenstaende_) {
            if (item.getName_().toLowerCase().contains(search)) {
                gegenstandListView_.getItems().add(item);
            }
        }

        if (search.isEmpty())
            gegenstandListView_.getItems().add(entryForNewGegenstand_);
    }
    
    
    
    private void showGegenstandDetails(EinfacherGegenstand gegenstand) {
        if(gegenstand == null) {
            showEmptyGegenstandDetails();
        }
        else {
            gegenstandNameTextField_.setText(gegenstand == entryForNewGegenstand_ ? "" : gegenstand.getName_());
            gegenstandKostenTextField_.setText(Integer.toString(gegenstand.getKosten_()));
            gegenstandBeschreibungTextField_.setText(gegenstand.getBeschreibung_());
        }
    }



    private void showEmptyGegenstandDetails() {
        gegenstandNameTextField_.setText("");
        gegenstandKostenTextField_.setText("");
        gegenstandBeschreibungTextField_.setText("");
    }
}
