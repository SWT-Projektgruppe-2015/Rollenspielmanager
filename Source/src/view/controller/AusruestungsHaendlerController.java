package view.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.Gegenstand;

public class AusruestungsHaendlerController extends HaendlerTabController {
    List<Gegenstand> alleAusruestung_;
    List<String> ausruestungKategorien_;
    
    @FXML
    private TextField searchAusruestungListTextField_;
    @FXML
    private TextField searchAusruestungTreeTextField_;
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
    
    void initialize() {
        super.initialize();
        alleAusruestung_ = Gegenstand.getAllAusruestung();
        ausruestungKategorien_ = Gegenstand.getKategorien(alleAusruestung_);
        
        initializeAusruestungKategorienTreeView();
        initializeListView();
    }
    
    
    
    private void initializeAusruestungKategorienTreeView() {
        TreeItem<String> rootItem = new TreeItem<String>(ALLE_KATEGORIEN);
        rootItem.setExpanded(true);
        ausruestungTreeView_.setRoot(rootItem);
        addKategorieTreeViewItems(ausruestungKategorien_);
        addListenerToTreeView(ausruestungTreeView_);
        ausruestungTreeView_.getSelectionModel().select(0);
    }
    
    
    
    @Override
    protected void addKategorieTreeViewItems(List<String> kategorien_) {
        TreeItem<String> rootItem = getTreeView().getRoot();
        for(String kategorie : kategorien_) {
            if(Gegenstand.isAusruestung(kategorie))
                updateTreeViewWithItem(rootItem, kategorie);
        }
    }
    
   
    
    @FXML
    private void changeAusruestung() {
        changeItem();
    }
    
    
    
    @Override
    protected boolean fillWithValues(Gegenstand selectedGegenstand) {
        String newName = ausruestungNameTextField_.getText();
        String newBeschreibung = ausruestungBeschreibungTextField_.getText();
        String newKategorie = getFullKategorie();
        String newWert = ausruestungWertTextField_.getText();
        
        int newKosten, newTraglast, newStaerke;
        try {
            newKosten = Integer.parseInt(ausruestungKostenTextField_.getText());
            newTraglast = Integer.parseInt(ausruestungTraglastTextField_
                    .getText());
            newStaerke = Integer.parseInt(ausruestungStaerkeTextField_
                    .getText());
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
            selectedGegenstand.setStaerke_(newStaerke);
            selectedGegenstand.setWert_(newWert);
            return true;
        }
        else {
            return false;
        }
    }
    
    
    
    @FXML
    private void deleteAusruestung() {
        deleteItem(ausruestungListView_, alleAusruestung_);
    }
    
    
    
    @FXML
    private void searchAusruestungListView() {
        searchListView();
    }
    
    
    
    @FXML
    private void searchAusruestungTreeView() {
        searchTreeView();
    }
    
    
    
    @Override
    protected void clearDetails() {
        this.ausruestungNameTextField_.clear();
        this.ausruestungKategorieTextField_.clear();
        this.ausruestungKostenTextField_.clear();
        this.ausruestungTraglastTextField_.clear();
        this.ausruestungBeschreibungTextField_.setText("");
        this.ausruestungStaerkeTextField_.clear();
        this.ausruestungWertTextField_.clear();
    }
    
    
    
    @Override
    protected void showDetails(Gegenstand newValue) {
        if (newValue == null) {
            clearDetails();
        }
        else {
            ausruestungNameTextField_.setText(newValue.getName_());
            ausruestungKostenTextField_.setText(Integer.toString(newValue
                    .getKosten_()));
            ausruestungBeschreibungTextField_.setText(newValue
                    .getBeschreibung_());
            ausruestungTraglastTextField_.setText(Integer.toString(newValue
                    .getTraglast_()));
            ausruestungStaerkeTextField_.setText(Integer.toString(newValue
                    .getStaerke_()));
            ausruestungWertTextField_.setText(newValue.getWert_());
            
            List<String> subKategorien = Gegenstand.getSubKategories(newValue
                    .getKategorie_());
            String lastKategorie = subKategorien.get(subKategorien.size() - 1);
            ausruestungKategorieTextField_.setText(lastKategorie);
            if (newValue == entryForNewGegenstand_) {
                TreeItem<String> item = getTreeView().getSelectionModel().getSelectedItem();
                if (item != null) {
                    String kategorie = item.getValue();
                    ausruestungKategorieTextField_.setText(kategorie);
                }
            }
        }
    }
    
    @Override
    protected String getFullKategorie() {
        String declaredKategorie = ausruestungKategorieTextField_.getText();
        String subKategoriePath = Gegenstand.getFullSubkategoriePath(declaredKategorie, alleAusruestung_);
        if(subKategoriePath == null || subKategoriePath.isEmpty())
            return declaredKategorie;
        else
            return subKategoriePath;
    }
    
    @Override
    protected List<Gegenstand> getSource() {
        return alleAusruestung_;
    }
    
    @Override
    protected List<String> getKategorien() {
        return ausruestungKategorien_;
    }
    
    @Override
    protected ListView<Gegenstand> getListView() {
        return ausruestungListView_;
    }
    
    @Override
    protected TreeView<String> getTreeView() {
        return ausruestungTreeView_;
    }
    
    @Override
    protected String getTreeSearch() {
        return searchAusruestungTreeTextField_.getText();
    }
    
    @Override
    protected String getListSearch() {
        return searchAusruestungListTextField_.getText();
    }
}
