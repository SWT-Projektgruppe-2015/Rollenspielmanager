package view.controller;

import java.util.List;

import model.Gruppe;
import model.Spieler;
import controller.GruppenSubject;
import controller.interfaces.GruppenObserver;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;

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
    @FXML
    private Button gruppe_;
    @FXML
    private Button kampf_;
    @FXML
    private Button haendler_;
    @FXML
    private Button wuerfel_;
    @FXML
    private Label stufeLabel_;
    @FXML
    private Label erfahrungLabel_;
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    
    
    
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
        gruppenDropDown_.getItems().setAll(Gruppe.getAll());
        gruppenDropDown_.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Gruppe>() {
            @Override
            public void changed(
                    ObservableValue<? extends Gruppe> observable,
                    Gruppe oldValue, Gruppe newValue) {
                updateGruppenTableView(newValue);
            }
        });
        setButtonTextAlignment(TextAlignment.CENTER);  
        /* 
         * Um den Abstand zwischen den Zeilen in den Button-texten 
         * zu aendern die Eingabe  
         */
        if(isWindows() || isSolaris())   {
            setButtonTextLineSpacing(-13.0);
        }else if(isMac())   {
            setButtonTextLineSpacing(-13.5);
        }else if(isUnix())   {
            setButtonTextLineSpacing(-3.0);
        }
        
        gruppe_.setText("Zuuma\ndie Wirtin");
        kampf_.setText("Anakok\ndie Kriegerin");
        haendler_.setText("Giakiri\nder H" + Hauptprogramm.UMLAUT_SMALL_AE + "ndler");
        wuerfel_.setText("Ceto\nder W" + Hauptprogramm.UMLAUT_SMALL_UE + "rfelspieler");
        
        
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
    
    
    
    public static boolean isWindows()   {
        return (OS.indexOf("win") >= 0); 
    }
    
    
 
    public static boolean isMac()   {
        return (OS.indexOf("mac") >= 0); 
    }
 
    
    
    public static boolean isUnix()  {
 
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
 
    
    
    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }



    private void setButtonTextLineSpacing(double spacing) {
        gruppe_.setLineSpacing(spacing);
        kampf_.setLineSpacing(spacing);
        haendler_.setLineSpacing(spacing);
        wuerfel_.setLineSpacing(spacing);
    }



    private void setButtonTextAlignment(TextAlignment alignment) {
        gruppe_.setTextAlignment(alignment);
        kampf_.setTextAlignment(alignment);
        haendler_.setTextAlignment(alignment);
        wuerfel_.setTextAlignment(alignment);
    }

    
    
    private void updateGruppenTableView(Gruppe gruppe) {
        if(gruppe == null)
            spielerInGruppe_.getItems().clear();
        else {
            spielerInGruppe_.getItems().setAll(gruppe.getOrderedMemberList());
            int stufenSumme = gruppe.getStufenSumme();
            int stufe = Gruppe.getStufe(stufenSumme);
            int kreis = Gruppe.getKreis(stufenSumme);
            int erfahrung = Gruppe.getBenoetigteErfahrung(stufe, kreis);
            this.stufeLabel_.setText(Integer.toString(kreis) + " \\ " + Integer.toString(stufe));
            this.erfahrungLabel_.setText(Integer.toString((int) (erfahrung*0.1)));
        }
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
