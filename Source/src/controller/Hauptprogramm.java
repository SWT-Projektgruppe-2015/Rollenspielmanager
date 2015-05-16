package controller;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import model.Gegner;
import model.Spieler;
import view.CharaktermanagerController;
import view.GegnerrundeController;
import view.MainMenuController;
import view.TeilnehmerAuswahlController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Startet das Programm und zeigt das Hauptmenue an.
 * 
 * @author Britta Heymann
 *
 */
public class Hauptprogramm extends Application {
    
    private Stage primaryStage;
    private BorderPane menuBar;
    private Stage kampfStage;
    private GruppenSubject gruppenSubject_;
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Rollenspielmanager");
        gruppenSubject_ = new GruppenSubject();
        
        initializeMenuBar();
        
        showMainMenu();
    }
    
    
    
    public void initializeMenuBar() {
        try {
            FXMLLoader loader = getLoaderForXML("../view/MenuBar.fxml");
            menuBar = (BorderPane) loader.load();
            
            Scene scene = new Scene(menuBar);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void showMainMenu() {
        try {
            FXMLLoader loader = getLoaderForXML("../view/MainMenu.fxml");
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
            openNewWindow("../view/Wuerfelsimulator.fxml", "Würfelsimulator");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openCharakterManager() {
        try {
            FXMLLoader loader = getLoaderForXML("../view/Charaktermanager.fxml");
            Parent page = loader.load();
            CharaktermanagerController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            Stage newStage = new Stage();
            newStage.setTitle("Charaktermanager");
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(primaryStage);
            newStage.setScene(new Scene(page));
            
            newStage.showAndWait();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void openHaendler() {
        try {
            openNewWindow("../view/Haendler.fxml", "Händler");
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
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(primaryStage);
        newStage.setScene(new Scene(page));
        
        newStage.showAndWait();
        return loader;
    }
    
    private FXMLLoader getLoaderForXML(String pathToXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hauptprogramm.class.getResource(pathToXML));
        
        return loader;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }



    public void openTeilnehmerauswahl() {
        try {
            FXMLLoader loader = getLoaderForXML("../view/TeilnehmerAuswahl.fxml");
            Parent page = loader.load();
            TeilnehmerAuswahlController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            gruppenSubject_.addGruppenObserver(controller);
            controller.setHauptProgramm(this);
            
            kampfStage = new Stage();
            kampfStage.setTitle("Kampf - Teilnehmerauswahl");
            kampfStage.initModality(Modality.WINDOW_MODAL);
            kampfStage.initOwner(primaryStage);
            kampfStage.setScene(new Scene(page));
            
            kampfStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    public void startKampf(List<Spieler> spieler, List<Gegner> gegner) {
        try {
            FXMLLoader loader = getLoaderForXML("../view/Gegnerrunde.fxml");
            Parent page = loader.load();
            GegnerrundeController controller = loader.getController();
            controller.initialize(spieler, gegner);
            
            kampfStage.setTitle("Kampf");
            kampfStage.setScene(new Scene(page));
            kampfStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
