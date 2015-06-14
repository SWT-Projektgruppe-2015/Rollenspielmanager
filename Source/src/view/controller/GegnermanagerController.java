package view.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.controlsfx.control.action.Action;

import view.NotificationTexts;
import model.Charakter;
import model.Faehigkeiten;
import model.GegnerTyp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GegnermanagerController extends CharakterTabController {
    private List<GegnerTyp> gegnerList_;
    private GegnerTyp entryForNewGegner_;
    private Faehigkeiten entryForNewFaehigkeit_;
    
    @FXML
    private CheckBox kreis1CheckBox_;
    @FXML
    private CheckBox kreis2CheckBox_;
    @FXML
    private CheckBox kreis3CheckBox_;
    @FXML
    private CheckBox kreis4CheckBox_;
    
    @FXML
    private ListView<GegnerTyp> gegnerListView_;
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
    private TextField lebenspunkteTextField_;

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
        gegnerList_ = GegnerTyp.getAll();
        entryForNewGegner_ = getEntryForNewGegner();
        gegnerListView_.getItems().setAll(entryForNewGegner_);
        gegnerListView_.getItems().addAll(gegnerList_);

        showGegnerDetails(null);

        gegnerListView_.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<GegnerTyp>() {
                    public void changed(
                            ObservableValue<? extends GegnerTyp> observable,
                            GegnerTyp oldValue, GegnerTyp newValue) {
                        showGegnerDetails(newValue);
                    }
                });
    }

    
    
    @FXML
    private void filterByKreis() {
        List<Integer> kreiseToShow = new ArrayList();
        if (kreis1CheckBox_.isSelected()) {
            kreiseToShow.add(1);
        }
        if (kreis2CheckBox_.isSelected()) {
            kreiseToShow.add(2);
        }
        if (kreis3CheckBox_.isSelected()) {
            kreiseToShow.add(3);
        }
        if (kreis4CheckBox_.isSelected()) {
            kreiseToShow.add(4);
        }

        gegnerListView_.getItems().clear();
        gegnerListView_.getItems().setAll(entryForNewGegner_);
        for (GegnerTyp item : gegnerList_) {
            if (kreiseToShow.contains(item.getKreis_())) {
                gegnerListView_.getItems().add(item);
            }
        }
    }
    
    
    
    @FXML
    private void searchGegner() {
        search(searchGegnerTextField_, gegnerListView_, gegnerList_,
                entryForNewGegner_);
    }

    
    
    @FXML
    private void deleteGegner() {
        GegnerTyp selectedGegner = getSelectedGegner();
        if(selectedGegner == null || selectedGegner == entryForNewGegner_)
            return;
        
        Action deleteGegner = new Action(new Consumer<ActionEvent>() {
            @Override
            public void accept(ActionEvent t) {
                selectedGegner.deleteFromDB();
                gegnerList_.remove(selectedGegner);
                gegnerListView_.getItems().remove(selectedGegner);
                createNotification(NotificationTexts.textForGegnerDeletion(selectedGegner));
            }
        });
        createReallyDeleteDialog(NotificationTexts.confirmationTextGegnerDeletion(selectedGegner), deleteGegner);
    }
    
    
    
    @FXML
    private void updateGegner() {
        GegnerTyp selectedGegner = getSelectedGegner();
        if(selectedGegner == null)
            return;
        
        updateGegnerDetails(selectedGegner);   

        if (selectedGegner == entryForNewGegner_) {
            addNewGegner(selectedGegner);
            createNotification(NotificationTexts.textForNewCharakter(selectedGegner));
        } else {
            createNotification(NotificationTexts.textForGegnerUpdate(selectedGegner));
        }
        
        updateGegnerAusruestung(selectedGegner);
        
        handleGegnerUpdate(selectedGegner);
    }
    
    
    
    private void updateGegnerDetails(GegnerTyp selectedGegner){        
        try {
            String newName = gegnerNameTextField_.getText();
            int newLevel = Integer.parseInt(gegnerLevelTextField_.getText());
            int newKreis = Integer.parseInt(gegnerKreisTextField_.getText());
            int newGeschick = Integer.parseInt(geschickTextField_.getText());
            int newStaerke = Integer.parseInt(staerkeTextField_.getText());
            int newErfahrung = Integer.parseInt(erfahrungsTextField_.getText());
            int newLebenspunkte = Integer.parseInt(lebenspunkteTextField_.getText());
      
            if(GegnerTyp.detailsAreValid(newLevel, newKreis, newGeschick, newStaerke, newErfahrung)){
                selectedGegner.setName_(newName);
                selectedGegner.setLevel_(newLevel);
                selectedGegner.setKreis_(newKreis);
                selectedGegner.setGeschick_(newGeschick);
                selectedGegner.setStaerke_(newStaerke);
                selectedGegner.setErfahrung_(newErfahrung);
                selectedGegner.setMaxLebenspunkte_(newLebenspunkte);
            }
        }
        catch (NumberFormatException e) {
            
        }
    }
    
    
    
    private void updateGegnerAusruestung(GegnerTyp selectedGegner) {        
        try {
            int newDefR = Integer.parseInt(gegnerDefRTextField_.getText());
            int newDefH = Integer.parseInt(gegnerDefHTextField_.getText());
            int newDefS = Integer.parseInt(gegnerDefSTextField_.getText());
            int newDamage = Integer.parseInt(gegnerDamageTextField_.getText());
            
            if(Charakter.ausruestungIsValid(newDefR, newDefH, newDefS) || newDamage >= 0) {
                selectedGegner.setDefR(newDefR);
                selectedGegner.setDefH(newDefH);
                selectedGegner.setDefS(newDefS);
                selectedGegner.setSchaden_(newDamage);
            }
        }
        catch (NumberFormatException e) {
            
        }

    }
    
    
    
    private void handleGegnerUpdate(GegnerTyp changedGegner) {        
        updateGegnerList(changedGegner);
    }

    
    
    private void updateGegnerList(GegnerTyp changedGegner) {
        gegnerList_.sort(null);
        filterByKreis();
        
        if(gegnerListView_.getItems().contains(changedGegner))
            gegnerListView_.getSelectionModel().select(changedGegner);
    }

    
    
    private void addNewGegner(GegnerTyp changedGegner) {
        gegnerList_.add(changedGegner);
        changedGegner.addToDB();
        entryForNewGegner_ = getEntryForNewGegner();
    }
        
    
    
    private void showGegnerDetails(GegnerTyp gegnerTyp) {
        if (gegnerTyp == null) {
            showEmptyGegnerDetails();
        }
        else {
            gegnerNameTextField_.setText(gegnerTyp.getName_());
            gegnerKreisTextField_.setText(Integer.toString(gegnerTyp.getKreis_()));
            gegnerLevelTextField_.setText(Integer.toString(gegnerTyp.getLevel_()));

            staerkeTextField_.setText(Integer.toString(gegnerTyp.getStaerke_()));
            geschickTextField_.setText(Integer.toString(gegnerTyp.getGeschick_()));
            erfahrungsTextField_.setText(Integer.toString(gegnerTyp.getErfahrung_()));
            lebenspunkteTextField_.setText(Integer.toString(gegnerTyp.getMaxLebenspunkte_()));
            
            gegnerDefRTextField_.setText(Integer.toString(gegnerTyp.getDefR()));
            gegnerDefHTextField_.setText(Integer.toString(gegnerTyp.getDefH()));
            gegnerDefSTextField_.setText(Integer.toString(gegnerTyp.getDefS()));
            gegnerDamageTextField_.setText(Integer.toString(gegnerTyp.getSchaden_()));
        }
    }
    

    
    private void showEmptyGegnerDetails() {
        gegnerNameTextField_.setText("");
        gegnerKreisTextField_.setText("");
        gegnerLevelTextField_.setText("");

        staerkeTextField_.setText("");
        geschickTextField_.setText("");
        lebenspunkteTextField_.setText("");
        
        gegnerDefRTextField_.setText("");
        gegnerDefHTextField_.setText("");
        gegnerDefSTextField_.setText("");        
        
        erfahrungsTextField_.setText("");

        gegnerDamageTextField_.setText("");
    }
    
    
    
    private GegnerTyp getEntryForNewGegner() {
        GegnerTyp entryForNewGegner = new GegnerTyp();
        entryForNewGegner.setName_("Neuer GegnerTyp");

        return entryForNewGegner;
    }
    
    
    
    private GegnerTyp getSelectedGegner() {
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
