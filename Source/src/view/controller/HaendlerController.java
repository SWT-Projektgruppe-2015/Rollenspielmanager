package view.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.controlsfx.control.action.Action;

import view.NotificationTexts;
import model.Gegenstand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class HaendlerController extends NotificationController {
    Gegenstand entryForNewGegenstand_;
    List<Gegenstand> alleAusruestung_;
    List<Gegenstand> alleGegenstaende_;
    List<String> gegenstandKategorien_;
    List<String> ausruestungKategorien_;
    
    @FXML
    private TextField searchGegenstandListTextField_;
    @FXML
    private TextField searchGegenstandTreeTextField_;
    @FXML
    private TextField searchAusruestungListTextField_;
    @FXML
    private TextField searchAusruestungTreeTextField_;    
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
        alleGegenstaende_ = Gegenstand.getAllInventar();
        alleAusruestung_ = Gegenstand.getAllAusruestung();
        gegenstandKategorien_ = Gegenstand.getKategorien(alleGegenstaende_);
        ausruestungKategorien_ = Gegenstand.getKategorien(alleAusruestung_);
        entryForNewGegenstand_ = new Gegenstand();
        entryForNewGegenstand_.setName_(Gegenstand.GEGENSTAND_NEU);
        
        initializeGegenstandKategorienTreeView();
        initializeGegenstandListView();
        initializeAusruestungKategorienTreeView();
        initializeAusruestungListView();
    }

    
