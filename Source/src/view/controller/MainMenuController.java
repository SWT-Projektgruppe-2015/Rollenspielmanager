package view.controller;

import java.util.List;

import model.Gruppe;
import model.Spieler;
import controller.GruppenSubject;
import controller.Hauptprogramm;
import controller.interfaces.GruppenObserver;
import controller.manipulators.GruppenManipulator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainMenuController implements GruppenObserver{
    @FXML
    private ComboBox<Gruppe> gruppenDropDown_;
    @FXML
    private TableView<Spieler> spielerInGruppe_;
    @FXML
    private TableColumn<Spieler, String> name_;
    @FXML
    private TableColumn<Spieler, Integer> kreis_;
    @FXML
    private TableColumn<Spieler, Integer> level_;
    
    private Hauptprogramm hauptProgramm_;
    
    private GruppenSubject gruppenSubject_;
    
    private ChangeListener<Gruppe> selectedGruppenObserver_;
    
    
    
    public void setGruppenSubject_(GruppenSubject gruppenSubject_) {
        this.gruppenSubject_ = gruppenSubject_;
        this.gruppenSubject_.setSelectedGruppe(gruppenDropDown_.getSelectionModel().getSelectedItem());
    }



    public MainMenuController() {
    }
    
    
    
    @FXML
    private void initialize() {
        gruppenDropDown_.getItems().setAll(GruppenManipulator.getInstance().getAll());
        gruppenDropDown_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Gruppe>() {
            @Override
            public void changed(
                    ObservableValue<? extends Gruppe> observable,
                    Gruppe oldValue, Gruppe newValue) {
                updateGruppenTableView(newValue);
            }
        });
        
        selectedGruppenObserver_ = new ChangeListener<Gruppe>() {            
            @Override
            public void changed(ObservableValue<? extends Gruppe> observable,
                    Gruppe oldValue, Gruppe newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gruppenSubject_.setSelectedGruppe(newValue);
                    }
                });
            }
        };
        gruppenDropDown_.getSelectionModel().selectedItemProperty().addListener(selectedGruppenObserver_);
        
        kreis_.setCellValueFactory(new PropertyValueFactory<Spieler, Integer>("kreis_"));
        level_.setCellValueFactory(new PropertyValueFactory<Spieler, Integer>("level_"));
        name_.setCellValueFactory(new PropertyValueFactory<Spieler, String>("name_"));
    }

    
    
    private void updateGruppenTableView(Gruppe newValue) {
        if(newValue == null)
            spielerInGruppe_.getItems().clear();
        else
            spielerInGruppe_.getItems().setAll(newValue.getOrderedMemberList());
    }
    
    
    
    public void setHauptProgramm(Hauptprogramm hauptProgramm) {
        hauptProgramm_ = hauptProgramm;
    }

    @FXML
    private void onWuerfelSimulatorClick() {
        hauptProgramm_.openWuerfelSimulator();
    }

    @FXML
    private void onCharakterManagerClick() {
        hauptProgramm_.openCharakterManager();
    }
    
    
    
    @FXML
    private void onHaendlerClick() {
        hauptProgramm_.openHaendler();
    }
    
    
    @FXML
    private void onKampfClick() {
        hauptProgramm_.openTeilnehmerauswahl();
    }



    @Override
    public void updateGruppenList() {
        gruppenDropDown_.getSelectionModel().selectedItemProperty().removeListener(selectedGruppenObserver_);

        Gruppe selectedGruppe = gruppenDropDown_.getSelectionModel().getSelectedItem();
        List<Gruppe> currentGruppen = gruppenSubject_.getGruppen();
        gruppenDropDown_.getItems().setAll(currentGruppen);
        if(currentGruppen.contains(selectedGruppe)) {
            gruppenDropDown_.getSelectionModel().select(selectedGruppe);
            updateGruppenTableView(selectedGruppe);
        } else if (gruppenDropDown_.getValue() != null){
            gruppenDropDown_.setValue(null);
            gruppenSubject_.setSelectedGruppe(null);
        }
        
        gruppenDropDown_.getSelectionModel().selectedItemProperty().addListener(selectedGruppenObserver_);
    }



    @Override
    public void updateSelectedGruppe() {
        // TODO Auto-generated method stub
        
    }
}
