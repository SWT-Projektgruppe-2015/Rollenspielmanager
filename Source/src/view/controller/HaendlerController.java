package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.SharedGegnerTableEntry;
import model.EinfacherGegenstand;
import model.GegnerTyp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class HaendlerController {
    EinfacherGegenstand entryForNewGegenstand_;
    List<EinfacherGegenstand> allGegenstaende_;
    List<String> allKategorien_;
    
    @FXML
    private TextField searchTextField_;
    @FXML
    private ListView<EinfacherGegenstand> gegenstandListView_;
    @FXML
    private TreeView<String> gegenstandKategorieTreeView_;
    
    @FXML
    private TextField gegenstandNameTextField_;
    @FXML
    private TextField gegenstandKostenTextField_;
    @FXML
    private TextField gegenstandTraglastTextField_;   
    @FXML
    private TextField gegenstandKategorieTextField_;
    @FXML
    private TextArea gegenstandBeschreibungTextField_;
    
    @FXML
    private TextField ausruestungNameTextField_;
    @FXML
    private TextField ausruestungKostenTextField_;
    @FXML
    private TextField ausruestungTraglastTextField_;    
    @FXML
    private TextArea ausruestungBeschreibungTextField_;    
    @FXML
    private TextField ausruestungStaerkeTextField_;
    @FXML
    private TextField ausruestungWertTextField_;
    
    
    
    @FXML
    void initialize() {
        allGegenstaende_ = EinfacherGegenstand.getAll();
        allKategorien_ = EinfacherGegenstand.getKategorien(allGegenstaende_);
        entryForNewGegenstand_ = new EinfacherGegenstand();
        entryForNewGegenstand_.setName_("Neuer Gegenstand");
        
        initializeKategorienTreeView();
        initializeGegenstandListView();
    }



    private void initializeKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        gegenstandKategorieTreeView_.setRoot(rootItem);
        gegenstandKategorieTreeView_.showRootProperty().set(false);
        addRootCategories(allKategorien_);
    }



    private void addRootCategories(List<String> kategorien_) {
        TreeItem<String> rootItem = gegenstandKategorieTreeView_.getRoot();
//        rootItem.getChildren().rem
        for(String kategorie : kategorien_){
            TreeItem<String> item = new TreeItem<String>(kategorie);
            rootItem.getChildren().add(item);
        }
    }



    private void initializeGegenstandListView() {
        gegenstandListView_.getItems().setAll(entryForNewGegenstand_);
        gegenstandListView_.getItems().addAll(allGegenstaende_);
        
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
            int newTraglast = Integer.parseInt(gegenstandTraglastTextField_.getText());
            String newKategorie = gegenstandKategorieTextField_.getText();

            if (newKosten >= 0 && !newName.isEmpty()) {
                selectedEinfacherGegenstand.setName_(newName);
                selectedEinfacherGegenstand.setKosten_(newKosten);
                selectedEinfacherGegenstand.setBeschreibung_(newBeschreibung);
                selectedEinfacherGegenstand.setTraglast_(newTraglast);
                selectedEinfacherGegenstand.setKategorie_(newKategorie);

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

        gegenstandListView_.getItems().setAll(entryForNewGegenstand_);
        allGegenstaende_.sort(null);
        gegenstandListView_.getItems().addAll(allGegenstaende_);
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

        if (search.isEmpty())
            gegenstandListView_.getItems().add(entryForNewGegenstand_);
        
        for (EinfacherGegenstand item : allGegenstaende_) {
            if (item.getName_().toLowerCase().contains(search)) {
                gegenstandListView_.getItems().add(item);
            }
        }
    }
    
    
    
    private void showGegenstandDetails(EinfacherGegenstand gegenstand) {
        if(gegenstand == null) {
            showEmptyGegenstandDetails();
        }
        else {
            gegenstandNameTextField_.setText(gegenstand.getName_());
            gegenstandKostenTextField_.setText(Integer.toString(gegenstand.getKosten_()));
            gegenstandBeschreibungTextField_.setText(gegenstand.getBeschreibung_());
            gegenstandTraglastTextField_.setText(Integer.toString(gegenstand.getTraglast_()));
            gegenstandKategorieTextField_.setText(gegenstand.getKategorie_());
        }
    }



    private void showEmptyGegenstandDetails() {
        gegenstandNameTextField_.setText("");
        gegenstandKostenTextField_.setText("");
        gegenstandBeschreibungTextField_.setText("");
    }
}
