package view.controller;

import java.util.List;

import model.Charakter;
import model.Faehigkeiten;
import model.Gegner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GegnermanagerController extends CharakterTabController {
    private List<Gegner> gegnerList_;
    private Gegner entryForNewGegner_;
    private Faehigkeiten entryForNewFaehigkeit_;
    
    @FXML
    private ListView<Gegner> gegnerListView_;
    @FXML
    private TextField searchGegnerTextField_;

    @FXML
    private TextField gegnerNameTextField_;
    @FXML
    private TextField gegnerLevelTextField_;
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
    
    
    void initialize() {
        initializeGegnerList();
        initializeFaehigkeitenList(gegnerFaehigkeitenListView_);
    }
    
    

    private void initializeGegnerList() {
        gegnerList_ = Gegner.getAllGegner();
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

    
    
    @FXML
    private void searchGegner() {
        search(searchGegnerTextField_, gegnerListView_, gegnerList_,
                entryForNewGegner_);
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
    
    
    
    @FXML
    private void updateGegner() {
        Gegner selectedGegner = getSelectedGegner();
        if(selectedGegner == null)
            return;
        
        updateGegnerDetails(selectedGegner);
        

        if (selectedGegner == entryForNewGegner_)
            addNewGegner(selectedGegner);
        
        updateGegnerAusruestung(selectedGegner);
        
        handleGegnerUpdate(selectedGegner);
    }
    
    private void updateGegnerDetails(Gegner selectedGegner){        
        try {
            String newName = gegnerNameTextField_.getText();
            int newLevel = Integer.parseInt(gegnerLevelTextField_.getText());
            int newKreis = Integer.parseInt(gegnerKreisTextField_.getText());
            int newGeschick = Integer.parseInt(geschickTextField_.getText());
            int newStaerke = Integer.parseInt(staerkeTextField_.getText());
            int newErfahrung = Integer.parseInt(erfahrungsTextField_.getText());
      
            if(Gegner.detailsAreValid(newLevel, newKreis, newGeschick, newStaerke, newErfahrung)){
                selectedGegner.setName_(newName);
                selectedGegner.setLevel_(newLevel);
                selectedGegner.setKreis_(newKreis);
                selectedGegner.setGeschick_(newGeschick);
                selectedGegner.setStaerke_(newStaerke);
                selectedGegner.setErfahrung_(newErfahrung);
            }
        }
        catch (NumberFormatException e) {
            
        }
    }
    
    
    
    private void updateGegnerAusruestung(Gegner selectedGegner) {        
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
            }
        }
        catch (NumberFormatException e) {
            
        }

    }
    
    
    
    private void handleGegnerUpdate(Gegner changedGegner) {        
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
        
    
    
    private void showGegnerDetails(Gegner gegner) {
        if (gegner == null) {
            showEmptyGegnerDetails();
        }
        else {
            gegnerNameTextField_.setText(gegner.getName_());
            gegnerKreisTextField_.setText(Integer.toString(gegner.getKreis_()));
            gegnerLevelTextField_.setText(Integer.toString(gegner.getLevel_()));

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
        gegnerLevelTextField_.setText("");

        staerkeTextField_.setText("");
        geschickTextField_.setText("");
        
        gegnerDefRTextField_.setText("");
        gegnerDefHTextField_.setText("");
        gegnerDefSTextField_.setText("");        
        
        erfahrungsTextField_.setText("");

        gegnerDamageTextField_.setText("");
    }
    
    
    
    private Gegner getEntryForNewGegner() {
        Gegner entryForNewGegner = new Gegner();
        entryForNewGegner.setName_("Neuer Gegner");

        return entryForNewGegner;
    }
    
    
    
    private Gegner getSelectedGegner() {
        return getSelected(gegnerListView_);
    }
    
    
    
    @Override
    protected TextField getFaehigkeitenNameTextField() {
        return gegnerFaehigkeitenNameTextField_;
    }

    
    
    @Override
    protected void createEntryForNewFaehigkeit() {
        entryForNewFaehigkeit_ = getEntryForNewFaehigkeit();
    }
    
}