//          ====================================================
//          =======  Inventar  =================================
//          ====================================================
    
    
    
    private void initializeGegenstandKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        gegenstandKategorieTreeView_.setRoot(rootItem);
        gegenstandKategorieTreeView_.showRootProperty().set(false);
        addGegenstandKategorieTreeViewItems(gegenstandKategorien_);
        addListenerGegenstandKategorieTreeView();
    }
    
    

    private void initializeAusruestungKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        ausruestungTreeView_.setRoot(rootItem);
        ausruestungTreeView_.showRootProperty().set(false);
        addAusruestungKategorieTreeViewItems(ausruestungKategorien_);
        
        addListenerAusruestungKategorieTreeView();
    }
    
    
    
    private void initializeGegenstandListView() {
        updateListView(alleGegenstaende_, gegenstandListView_);
        addListenerGegenstandListView();
    }
    
    
    
    private void initializeAusruestungListView() {
        updateListView(alleAusruestung_, ausruestungListView_);
        addListenerAusruestungListView();     
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
    
    
    
    private void addGegenstandKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = gegenstandKategorieTreeView_.getRoot();
        for(String kategorie : kategorien_) {
            updateGegenstandTreeViewWithItem(rootItem, kategorie);
        }
    }
    
    
    
    private void addAusruestungKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = ausruestungTreeView_.getRoot();
        for(String kategorie : kategorien_) {
            if(Gegenstand.isAusruestung(kategorie))
                updateAusruestungTreeViewWithItem(rootItem, kategorie);
        }
    }
    
    
    
    private void updateGegenstandTreeViewWithItem(TreeItem<String> rootItem, String kategorie) {
        List<String> subKategorien = Gegenstand.getSubKategories(kategorie);
        updateGegenstandRoot(rootItem, subKategorien);
    }
    
    
    
    private void updateAusruestungTreeViewWithItem(TreeItem<String> rootItem, String kategorie) {
        List<String> subKategorien = Gegenstand.getSubKategories(kategorie);
        updateAusruestungRoot(rootItem, subKategorien);
    }    



    private void updateGegenstandRoot(final TreeItem<String> rootItem, List<String> subKategorien) {
        String highestKategorie = subKategorien.get(0);
        if(!ifRootContainsSubKategorie(rootItem, highestKategorie)){
            TreeItem<String> item = new TreeItem<String>(highestKategorie);
            rootItem.getChildren().add(item);
        }
        TreeItem<String> branch = getTreeItemByName(rootItem, highestKategorie); // don't override rootItem
        if(subKategorien.size() == 1){
            showGegenstandKategorieItems(branch);
            return;
        }
        updateGegenstandRoot(branch, subKategorien.subList(1, subKategorien.size()));
    }
    
    

    private void updateAusruestungRoot(final TreeItem<String> rootItem, List<String> subKategorien) {
        String highestKategorie = subKategorien.get(0);
        if(!ifRootContainsSubKategorie(rootItem, highestKategorie)){
            TreeItem<String> item = new TreeItem<String>(highestKategorie);
            rootItem.getChildren().add(item);
        }
        TreeItem<String> branch = getTreeItemByName(rootItem, highestKategorie); // don't override rootItem
        if(subKategorien.size() == 1){
            showAusruestungKategorieItems(branch);
            return;
        }
        updateAusruestungRoot(branch, subKategorien.subList(1, subKategorien.size()));
    }



    private TreeItem<String> getTreeItemByName(TreeItem<String> rootItem, String subKategorieName) {
        for(TreeItem<String> child : rootItem.getChildren()){
            if(child.getValue().contentEquals(subKategorieName))
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
            String fullKategorie = Gegenstand.getFullSubkategoriePath(ausruestungKategorieTextField_.getText(), alleAusruestung_);
            if(fullKategorie != null)
                selectedGegenstand.setKategorie_(fullKategorie);
            if (selectedGegenstand.isValid()) {
                checkForNewGegenstand(selectedGegenstand, alleAusruestung_);
                updateListView(alleAusruestung_, ausruestungListView_);
                updateAusruestungKategorieTreeView(selectedGegenstand);
            }
        }
        catch (NumberFormatException e) {}
    }



    @FXML
    private void changeGegenstand() {
        Gegenstand selectedGegenstand = getSelectedGegenstand(gegenstandListView_);
        if (selectedGegenstand == null)
            return;
        try {
            fillGegenstandWithValues(selectedGegenstand);
            String fullKategorie = Gegenstand.getFullSubkategoriePath(gegenstandKategorieTextField_.getText(), alleGegenstaende_);
            if(fullKategorie != null)
                selectedGegenstand.setKategorie_(fullKategorie);
            if (selectedGegenstand.isValid()) {
                checkForNewGegenstand(selectedGegenstand, alleGegenstaende_);
                updateListView(alleGegenstaende_, gegenstandListView_);
                updateGegenstandKategorieTreeView(selectedGegenstand);
            }
        }
        catch (NumberFormatException e) {}
    }


    
    private void updateAusruestungKategorieTreeView(Gegenstand selectedGegenstand) {
        TreeItem<String> rootItem = ausruestungTreeView_.getRoot();
        String kategorie = selectedGegenstand.getKategorie_();
        updateAusruestungTreeViewWithItem(rootItem, kategorie);
    }



    private void updateGegenstandKategorieTreeView(Gegenstand selectedGegenstand) {
        TreeItem<String> rootItem = gegenstandKategorieTreeView_.getRoot();
        String kategorie = selectedGegenstand.getKategorie_();
        updateGegenstandTreeViewWithItem(rootItem, kategorie);
    }



    private void checkForNewGegenstand(Gegenstand selectedGegenstand, List<Gegenstand> allItems) {
        if (selectedGegenstand == entryForNewGegenstand_) {
            allItems.add(selectedGegenstand);
            selectedGegenstand.addToDB();
            entryForNewGegenstand_ = new Gegenstand();
            entryForNewGegenstand_.setName_(Gegenstand.GEGENSTAND_NEU);
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
        } else {
            createNotification(NotificationTexts.textForFailedGegenstandUpdate(selectedGegenstand));
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
        } else {
            createNotification(NotificationTexts.textForFailedGegenstandUpdate(selectedGegenstand));
        }
    }
    
    
    
    @FXML
    private void deleteGegenstand() {
        deleteItem(gegenstandListView_, alleGegenstaende_);
    }
    
    
    
    @FXML
    private void deleteAusruestung() {
        deleteItem(ausruestungListView_, alleAusruestung_);
    }
    
    
    
    private void deleteItem(ListView<Gegenstand> listView, List<Gegenstand> list) {
        Gegenstand itemToDelete = getSelectedGegenstand(listView);
        
        if (itemToDelete != null && itemToDelete != entryForNewGegenstand_) {
            Action deleteItem = new Action(new Consumer<ActionEvent>() {
                @Override
                public void accept(ActionEvent t) {
                    listView.getItems().remove(itemToDelete);
                    list.remove(itemToDelete);
    
                    itemToDelete.deleteFromDB();
                    
                    createNotification(NotificationTexts.textForGegenstandDeletion(itemToDelete));
                }
            });
            
            createReallyDeleteDialog(NotificationTexts.confirmationTextGegenstandDeletion(itemToDelete), deleteItem);
        }
    }
    
    

    private void updateListView(List<Gegenstand> newItems, ListView<Gegenstand> listView) {
        listView.getItems().setAll(entryForNewGegenstand_);
        Gegenstand.sortByKosten(newItems);
        listView.getItems().addAll(newItems);
    }



    private Gegenstand getSelectedGegenstand(ListView<Gegenstand> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;
        Gegenstand selected = listView.getItems().get(selectedIndex);
        return selected;
    }



    @FXML
    private void searchGegenstandListView() {
        String search = searchGegenstandListTextField_.getText().toLowerCase();
        gegenstandListView_.getItems().clear();

        if (search.isEmpty())
            gegenstandListView_.getItems().add(entryForNewGegenstand_);
        
        for (Gegenstand item : alleGegenstaende_) {
            if (item.getName_().toLowerCase().contains(search)) {
                gegenstandListView_.getItems().add(item);
            }
        }
    }
    
    
    
    @FXML
    private void searchAusruestungListView() {
        String search = searchAusruestungListTextField_.getText().toLowerCase();
        ausruestungListView_.getItems().clear();

        if (search.isEmpty())
            ausruestungListView_.getItems().add(entryForNewGegenstand_);
        
        for (Gegenstand item : alleAusruestung_) {
            if (item.getName_().toLowerCase().contains(search)) {
                ausruestungListView_.getItems().add(item);
            }
        }
    }    
    
    
    
    @FXML
    private void searchGegenstandTreeView() {
        String search = searchGegenstandTreeTextField_.getText().toLowerCase();
        List<String> matchingKategorien = Gegenstand.getSearchMatchingKategorien(search, gegenstandKategorien_);
        TreeItem<String> root = gegenstandKategorieTreeView_.getRoot();
        root.getChildren().setAll();
        for(String matchingKategorie : matchingKategorien)
            updateGegenstandTreeViewWithItem(root, matchingKategorie);
        gegenstandListView_.getItems().clear();
    }


    
    @FXML
    private void searchAusruestungTreeView() {
        String search = searchAusruestungTreeTextField_.getText().toLowerCase();
        List<String> matchingKategorien = Gegenstand.getSearchMatchingKategorien(search, ausruestungKategorien_);
        TreeItem<String> root = ausruestungTreeView_.getRoot();
        root.getChildren().setAll();
        for(String matchingKategorie : matchingKategorien)
            updateAusruestungTreeViewWithItem(root, matchingKategorie);
        ausruestungListView_.getItems().clear();
    }
    
    
    
    private void showAusruestungKategorieItems(TreeItem<String> new_value) {
        String kategorie = new_value.getValue();
        List<Gegenstand> filteredItems = new ArrayList<Gegenstand>();
        for(Gegenstand gegenstand : alleAusruestung_) {
            if(gegenstand.isContainedInKategorie(kategorie))
                filteredItems.add(gegenstand);
        }
        updateListView(filteredItems, ausruestungListView_);
        clearAusruestungDetails();
    }
    
    
    
    private void showGegenstandKategorieItems(TreeItem<String> new_value) {
        List<Gegenstand> newItems = new ArrayList<Gegenstand>();
        if(new_value != null){
            String kategorie = new_value.getValue();
            for(Gegenstand item : alleGegenstaende_) {
                if(item.getKategorie_().contains(kategorie))
                    newItems.add(item);
            }
        }
        updateListView(newItems, gegenstandListView_);
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

    
    
    private void showAusruestungDetails(Gegenstand newValue) {
        if(newValue == null) {
            showEmptyAusruestungDetails();
        }
        else {
            ausruestungNameTextField_.setText(newValue.getName_());
            ausruestungKostenTextField_.setText(Integer.toString(newValue.getKosten_()));
            ausruestungBeschreibungTextField_.setText(newValue.getBeschreibung_());
            ausruestungTraglastTextField_.setText(Integer.toString(newValue.getTraglast_()));
            ausruestungStaerkeTextField_.setText(Integer.toString(newValue.getStaerke_()));
            ausruestungWertTextField_.setText(newValue.getWert_());
            
            List<String> subKategorien = Gegenstand.getSubKategories(newValue.getKategorie_());
            String lastKategorie = subKategorien.get(subKategorien.size()-1);
            ausruestungKategorieTextField_.setText(lastKategorie);
            if(newValue == entryForNewGegenstand_){
                TreeItem<String> item = ausruestungTreeView_.getSelectionModel().getSelectedItem();
                if(item != null){
                    String kategorie = item.getValue();
                    ausruestungKategorieTextField_.setText(kategorie);
                }
            }
        }
    }
    


    private void showGegenstandDetails(Gegenstand newValue) {
        if(newValue == null) {
            showEmptyGegenstandDetails();
        }
        else {
            gegenstandNameTextField_.setText(newValue.getName_());
            gegenstandKostenTextField_.setText(Integer.toString(newValue.getKosten_()));
            gegenstandBeschreibungTextField_.setText(newValue.getBeschreibung_());
            gegenstandTraglastTextField_.setText(Integer.toString(newValue.getTraglast_()));
            gegenstandKategorieTextField_.setText(newValue.getKategorie_());
            
            List<String> subKategorien = Gegenstand.getSubKategories(newValue.getKategorie_());
            String lastKategorie = subKategorien.get(subKategorien.size()-1);
            gegenstandKategorieTextField_.setText(lastKategorie);
            if(newValue == entryForNewGegenstand_){
                TreeItem<String> item = gegenstandKategorieTreeView_.getSelectionModel().getSelectedItem();
                if(item != null){
                    String kategorie = item.getValue();
                    gegenstandKategorieTextField_.setText(kategorie);
                }
            }
        }
    }



    private void showEmptyGegenstandDetails() {
        gegenstandNameTextField_.setText("");
        gegenstandKostenTextField_.setText("");
        gegenstandBeschreibungTextField_.setText("");
        gegenstandTraglastTextField_.setText("");
    }
    
    
    
    private void showEmptyAusruestungDetails() {
        ausruestungNameTextField_.setText("");
        ausruestungKostenTextField_.setText("");
        ausruestungBeschreibungTextField_.setText("");
        ausruestungTraglastTextField_.setText("");
        ausruestungKategorieTextField_.setText("");
        ausruestungWertTextField_.setText("");
        ausruestungStaerkeTextField_.setText("");
    }
}
