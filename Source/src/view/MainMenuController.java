package view;

import model.Gegner;
import model.Gruppe;
import model.Spieler;
import controller.Hauptprogramm;
import controller.manipulators.GruppenManipulator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainMenuController {
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
        
        kreis_.setCellValueFactory(new PropertyValueFactory<Spieler, Integer>("kreis_"));
        level_.setCellValueFactory(new PropertyValueFactory<Spieler, Integer> ("level_"));
        name_.setCellValueFactory(new PropertyValueFactory<Spieler, String>("name_"));
    }

    
    
    private void updateGruppenTableView(Gruppe newValue) {
        spielerInGruppe_.getItems().setAll(newValue.getAllSpieler());
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
}
