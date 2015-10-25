package view.controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import org.controlsfx.control.NotificationPane;

import view.AboutTexts;
import view.tabledata.SpielerMitWaffe;
import controller.GruppenSubject;
import model.GegnerEinheit;
import model.Spieler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Startet das Programm und zeigt das Hauptmenue an.
 * 
 * @author Boris Prochnau
 *
 */
public class Hauptprogramm extends Application {
    
    public final static String UMLAUT_SMALL_AE = "\u00e4";
    public final static String UMLAUT_CAPITAL_AE = "\u00c4"; 
    public final static String UMLAUT_SMALL_UE = "\u00fc"; 
    public final static String UMLAUT_CAPITAL_UE = "\u00dc"; 
    public final static String UMLAUT_SMALL_OE = "\u00f6"; 
    public final static String UMLAUT_CAPITAL_OE = "\u00d6"; 
    public final static String SZ = "\u00df"; 
    
    private Stage primaryStage_;
    private Stage kampfStage_;
    private GruppenSubject gruppenSubject_;
    
    public void start(Stage primaryStage) {
        this.primaryStage_ = primaryStage;
        this.primaryStage_.getIcons().add(new Image("/img/Logo3_1.png"));
        this.primaryStage_.setTitle("DLVC Taverne v1.31");
        
        gruppenSubject_ = new GruppenSubject();
        
        showMainMenu();
    }



    public void showMainMenu() {
        try {
            BorderPane menuBar = loadBorderPane(AboutTexts.MAIN_MENU_TITLE, AboutTexts.ABOUT_MAIN_MENU);
            FXMLLoader loader = getLoaderForXML("/view/MainMenu.fxml");
            AnchorPane mainMenu = (AnchorPane) loader.load();

            MainMenuController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            gruppenSubject_.addGruppenObserver(controller);
            controller.setHauptProgramm(this);
            
            menuBar.setCenter(mainMenu);
            
            primaryStage_.setScene(new Scene(menuBar));
            primaryStage_.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openCharakterManager() {
        try {
            BorderPane charakterManagerBorder = loadBorderPane(AboutTexts.CHARAKTER_MANAGER_TITLE, AboutTexts.ABOUT_CHARAKTER_MANAGER);
            NotificationPane notificationPane = getNotificationPane(charakterManagerBorder);           
            FXMLLoader loader = getLoaderForXML("/view/Charaktermanager.fxml");
            Parent page = loader.load();
            
            CharaktermanagerController controller = loader.getController();            
            controller.setGruppenSubject_(gruppenSubject_);
            controller.setNotificationPane(notificationPane);
            
            charakterManagerBorder.setCenter(page);
            
            Stage newStage = createNamedStage("Zuumas Charaktermanager", notificationPane);            
            newStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openTeilnehmerauswahl() {
        try {
            FXMLLoader loader = getLoaderForXML("/view/TeilnehmerAuswahl.fxml");
            Parent page = loader.load();
            
            TeilnehmerAuswahlController controller = loader.getController();
            controller.setHauptProgramm(this);
            controller.setGruppenSubject_(gruppenSubject_);
            gruppenSubject_.addGruppenObserver(controller);
            
            kampfStage_ = createNamedStage("Anakoks Kampfsimulator - Teilnehmerauswahl", page);            
            kampfStage_.showAndWait();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    public void startKampf(List<Spieler> spieler, ObservableList<GegnerEinheit> gegnerEinheiten) {
        try {
            BorderPane pane = loadBorderPane(AboutTexts.KAMPF_TITLE, AboutTexts.ABOUT_KAMPF);
            NotificationPane notificationPane = getNotificationPane(pane);
            FXMLLoader loader = getLoaderForXML("/view/Kampfsimulator.fxml");
            Parent page = loader.load();
            
            KampfsimulatorController controller = loader.getController();
            controller.initializeAllTabs(this, spieler, gegnerEinheiten);
            controller.setNotificationPane(notificationPane);
           
            pane.setCenter(page);
            
            kampfStage_.setTitle("Anakoks Kampfsimulator");
            kampfStage_.setScene(new Scene(notificationPane));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    public void openWaffenwechsel(SpielerrundeController spielerRundeController, SpielerMitWaffe currentSpieler) {
        try {
            FXMLLoader loader = getLoaderForXML("/view/Waffenwechsel.fxml");
            Parent page = loader.load();
            
            WaffenwechselController controller = loader.getController();
            controller.initialize(spielerRundeController, currentSpieler);
            
            Stage newStage = createNamedStage("Anakoks Kampfsimulator - Waffenwechsel", page);            
            controller.setStage(newStage);
            newStage.setResizable(false);
            newStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openHaendler() {
        try {
            BorderPane border = loadBorderPane(AboutTexts.HAENDLER_TITLE, AboutTexts.ABOUT_HAENDLER);
            NotificationPane notificationPane = getNotificationPane(border);            
            FXMLLoader loader = getLoaderForXML("/view/Haendler.fxml");
            Parent page = loader.load();
            
            HaendlerController controller = loader.getController();            
            controller.setNotificationPane(notificationPane);
            
            border.setCenter(page);
            
            Stage newStage = this.createNamedStage("Giakiri, der H" + UMLAUT_SMALL_AE +"ndler", notificationPane);            
            newStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    public void openWuerfelSimulator() {
        try {
            BorderPane border = loadBorderPane(AboutTexts.WUERFELSIMULATOR_TITLE, AboutTexts.ABOUT_WUERFELSIMULATOR);
            FXMLLoader loader = getLoaderForXML("/view/Wuerfelsimulator.fxml");
            Parent page = loader.load();
            
            border.setCenter(page);
            
            Stage newStage = this.createNamedStage("Cetos W" + UMLAUT_SMALL_UE + "rfelsimulator", border);
            newStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
        

    
    private Stage createNamedStage(String name, Parent content) {
        Stage newStage = new Stage();
        newStage.setTitle(name);
        newStage.initModality(Modality.NONE);
        newStage.initOwner(primaryStage_);
        newStage.setScene(new Scene(content));
        newStage.getIcons().add(new Image("/img/Logo3_1.png"));
        
        return newStage;
    }
    
    
    
    private FXMLLoader getLoaderForXML(String pathToXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hauptprogramm.class.getResource(pathToXML));
        
        return loader;
    }
    
    
    
    private NotificationPane getNotificationPane(BorderPane border) {
        NotificationPane notificationPane = new NotificationPane(border);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        
        return notificationPane;
    }
    
    
    
    private BorderPane loadBorderPane(String windowTitle, String aboutWindow) throws IOException {
        FXMLLoader loader = getLoaderForXML("/view/MenuBar.fxml");
        BorderPane pane = loader.load();
        
        MenuBarController controller = loader.getController();
        controller.setAboutWindow(windowTitle, aboutWindow);
        
        return pane;
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
