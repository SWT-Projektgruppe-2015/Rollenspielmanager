package view.controller;

import java.util.ArrayList;
import java.util.List;

import view.tabledata.SharedGegnerTableEntry;
import model.Ausruestung;
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

public class HaendlerController {
    Gegenstand entryForNewGegenstand_;
    List<Gegenstand> allGegenstaende_;
    List<String> allKategorien_;
    
    @FXML
    private TextField searchTextField_;
    @FXML
    private TextField gegenstandSearchTree_;
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
    private TextField ausruestungKategorieTextField_;
    @FXML
    private TextField ausruestungTreeSearch_;
    @FXML
    private TreeView<String> ausruestungTreeView_;
    @FXML
    private TextField ausruestungListSearch_;
    @FXML
    private ListView<Gegenstand> ausruestungListView_;
    
    
    
    @FXML
    void initialize() {
        allGegenstaende_ = Gegenstand.getAll();
        allKategorien_ = Gegenstand.getKategorien(allGegenstaende_);
        entryForNewGegenstand_ = new Gegenstand();
        entryForNewGegenstand_.setName_("Neuer Gegenstand");
        
        initializeGegenstandKategorienTreeView();
        initializeGegenstandListView();
        initializeAusruestungKategorienTreeView();
        initializeAusruestungListView();
    }
   

    private void initializeAusruestungListView() {
        showGegenstaendeInListView(allGegenstaende_, ausruestungListView_);
        addListenerAusruestungListView();        
    }



    private void initializeAusruestungKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        ausruestungTreeView_.setRoot(rootItem);
        ausruestungTreeView_.showRootProperty().set(false);
        addAusruestungKategorieTreeViewItems(allKategorien_);
        
