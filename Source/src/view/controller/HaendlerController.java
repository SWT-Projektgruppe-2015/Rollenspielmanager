package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.SharedGegnerTableEntry;
import model.Gegenstand;
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

public class HaendlerController extends NotificationController {
    Gegenstand entryForNewGegenstand_;
    List<Gegenstand> allGegenstaende_;
    List<String> allKategorien_;
    
    @FXML
    private TextField searchTextField_;
    @FXML
    private ListView<Gegenstand> gegenstandListView_;
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
        allGegenstaende_ = Gegenstand.getAll();
        allKategorien_ = Gegenstand.getKategorien(allGegenstaende_);
        entryForNewGegenstand_ = new Gegenstand();
        entryForNewGegenstand_.setName_("Neuer Gegenstand");
        
        initializeKategorienTreeView();
        initializeGegenstandListView();
    }



    private void initializeKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        gegenstandKategorieTreeView_.setRoot(rootItem);
        gegenstandKategorieTreeView_.showRootProperty().set(false);
        addKategorieTreeViewItems(allKategorien_);
        
        gegenstandKategorieTreeView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                    ObservableValue<? extends TreeItem<String>> observable,
                    TreeItem<String> old_value, TreeItem<String> new_value) {
                showKategorieItems(new_value);
            }

        });
        
