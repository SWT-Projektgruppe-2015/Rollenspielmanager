package controller;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import model.Gegner;
import model.Spieler;
import view.controller.CharaktermanagerController;
import view.controller.GegnerrundeController;
import view.controller.MainMenuController;
import view.controller.TeilnehmerAuswahlController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Startet das Programm und zeigt das Hauptmenue an.
 * 
 * @author Britta Heymann
 *
 */
public class Hauptprogramm extends Application {
    
    final static String UMLAUT_SMALL_AE = "\u00e4";
    final static String UMLAUT_CAPITAL_AE = "\u00c4"; 
    final static String UMLAUT_SMALL_UE = "\u00fc"; 
    final static String UMLAUT_CAPITAL_UE = "\u00dc"; 
    final static String UMLAUT_SMALL_OE = "\u00f6"; 
    final static String UMLAUT_CAPITAL_OE = "\u00d6"; 
    final static String SZ = "\u00df"; 
    
    private Stage primaryStage_;
    private BorderPane menuBar;
    private Stage kampfStage;
    private GruppenSubject gruppenSubject_;
    
    public void start(Stage primaryStage) {
        this.primaryStage_ = primaryStage;
        this.primaryStage_.getIcons().add(new Image("file:/img/Logo3_1.png"));
        this.primaryStage_.setTitle("DLVC Taverne");
        gruppenSubject_ = new GruppenSubject();
        
        initializeMenuBar();
        
        showMainMenu();
    }
    
    
    
    public void initializeMenuBar() {
        try {
            FXMLLoader loader = getLoaderForXML("/view/MenuBar.fxml");
            menuBar = (BorderPane) loader.load();
            
            Scene scene = new Scene(menuBar);
            primaryStage_.setScene(scene);
            primaryStage_.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void showMainMenu() {
        try {
            FXMLLoader loader = getLoaderForXML("/view/MainMenu.fxml");
            AnchorPane mainMenu = (AnchorPane) loader.load();
            
            menuBar.setCenter(mainMenu);

            MainMenuController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            gruppenSubject_.addGruppenObserver(controller);
            controller.setHauptProgramm(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void openWuerfelSimulator() {
        try {
            openNewWindow("src/view/Wuerfelsimulator.fxml", "W" + UMLAUT_SMALL_UE + "rfelsimulator");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openCharakterManager() {
        try {
            FXMLLoader loader = getLoaderForXML("/view/Charaktermanager.fxml");
            Parent page = loader.load();
            CharaktermanagerController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            Stage newStage = new Stage();
            newStage.setTitle("Charaktermanager");
            newStage.initModality(Modality.NONE);
            newStage.initOwner(primaryStage_);
            newStage.setScene(new Scene(page));
            newStage.getIcons().add(new Image("file:/img/Logo3_1.png"));
            
            newStage.showAndWait();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openHaendler() {
        try {
            openNewWindow("/view/Haendler.fxml", "H" + UMLAUT_SMALL_AE +"ndler");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private FXMLLoader openNewWindow(String resourceFile, String title)
            throws IOException {
        FXMLLoader loader = getLoaderForXML(resourceFile);
        Parent page = loader.load();
        
        Stage newStage = new Stage();
        newStage.setTitle(title);
        newStage.initModality(Modality.NONE);
        newStage.initOwner(primaryStage_);
        newStage.setScene(new Scene(page));
        newStage.getIcons().add(new Image("file:/img/Logo3_1.png"));
        
        newStage.showAndWait();
        return loader;
    }
    
    private FXMLLoader getLoaderForXML(String pathToXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hauptprogramm.class.getResource(pathToXML));
        
        return loader;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage_;
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }



    public void openTeilnehmerauswahl() {
        try {
            FXMLLoader loader = getLoaderForXML("/view/TeilnehmerAuswahl.fxml");
            Parent page = loader.load();
            TeilnehmerAuswahlController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            gruppenSubject_.addGruppenObserver(controller);
            controller.setHauptProgramm(this);
            
            kampfStage = new Stage();
            kampfStage.setTitle("Kampf - Teilnehmerauswahl");
            kampfStage.initModality(Modality.NONE);
            kampfStage.initOwner(primaryStage_);
            kampfStage.setScene(new Scene(page));
            kampfStage.getIcons().add(new Image("file:/img/Logo3_1.png"));
            
            kampfStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    public void startKampf(List<Spieler> spieler, List<Gegner> gegner) {
        try {
            FXMLLoader loader = getLoaderForXML("/view/Gegnerrunde.fxml");
            Parent page = loader.load();
            GegnerrundeController controller = loader.getController();
            controller.initializeParameters(spieler, gegner);
            
            kampfStage.setTitle("Kampf");
            kampfStage.setScene(new Scene(page));
            kampfStage.getIcons().add(new Image("file:/img/Logo3_1.png"));
            kampfStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
