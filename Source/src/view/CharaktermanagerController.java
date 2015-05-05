package view;

import java.util.List;

import model.Charakter;
import model.Faehigkeiten;
import model.Gegner;
import model.Spieler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CharaktermanagerController {
    private List<Spieler> spielerList_;
    private List<Gegner> gegnerList_;
    
    private Gegner entryForNewGegner_;

    @FXML
    private GruppenmanagerController gruppenManagerController;
    @FXML
    private SpielermanagerController spielerManagerController;

    @FXML
    private ListView<Gegner> gegnerListView_;
    @FXML
    private TextField searchGegnerTextField_;

    @FXML
    private TextField gegnerNameTextField_;
    @FXML
    private TextField gegnerStufeTextField_;
    @FXML
    private TextField gegnerKreisTextField_;
    @FXML
    private TextField geschickTextField_;
    @FXML
    private TextField staerkeTextField_;
    @FXML
    private TextField erfahrungsTextField_;

    @FXML
    private TextField gegnerDefRTextField_;
    @FXML
    private TextField gegnerDefHTextField_;
    @FXML
    private TextField gegnerDefSTextField_;
    @FXML
    private TextField gegnerDamageTextField_;

    @FXML
    private ListView<Faehigkeiten> gegnerFaehigkeitenListView_;
    @FXML
    private TextField gegnerFaehigkeitenNameTextField_;

    public CharaktermanagerController() {
    }

    @FXML
    private void initialize() {
        initializeController();
        gruppenManagerController.initialize(spielerList_);
        spielerManagerController.initialize(spielerList_, gruppenManagerController);
        initializeGegnerManager();
    }

    private void initializeController() {
        spielerList_ = Spieler.getAllPlayers();
        gegnerList_ = Gegner.getAllGegner();
    }

    private void initializeGegnerManager() {
        initializeGegnerList();
        //initializeFaehigkeitenList(gegnerFaehigkeitenListView_);
    }

    /*private void initializeFaehigkeitenList(ListView<Faehigkeiten> view) {
        entryForNewFaehigkeit_ = getEntryForNewFaehigkeit();

        showFaehigkeitenDetails(null);

        view.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Faehigkeiten>() {
                    public void changed(
                            ObservableValue<? extends Faehigkeiten> observable,
                            Faehigkeiten oldValue, Faehigkeiten newValue) {
                        showFaehigkeitenDetails(newValue);
                    }
                });
    }
    private void showFaehigkeitenDetails(Faehigkeiten faehigkeit) {
        if (faehigkeit == null) {
            showEmptyFaehigkeitenDetails();
        }
        else {
            faehigkeitenNameTextField_.setText(faehigkeit.getName_());
        }
    }

    private void showEmptyFaehigkeitenDetails() {
        faehigkeitenNameTextField_.setText("");
    }*/

    private void initializeGegnerList() {
        gegnerListView_.getItems().setAll(gegnerList_);
        entryForNewGegner_ = getEntryForNewGegner();
        gegnerListView_.getItems().add(entryForNewGegner_);

        showGegnerDetails(null);

        gegnerListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Gegner>() {
                    public void changed(
                            ObservableValue<? extends Gegner> observable,
                            Gegner oldValue, Gegner newValue) {
                        showGegnerDetails(newValue);
                    }
                });
    }

    private Gegner getEntryForNewGegner() {
        Gegner entryForNewGegner = new Gegner();
        entryForNewGegner.setName_("Neuer Gegner");

        return entryForNewGegner;
    }


    
    
    
    private void handleGegnerUpdate(Gegner changedGegner) {
        if (changedGegner == entryForNewGegner_)
            addNewGegner(changedGegner);
        updateGegnerList(changedGegner);
    }

    
    
    private void updateGegnerList(Gegner changedGegner) {
        gegnerListView_.getItems().setAll(gegnerList_);
        gegnerListView_.getItems().add(entryForNewGegner_);
        gegnerListView_.getSelectionModel().select(changedGegner);
    }

    
    
    private void addNewGegner(Gegner changedGegner) {
        gegnerList_.add(changedGegner);
        changedGegner.addToDB();
        entryForNewGegner_ = getEntryForNewGegner();
    }
    
    
    private Gegner getSelectedGegner() {
        return getSelected(gegnerListView_);
    }

    

    private <T> T getSelected(ListView<T> listView) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0)
            return null;

        T selected = listView.getItems().get(selectedIndex);

        return selected;
    }

    



    private void showGegnerDetails(Gegner gegner) {
        if (gegner == null) {
            showEmptyGegnerDetails();
        }
        else {
            gegnerNameTextField_.setText(gegner.getName_());
            gegnerKreisTextField_.setText(Integer.toString(gegner.getKreis_()));
            gegnerStufeTextField_.setText(Integer.toString(gegner.getLevel_()));

            staerkeTextField_.setText(Integer.toString(gegner.getStaerke_()));
            geschickTextField_.setText(Integer.toString(gegner.getGeschick_()));
            erfahrungsTextField_.setText(Integer.toString(gegner.getErfahrung_()));
            
            gegnerDefRTextField_.setText(Integer.toString(gegner.getDefR()));
            gegnerDefHTextField_.setText(Integer.toString(gegner.getDefH()));
            gegnerDefSTextField_.setText(Integer.toString(gegner.getDefS()));
            gegnerDamageTextField_.setText(Integer.toString(gegner.getDamage()));
        }
    }
    

    private void showEmptyGegnerDetails() {
        gegnerNameTextField_.setText("");
        gegnerKreisTextField_.setText("");
        gegnerStufeTextField_.setText("");

        staerkeTextField_.setText("");
        geschickTextField_.setText("");
        
        gegnerDefRTextField_.setText("");
        gegnerDefHTextField_.setText("");
        gegnerDefSTextField_.setText("");        
        
        erfahrungsTextField_.setText("");

        gegnerDamageTextField_.setText("");
    }

    

    @FXML
    private void searchGegner() {
        search(searchGegnerTextField_, gegnerListView_, gegnerList_,
                entryForNewGegner_);
    }

    private <T extends Charakter> void search(TextField searchField,
            ListView<T> listviewToUpdate, List<T> source, T defaultEntry) {
        String search = searchField.getText().toLowerCase();
        listviewToUpdate.getItems().clear();

        for (T item : source) {
            if (item.getName_().toLowerCase().contains(search)) {
                listviewToUpdate.getItems().add(item);
            }
        }

        if (search.isEmpty())
            listviewToUpdate.getItems().add(defaultEntry);
    }

    
    
    @FXML
    private void updateGegnerDetails(){
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null)
            return;
        
        try {
            String newName = gegnerNameTextField_.getText();
            int newStufe = Integer.parseInt(this.gegnerStufeTextField_.getText());
            int newKreis = Integer.parseInt(this.gegnerKreisTextField_.getText());
            int newGeschick = Integer.parseInt(this.geschickTextField_.getText());
            int newStaerke = Integer.parseInt(this.staerkeTextField_.getText());
            int newErfahrung = Integer.parseInt(this.erfahrungsTextField_.getText());
      
            if(Gegner.detailsAreValid(newStufe, newKreis, newGeschick, newStaerke, newErfahrung)){
                selectedGegner.setName_(newName);
                selectedGegner.setLevel_(newStufe);
                selectedGegner.setKreis_(newKreis);
                selectedGegner.setGeschick_(newGeschick);
                selectedGegner.setStaerke_(newStaerke);
                selectedGegner.setErfahrung_(newErfahrung);
                this.handleGegnerUpdate(selectedGegner);
            }
        }
        catch (NumberFormatException e) {
            
        }
    }
    
    
    
    @FXML
    private void updateGegnerAusruestung() {
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null)
            return;
        
        try {
            int newDefR = Integer.parseInt(gegnerDefRTextField_.getText());
            int newDefH = Integer.parseInt(gegnerDefHTextField_.getText());
            int newDefS = Integer.parseInt(gegnerDefSTextField_.getText());
            int newDamage = Integer.parseInt(gegnerDamageTextField_.getText());
            
            if(Charakter.ausruestungIsValid(newDefR, newDefH, newDefS) || newDamage >= 0) {
                selectedGegner.setDefR(newDefR);
                selectedGegner.setDefH(newDefH);
                selectedGegner.setDefS(newDefS);
                selectedGegner.setDamage(newDamage);
                handleGegnerUpdate(selectedGegner);
            }
        }
        catch (NumberFormatException e) {
            
        }

    }
    
    
    
    @FXML
    private void deleteGegner() {
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null || selectedGegner == entryForNewGegner_)
            return;
        
        selectedGegner.deleteFromDB();
        gegnerList_.remove(selectedGegner);
        gegnerListView_.getItems().remove(selectedGegner);
    }
}