//        gegenstandKategorieTreeView_.getSelectionModel().selectedItemProperty().addListener((new ChangeListener<String>() {
//            @Override
//            public void changed(
//                    ObservableValue<? extends String> observable,
//                    String oldValue, String newValue) {
//                showKategorieItems(newValue);
//            }
//        });
        
        gegenstandListView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Gegenstand>() {
            @Override
            public void changed(
                    ObservableValue<? extends Gegenstand> observable,
                    Gegenstand oldValue, Gegenstand newValue) {
                showGegenstandDetails(newValue);
            }
        });
    }



    private void initializeGegenstandListView() {
        showGegenstaendeInListView(allGegenstaende_);
        
        gegenstandListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Gegenstand>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Gegenstand> observable,
                            Gegenstand oldValue, Gegenstand newValue) {
                        showGegenstandDetails(newValue);
                    }
                });
    }



    private void addKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = gegenstandKategorieTreeView_.getRoot();
        for(String kategorie : kategorien_) {
            updateTreeViewWithItem(rootItem, kategorie);
        }
    }
    
    
    
    private void updateTreeViewWithItem(TreeItem<String> rootItem, String kategorie) {
        List<String> subKategorien = Gegenstand.getSubKategories(kategorie);
        updateRootElement(rootItem, subKategorien);
    }



    private void updateRootElement(final TreeItem<String> rootItem, List<String> subKategorien) {
        String highestKategorie = subKategorien.get(0);
        if(!ifRootContainsSubKategorie(rootItem, highestKategorie)){
            TreeItem<String> item = new TreeItem<String>(highestKategorie);
            rootItem.getChildren().add(item);
        }
        if(subKategorien.size() == 1)
            return;
        TreeItem<String> branch = getChildItem(rootItem, highestKategorie); // don't override rootItem
        updateRootElement(branch, subKategorien.subList(1, subKategorien.size()));
    }



    private TreeItem<String> getChildItem(TreeItem<String> rootItem,
            String itemName) {
        for(TreeItem<String> child : rootItem.getChildren()){
            if(child.getValue().contentEquals(itemName))
                return child;
        }
        return null;
    }



    private boolean ifRootContainsSubKategorie(TreeItem<String> rootItem, String subKategorie) {
        for(TreeItem<String> child : rootItem.getChildren()){
            String childName = child.getValue();
            if(childName.contentEquals(subKategorie))
                return true;
        }
        return false;
    }
    
    
    
    @FXML
    private void changeGegenstand() {
        Gegenstand selectedGegenstand = getSelectedGegenstand();
        if (selectedGegenstand == null)
            return;

        try {
            fillGegenstandWithValues(selectedGegenstand);
            if (isValid(selectedGegenstand)) {
                checkForNewGegenstand(selectedGegenstand);
                updateGegenstandList(selectedGegenstand);
                updateKategorieTreeView(selectedGegenstand);
            }
        }
        catch (NumberFormatException e) {

        }
    }



    private void updateKategorieTreeView(Gegenstand selectedGegenstand) {
        TreeItem<String> rootItem = gegenstandKategorieTreeView_.getRoot();
        String kategorie = selectedGegenstand.getKategorie_();
        updateTreeViewWithItem(rootItem, kategorie);
    }



    private void checkForNewGegenstand(Gegenstand selectedGegenstand) {
        if (selectedGegenstand == entryForNewGegenstand_) {
            allGegenstaende_.add(selectedGegenstand);
            selectedGegenstand.addToDB();
            entryForNewGegenstand_ = new Gegenstand();
            entryForNewGegenstand_.setName_("Neuer Gegenstand");
        }
    }



    private void fillGegenstandWithValues(Gegenstand selectedGegenstand) {
        String newName = gegenstandNameTextField_.getText();
        int newKosten = Integer.parseInt(gegenstandKostenTextField_.getText());
        String newBeschreibung = gegenstandBeschreibungTextField_.getText();
        int newTraglast = Integer.parseInt(gegenstandTraglastTextField_.getText());
        String newKategorie = gegenstandKategorieTextField_.getText();

        if (!newName.isEmpty()) {
            selectedGegenstand.setName_(newName);
            selectedGegenstand.setKosten_(newKosten);
            selectedGegenstand.setBeschreibung_(newBeschreibung);
            selectedGegenstand.setTraglast_(newTraglast);
            selectedGegenstand.setKategorie_(newKategorie);
        }
    }
    
    
    
    private boolean isValid(Gegenstand selectedGegenstand) {
        boolean isValid = true;
        isValid = (selectedGegenstand.getKosten_() >= 0) && isValid;
        isValid = (selectedGegenstand.getTraglast_() >= 0) && isValid;
        return isValid;
    }



    @FXML
    private void deleteGegenstand() {
        Gegenstand gegenstandToDelete = getSelectedGegenstand();
        if (gegenstandToDelete != null) {
            gegenstandListView_.getItems().remove(gegenstandToDelete);
            allGegenstaende_.remove(gegenstandToDelete);

            gegenstandToDelete.deleteFromDB();
        }
    }
    
    
    
    private void updateGegenstandList(Gegenstand changedGegenstand) {
        gegenstandListView_.getItems().setAll(entryForNewGegenstand_);
        allGegenstaende_.sort(null);
        gegenstandListView_.getItems().addAll(allGegenstaende_);
        gegenstandListView_.getSelectionModel().select(changedGegenstand);
    }



    private Gegenstand getSelectedGegenstand() {
        int selectedIndex = gegenstandListView_.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        Gegenstand selected = gegenstandListView_.getItems().get(selectedIndex);

        return selected;
    }



    @FXML
    private void search() {
        String search = searchTextField_.getText().toLowerCase();
        gegenstandListView_.getItems().clear();

        if (search.isEmpty())
            gegenstandListView_.getItems().add(entryForNewGegenstand_);
        
        for (Gegenstand item : allGegenstaende_) {
            if (item.getName_().toLowerCase().contains(search)) {
                gegenstandListView_.getItems().add(item);
            }
        }
    }
    
    
    
    private void showKategorieItems(TreeItem<String> new_value) {
        String kategorie = new_value.getValue();
        List<Gegenstand> newItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allGegenstaende_) {
            if(item.getKategorie_().contains(kategorie))
                newItems.add(item);
        }
        showGegenstaendeInListView(newItems);
    }
    
    
    
    private void showGegenstaendeInListView(List<Gegenstand> newItems) {
        gegenstandListView_.getItems().setAll(entryForNewGegenstand_);
        gegenstandListView_.getItems().addAll(newItems);
    }



    private void showGegenstandDetails(Gegenstand gegenstand) {
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
