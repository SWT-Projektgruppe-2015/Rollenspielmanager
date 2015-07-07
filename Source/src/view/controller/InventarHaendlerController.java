package view.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Gegenstand;

public class InventarHaendlerController extends HaendlerTabController {
    List<Gegenstand> alleGegenstaende_;
    List<String> gegenstandKategorien_;
    
    @FXML
    private TextField searchGegenstandListTextField_;
    @FXML
    private TextField searchGegenstandTreeTextField_;   
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
    
    
    void initialize() {
        super.initialize();
        alleGegenstaende_ = Gegenstand.getAllInventar();
        gegenstandKategorien_ = Gegenstand.getKategorien(alleGegenstaende_);

        gegenstandNameTextField_.textProperty().addListener(new MaxTextLengthListener(gegenstandNameTextField_, this, Gegenstand.MAX_NAME_LENGTH));
        gegenstandKategorieTextField_.textProperty().addListener(new MaxTextLengthListener(gegenstandKategorieTextField_, this, Gegenstand.MAX_KATEGORIE_LENGTH));
        gegenstandBeschreibungTextField_.textProperty().addListener(new MaxTextLengthListener(gegenstandBeschreibungTextField_, this, Gegenstand.MAX_BESCHREIBUNG_LENGTH));
        
        initializeGegenstandKategorienTreeView();
        initializeListView();
        gegenstandKategorieTreeView_.getSelectionModel().select(0);
    }
    
    
    
    private void initializeGegenstandKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>(ALLE_KATEGORIEN);
        rootItem.setExpanded(true);
        gegenstandKategorieTreeView_.setRoot(rootItem);
        addKategorieTreeViewItems(gegenstandKategorien_);
        
        addListenerToTreeView(gegenstandKategorieTreeView_);
    }
       
    
    
    @FXML
    private void searchGegenstandTreeView() {
        searchTreeView();
    }
    
    
    
    @FXML
    private void searchGegenstandListView() {
        searchListView();
    }
    
    
    
    @FXML
    private void changeGegenstand() {
        changeItem();
    }
    
    
    
    @Override
    protected boolean fillWithValues(Gegenstand selectedGegenstand) {
        String newName = gegenstandNameTextField_.getText();
        String newBeschreibung = gegenstandBeschreibungTextField_.getText();
        String newKategorie = getFullKategorie();
        
        int newKosten, newTraglast;
        try {
            newTraglast = Integer.parseInt(gegenstandTraglastTextField_.getText());
            newKosten = Integer.parseInt(gegenstandKostenTextField_.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }

        if (!newName.isEmpty() && Gegenstand.isValid(newKosten, newTraglast, newName)) {
            selectedGegenstand.setName_(newName);
            selectedGegenstand.setKosten_(newKosten);
            selectedGegenstand.setBeschreibung_(newBeschreibung);
            selectedGegenstand.setTraglast_(newTraglast);
            selectedGegenstand.setKategorie_(newKategorie);
            return true;
        } else {
            return false;
        }
    }
    
    @FXML
    private void deleteGegenstand() {
        deleteItem(gegenstandListView_, alleGegenstaende_);
    }
    
    @Override
    protected void clearDetails() {
        gegenstandNameTextField_.clear();
        gegenstandKategorieTextField_.clear();
        gegenstandKostenTextField_.clear();
        gegenstandTraglastTextField_.clear();
        gegenstandBeschreibungTextField_.clear();
    }
    
    
    
    @Override
    protected void showDetails(Gegenstand newValue) {
        if(newValue == null) {
            clearDetails();
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
                TreeItem<String> item = getTreeView().getSelectionModel().getSelectedItem();
                if(item != null){
                    String kategorie = item.getValue();
                    gegenstandKategorieTextField_.setText(kategorie);
                }
            }
        }
    }



    @Override
    protected String getFullKategorie() {
        String declaredKategorie = gegenstandKategorieTextField_.getText();
        String subKategoriePath = Gegenstand.getFullSubkategoriePath(declaredKategorie, alleGegenstaende_);
        if(subKategoriePath == null || subKategoriePath.isEmpty())
            return declaredKategorie;
        else
            return subKategoriePath;
    }



    @Override
    protected List<Gegenstand> getSource() {
        return alleGegenstaende_;
    }



    @Override
    protected List<String> getKategorien() {
        return gegenstandKategorien_;
    }



    @Override
    protected ListView<Gegenstand> getListView() {
        return gegenstandListView_;
    }



    @Override
    protected TreeView<String> getTreeView() {
        return gegenstandKategorieTreeView_;
    }



    @Override
    protected String getTreeSearch() {
        return searchGegenstandTreeTextField_.getText();
    }



    @Override
    protected String getListSearch() {
        return searchGegenstandListTextField_.getText();
    }
}