        addListenerAusruestungKategorieTreeView();
    }



    private void initializeGegenstandKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        gegenstandKategorieTreeView_.setRoot(rootItem);
        gegenstandKategorieTreeView_.showRootProperty().set(false);
        addKategorieTreeViewItems(allKategorien_);
        
        addListenerGegenstandKategorieTreeView();
    }



    private void addListenerAusruestungListView() {
      ausruestungListView_.getSelectionModel().selectedItemProperty()
      .addListener(new ChangeListener<Gegenstand>() {
          @Override
          public void changed(
                  ObservableValue<? extends Gegenstand> observable,
                  Gegenstand oldValue, Gegenstand newValue) {
              showAusruestungDetails(newValue);
          }
      });
    }

    
    
    private void addListenerGegenstandListView() {
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

    
    
    private void addListenerAusruestungKategorieTreeView() {
        ausruestungTreeView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                    ObservableValue<? extends TreeItem<String>> observable,
                    TreeItem<String> old_value, TreeItem<String> new_value) {
                showAusruestungKategorieItems(new_value);
            }
        });
    }
    


    private void addListenerGegenstandKategorieTreeView() {
        gegenstandKategorieTreeView_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                    ObservableValue<? extends TreeItem<String>> observable,
                    TreeItem<String> old_value, TreeItem<String> new_value) {
                showGegenstandKategorieItems(new_value);
            }

        });
    }



    private void initializeGegenstandListView() {
        showGegenstaendeInListView(allGegenstaende_, gegenstandListView_);
        addListenerGegenstandListView();
    }
    
    
    
    private void addAusruestungKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = ausruestungTreeView_.getRoot();
        for(String kategorie : kategorien_) {
            if(kategorie.contains("Waffen") || kategorie.contains("Rüstung"))
                updateTreeViewWithItem(rootItem, kategorie);
        }
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
        TreeItem<String> branch = getChildItem(rootItem, highestKategorie); // don't override rootItem
        if(subKategorien.size() == 1){
            showGegenstandKategorieItems(branch);
            return;
        }
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
    private void changeAusruestung() {
        Gegenstand selectedGegenstand = getSelectedGegenstand(ausruestungListView_);
        if (selectedGegenstand == null)
            return;
        try {
            fillAusruestungWithValues(selectedGegenstand);
            if (isValid(selectedGegenstand)) {
                checkForNewGegenstand(selectedGegenstand);
                updateGegenstandListView(selectedGegenstand, ausruestungListView_);
                updateKategorieTreeView(selectedGegenstand);
            }
        }
        catch (NumberFormatException e) {

        }
    }
    
    
    
    
    @FXML
    private void changeGegenstand() {
        Gegenstand selectedGegenstand = getSelectedGegenstand(gegenstandListView_);
        if (selectedGegenstand == null)
            return;
        try {
            fillGegenstandWithValues(selectedGegenstand);
            if (isValid(selectedGegenstand)) {
                checkForNewGegenstand(selectedGegenstand);
                updateGegenstandListView(selectedGegenstand, gegenstandListView_);
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



    private void fillAusruestungWithValues(Gegenstand selectedGegenstand) {
        String newName = ausruestungNameTextField_.getText();
        int newKosten = Integer.parseInt(ausruestungKostenTextField_.getText());
        String newBeschreibung = ausruestungBeschreibungTextField_.getText();
        int newTraglast = Integer.parseInt(ausruestungTraglastTextField_.getText());
        String newKategorie = ausruestungKategorieTextField_.getText();
        int newStaerke = Integer.parseInt(ausruestungStaerkeTextField_.getText());
        String newWert = ausruestungWertTextField_.getText();

        if (!newName.isEmpty()) {
            selectedGegenstand.setName_(newName);
            selectedGegenstand.setKosten_(newKosten);
            selectedGegenstand.setBeschreibung_(newBeschreibung);
            selectedGegenstand.setTraglast_(newTraglast);
            selectedGegenstand.setKategorie_(newKategorie);
            selectedGegenstand.setStaerke_(newStaerke);
            selectedGegenstand.setWert_(newWert);
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
        Gegenstand gegenstandToDelete = getSelectedGegenstand(gegenstandListView_);
        if (gegenstandToDelete == null) {
            gegenstandToDelete = getSelectedGegenstand(ausruestungListView_);
        }
        if (gegenstandToDelete != null || gegenstandToDelete != entryForNewGegenstand_) {
            gegenstandListView_.getItems().remove(gegenstandToDelete);
            ausruestungListView_.getItems().remove(gegenstandToDelete);
            allGegenstaende_.remove(gegenstandToDelete);

            gegenstandToDelete.deleteFromDB();
        }
    }
    
    
    
    private void updateGegenstandListView(Gegenstand changedGegenstand, ListView<Gegenstand> listView) {
        listView.getItems().setAll(entryForNewGegenstand_);
        allGegenstaende_.sort(null);
        listView.getItems().addAll(allGegenstaende_);
        gegenstandListView_.getSelectionModel().select(changedGegenstand);
    }



    private Gegenstand getSelectedGegenstand(ListView<Gegenstand> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;
        Gegenstand selected = listView.getItems().get(selectedIndex);
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
    
    
    
    @FXML
    private void searchTree() {
        String searchTree = gegenstandSearchTree_.getText().toLowerCase();
        gegenstandKategorieTreeView_.getChildrenUnmodifiable().clear();
    }
    
    

    private void showAusruestungKategorieItems(TreeItem<String> new_value) {
        String kategorie = new_value.getValue();
        List<Gegenstand> filteredItems = new ArrayList<Gegenstand>();
        for(Gegenstand gegenstand : allGegenstaende_) {
            if(filterCategory(kategorie, gegenstand))
                filteredItems.add(gegenstand);
        }
        showGegenstaendeInListView(filteredItems, ausruestungListView_);
        clearAusruestungDetails();
    }


    private boolean filterCategory(String kategorie, Gegenstand item) {
        boolean isContained = item.getKategorie_().contains(kategorie);
        boolean isAusruestung = item.getKategorie_().contains("Waffen") || item.getKategorie_().contains("Rüstung");
        return  isContained && isAusruestung;
    }
    
    
    
    private void showGegenstandKategorieItems(TreeItem<String> new_value) {
        String kategorie = new_value.getValue();
        List<Gegenstand> newItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allGegenstaende_) {
            if(item.getKategorie_().contains(kategorie))
                newItems.add(item);
        }
        showGegenstaendeInListView(newItems, gegenstandListView_);
        clearGegenstandDetails();
    }
    
    
    
    private void clearAusruestungDetails() {
        this.ausruestungNameTextField_.clear();
        this.ausruestungKategorieTextField_.clear();
        this.ausruestungKostenTextField_.clear();
        this.ausruestungTraglastTextField_.clear();
        this.ausruestungBeschreibungTextField_.clear();
    }
    
    
    
    private void clearGegenstandDetails() {
        this.gegenstandNameTextField_.clear();
        this.gegenstandKategorieTextField_.clear();
        this.gegenstandKostenTextField_.clear();
        this.gegenstandTraglastTextField_.clear();
        this.gegenstandBeschreibungTextField_.clear();
    }



    private void showGegenstaendeInListView(List<Gegenstand> newItems, ListView<Gegenstand> listView) {
        listView.getItems().setAll(entryForNewGegenstand_);
        listView.getItems().addAll(newItems);
    }

    
    
    private void showAusruestungDetails(Gegenstand newValue) {
        if(newValue == null) {
            showEmptyGegenstandDetails();
        }
        else {
            ausruestungNameTextField_.setText(newValue.getName_());
            ausruestungKostenTextField_.setText(Integer.toString(newValue.getKosten_()));
            ausruestungBeschreibungTextField_.setText(newValue.getBeschreibung_());
            ausruestungTraglastTextField_.setText(Integer.toString(newValue.getTraglast_()));
            ausruestungKategorieTextField_.setText(newValue.getKategorie_());
            ausruestungStaerkeTextField_.setText(Integer.toString(newValue.getStaerke_()));
            ausruestungWertTextField_.setText(newValue.getWert_());
        } 
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