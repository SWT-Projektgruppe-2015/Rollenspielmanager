package view.controller;

import java.util.ArrayList;
import java.util.Collections;
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
        alleGegenstaende_ = getAllGegenstaende();
        alleAusruestung_ = getAllAusruestung();
        gegenstandKategorien_ = Gegenstand.getKategorien(alleGegenstaende_);
        ausruestungKategorien_ = Gegenstand.getKategorien(alleAusruestung_);
        entryForNewGegenstand_ = new Gegenstand();
        entryForNewGegenstand_.setName_("Neuer Gegenstand");
        
        initializeGegenstandKategorienTreeView();
        initializeGegenstandListView();
        initializeAusruestungKategorienTreeView();
        initializeAusruestungListView();
    }

    
//          ====================================================
//          =======  Inventar  =================================
//          ====================================================

    
    private List<Gegenstand> getAllAusruestung() {
        List<Gegenstand> allItems = Gegenstand.getAll();
        List<Gegenstand> relevantItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allItems) {
            if(ifGegenstandIsAusruestung(item)){
                relevantItems.add(item);
            }
        }
        return relevantItems;
    }
    private List<Gegenstand> getAllGegenstaende() {
        List<Gegenstand> allItems = Gegenstand.getAll();
        List<Gegenstand> relevantItems = new ArrayList<Gegenstand>();
        for(Gegenstand item : allItems) {
            if(!ifGegenstandIsAusruestung(item)){
                relevantItems.add(item);
            }
        }
        return relevantItems;
    }
    private boolean ifGegenstandIsAusruestung(Gegenstand item) {
        return item.getKategorie_().contains("Waffe") || item.getKategorie_().contains("R" + Hauptprogramm.UMLAUT_SMALL_UE + "stung");
    }


    private void initializeGegenstandKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        gegenstandKategorieTreeView_.setRoot(rootItem);
        gegenstandKategorieTreeView_.showRootProperty().set(false);
        addKategorieTreeViewItems(gegenstandKategorien_);
        
        addListenerGegenstandKategorieTreeView();
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
   


    private void initializeAusruestungListView() {
        updateListView(alleAusruestung_, ausruestungListView_);
        addListenerAusruestungListView();        
    }
    
    

    private void initializeAusruestungKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>("root");
        rootItem.setExpanded(true);
        ausruestungTreeView_.setRoot(rootItem);
        ausruestungTreeView_.showRootProperty().set(false);
        addAusruestungKategorieTreeViewItems(ausruestungKategorien_);
        
        addListenerAusruestungKategorieTreeView();
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
        updateListView(alleGegenstaende_, gegenstandListView_);
        addListenerGegenstandListView();
    }
    
    
    
    private void addAusruestungKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = ausruestungTreeView_.getRoot();
        for(String kategorie : kategorien_) {
            if(kategorie.contains("Waffe") || kategorie.contains("R�stung"))
                updateAusruestungTreeViewWithItem(rootItem, kategorie);
        }
    }



    private void addKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = gegenstandKategorieTreeView_.getRoot();
        for(String kategorie : kategorien_) {
            updateGegenstandTreeViewWithItem(rootItem, kategorie);
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
            String fullKategorie = getFullSubkategoriePath(ausruestungKategorieTextField_.getText(), alleAusruestung_);
            if(fullKategorie != null)
                selectedGegenstand.setKategorie_(fullKategorie);
            if (isValid(selectedGegenstand)) {
                checkForNewGegenstand(selectedGegenstand, alleAusruestung_);
                updateListView(alleAusruestung_, ausruestungListView_);
//                ausruestungListView_.getSelectionModel().select(selectedGegenstand);
                updateAusruestungKategorieTreeView(selectedGegenstand);
            }
        }
        catch (NumberFormatException e) {}
    }
    
    
    
    private String getFullSubkategoriePath(String subKategorie, List<Gegenstand> alleGegenstaende) {
        for(Gegenstand item : alleGegenstaende) {
            String kategorie = item.getKategorie_(); 
            if(kategorie.contains(subKategorie)){
                List<String> subList = Gegenstand.getSubKategories(kategorie);
                String result = "";
                for(int i = 0; i < subList.size(); ++i){
                    result += subList.get(i);
                    if(subList.get(i).contentEquals(subKategorie))
                        return result;
                    result += ".";
                }
            }
        }
        return null;
    }


    @FXML
    private void changeGegenstand() {
        Gegenstand selectedGegenstand = getSelectedGegenstand(gegenstandListView_);
        if (selectedGegenstand == null)
            return;
        try {
            fillGegenstandWithValues(selectedGegenstand);
            String fullKategorie = getFullSubkategoriePath(gegenstandKategorieTextField_.getText(), alleGegenstaende_);
            if(fullKategorie != null)
                selectedGegenstand.setKategorie_(fullKategorie);
            if (isValid(selectedGegenstand)) {
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
    
    
    
    private boolean isValid(Gegenstand selectedGegenstand) {
        boolean isValid = true;
        isValid = (selectedGegenstand.getKosten_() >= 0) && isValid;
        isValid = (selectedGegenstand.getTraglast_() >= 0) && isValid;
        isValid = (!selectedGegenstand.getName_().equals("Neuer Gegenstand")) && isValid;
        return isValid;
    }



    @FXML
    private void deleteGegenstand() {
        Gegenstand itemToDelete = getSelectedGegenstand(gegenstandListView_);
        if (itemToDelete != null && itemToDelete != entryForNewGegenstand_) {
            gegenstandListView_.getItems().remove(itemToDelete);
            alleGegenstaende_.remove(itemToDelete);

            itemToDelete.deleteFromDB();
        }
    }
    
    
    
    @FXML
    private void deleteAusruestung() {
        Gegenstand itemToDelete = getSelectedGegenstand(ausruestungListView_);
        if (itemToDelete != null && itemToDelete != entryForNewGegenstand_) {
            ausruestungListView_.getItems().remove(itemToDelete);
            alleAusruestung_.remove(itemToDelete);

            itemToDelete.deleteFromDB();
        }
    }
    
    

    private void updateListView(List<Gegenstand> newItems, ListView<Gegenstand> listView) {
        listView.getItems().setAll(entryForNewGegenstand_);
        sortWithKosten(newItems);
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
        List<String> matchingKategorien = getSearchMatchingKategorien(search, gegenstandKategorien_);
        TreeItem<String> root = gegenstandKategorieTreeView_.getRoot();
        root.getChildren().setAll();
        for(String matchingKategorie : matchingKategorien)
            updateGegenstandTreeViewWithItem(root, matchingKategorie);
        gegenstandListView_.getItems().clear();
    }


    
    @FXML
    private void searchAusruestungTreeView() {
        String search = searchAusruestungTreeTextField_.getText().toLowerCase();
        List<String> matchingKategorien = getSearchMatchingKategorien(search, ausruestungKategorien_);
        TreeItem<String> root = ausruestungTreeView_.getRoot();
        root.getChildren().setAll();
        for(String matchingKategorie : matchingKategorien)
            updateAusruestungTreeViewWithItem(root, matchingKategorie);
        ausruestungListView_.getItems().clear();
    }
    
    
    
    private List<String> getSearchMatchingKategorien(String search, List<String> kategorien) {
        if(search.contentEquals(""))
            return kategorien;
        List<String> result = new ArrayList<String>();
        for(String kategorie : kategorien){
            if(kategorie.toLowerCase().contains(search))
                result.add(kategorie);
        }
        return result;
    }
    
    
    
    private void showAusruestungKategorieItems(TreeItem<String> new_value) {
        String kategorie = new_value.getValue();
        List<Gegenstand> filteredItems = new ArrayList<Gegenstand>();
        for(Gegenstand gegenstand : alleAusruestung_) {
            if(isItemFromTypeKategorie(kategorie, gegenstand))
                filteredItems.add(gegenstand);
        }
        updateListView(filteredItems, ausruestungListView_);
        clearAusruestungDetails();
    }


    private boolean isItemFromTypeKategorie(String kategorie, Gegenstand item) {
        boolean isContained = item.getKategorie_().contains(kategorie);
        return  isContained;
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
    
    
    
    private void sortWithKosten(List<Gegenstand> allItems_){
        for(int i = 0; i < allItems_.size(); ++i){
            for(int j = 0; j < allItems_.size(); ++j){
                if(allItems_.get(i).getKosten_() < allItems_.get(j).getKosten_()){
                    Collections.swap(allItems_, i, j);
                }
            }
        }
    }
}
