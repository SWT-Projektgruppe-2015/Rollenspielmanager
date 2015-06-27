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
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public abstract class HaendlerTabController extends NotificationController {    
    protected Gegenstand entryForNewGegenstand_;
    protected static final String ALLE_KATEGORIEN = "Alle Kategorien";
    
    void initialize() {
        entryForNewGegenstand_ = new Gegenstand();
        entryForNewGegenstand_.setName_(Gegenstand.GEGENSTAND_NEU);
    }
    
        

    protected void initializeTreeView(TreeView<String> treeView, List<String> elements) {
        TreeItem<String> rootItem = new TreeItem<String>(ALLE_KATEGORIEN);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        addKategorieTreeViewItems(elements);
        
        addListenerToTreeView(treeView);
    }
    
    
    
    protected void addKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = getTreeView().getRoot();
        for(String kategorie : kategorien_) {
            updateTreeViewWithItem(rootItem, kategorie);
        }
    }
    
    
    
    protected void addListenerToTreeView(TreeView<String> treeView) {
        treeView.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(
                    ObservableValue<? extends TreeItem<String>> observable,
                    TreeItem<String> old_value, TreeItem<String> new_value) {
                showKategorieItems(new_value);
            }
        });
    }
    
    
    
    protected void initializeListView() {
        updateListView(getSource());
        addListenerToListView();
    }

    
    
    private void updateListView(List<Gegenstand> newItems) {
        getListView().getItems().setAll(entryForNewGegenstand_);
        Gegenstand.sortByKosten(newItems);
        getListView().getItems().addAll(newItems);
    }

    
    
    private void addListenerToListView() {
        getListView().getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Gegenstand>() {
            @Override
            public void changed(
                    ObservableValue<? extends Gegenstand> observable,
                    Gegenstand oldValue, Gegenstand newValue) {
                showDetails(newValue);
            }
        });
    }

  
    
    protected void updateTreeViewWithItem(TreeItem<String> rootItem, String kategorie) {
        List<String> subKategorien = Gegenstand.getSubKategories(kategorie);
        updateRoot(rootItem, subKategorien);
    }
    
    

    protected void updateRoot(final TreeItem<String> rootItem, List<String> subKategorien) {
        String highestKategorie = subKategorien.get(0);
        if(!ifRootContainsSubKategorie(rootItem, highestKategorie)){
            TreeItem<String> item = new TreeItem<String>(highestKategorie);
            rootItem.getChildren().add(item);
        }
        TreeItem<String> branch = getTreeItemByName(rootItem, highestKategorie); // don't override rootItem
        if(subKategorien.size() == 1){
            showKategorieItems(branch);
            getTreeView().getSelectionModel().select(branch);
            return;
        }
        updateRoot(branch, subKategorien.subList(1, subKategorien.size()));
    }



    protected TreeItem<String> getTreeItemByName(TreeItem<String> rootItem, String subKategorieName) {
        for(TreeItem<String> child : rootItem.getChildren()){
            if(child.getValue().contentEquals(subKategorieName))
                return child;
        }
        return null;
    }



    protected boolean ifRootContainsSubKategorie(TreeItem<String> rootItem, String subKategorie) {
        for(TreeItem<String> child : rootItem.getChildren()){
            String childName = child.getValue();
            if(childName.contentEquals(subKategorie))
                return true;
        }
        return false;
    }
    
    

    protected void changeItem() {
        Gegenstand selectedGegenstand = getSelectedGegenstand(getListView());
        if (selectedGegenstand == null)
            return;
        try {
            fillWithValues(selectedGegenstand);
            String fullKategorie = getFullKategorie();
            if(fullKategorie != null)
                selectedGegenstand.setKategorie_(fullKategorie);
            if (selectedGegenstand.isValid()) {
                checkForNewGegenstand(selectedGegenstand, getSource());
                updateListView(getSource());
                updateKategorieTreeView(selectedGegenstand);
            } else {
                createNotification(NotificationTexts.textForFailedGegenstandUpdate(selectedGegenstand));
            }
        }
        catch (NumberFormatException e) {
            createNotification(NotificationTexts.textForFailedGegenstandUpdate(selectedGegenstand));            
        }
    }

    
    
    protected abstract String getFullKategorie();
    
    
    
    protected abstract void fillWithValues(Gegenstand selectedGegenstand);

    

    protected void updateKategorieTreeView(Gegenstand selectedGegenstand) {
        TreeItem<String> rootItem = getTreeView().getRoot();
        String kategorie = selectedGegenstand.getKategorie_();
        updateTreeViewWithItem(rootItem, kategorie);
    }



    protected void checkForNewGegenstand(Gegenstand selectedGegenstand, List<Gegenstand> allItems) {
        if (selectedGegenstand == entryForNewGegenstand_) {
            allItems.add(selectedGegenstand);
            selectedGegenstand.addToDB();
            createNotification(NotificationTexts.textForNewGegenstand(selectedGegenstand));
            entryForNewGegenstand_ = new Gegenstand();
            entryForNewGegenstand_.setName_(Gegenstand.GEGENSTAND_NEU);
        } else {
            createNotification(NotificationTexts.textForGegenstandUpdate(selectedGegenstand));
        }
    }

    
    
    protected void deleteItem(ListView<Gegenstand> listView, List<Gegenstand> list) {
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
    
    
    
    protected Gegenstand getSelectedGegenstand(ListView<Gegenstand> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;
        Gegenstand selected = listView.getItems().get(selectedIndex);
        return selected;
    }
    
    
    
    protected void searchListView() {
        String search = getListSearch().toLowerCase();
        getListView().getItems().clear();

        if (search.isEmpty())
            getListView().getItems().add(entryForNewGegenstand_);
        
        for (Gegenstand item : getSource()) {
            if (item.getName_().toLowerCase().contains(search)) {
                getListView().getItems().add(item);
            }
        }
    }    
    
    
    
    protected void searchTreeView() {
        String search = getTreeSearch().toLowerCase();
        List<String> matchingKategorien = Gegenstand.getSearchMatchingKategorien(search, getKategorien());
        TreeItem<String> root = getTreeView().getRoot();
        root.getChildren().clear();
        for(String matchingKategorie : matchingKategorien)
            updateTreeViewWithItem(root, matchingKategorie);
        getListView().getItems().clear();
    }
    
    
    
    protected void showKategorieItems(TreeItem<String> new_value) {
        List<Gegenstand> filteredItems = new ArrayList<Gegenstand>();
        if(new_value == null) {
            updateListView(filteredItems);
            clearDetails();
            return;
        }
        String kategorie = new_value.getValue();
        for(Gegenstand gegenstand : getSource()) {
            if(gegenstand.isContainedInKategorie(kategorie) || kategorie.equals(ALLE_KATEGORIEN))
                filteredItems.add(gegenstand);
        }
        updateListView(filteredItems);
        clearDetails();
    }
    
    
    
    protected abstract void clearDetails();
    
    
    
    protected abstract void showDetails(Gegenstand value);
    
    
    
    protected abstract List<Gegenstand> getSource();
    
    
    
    protected abstract List<String> getKategorien();
    
    
    
    protected abstract ListView<Gegenstand> getListView();
    
    
    
    protected abstract TreeView<String> getTreeView();
    
    
    
    protected abstract String getTreeSearch();

    
    
    protected abstract String getListSearch();
}
