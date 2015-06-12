package view.controller;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.management.Notification;

import org.controlsfx.control.NotificationPane;

import sun.nio.ch.ThreadPool;
import view.tabledata.SpielerMitWaffe;
import controller.GruppenSubject;
import model.GegnerEinheit;
import model.Spieler;
import javafx.event.EventHandler;
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
    private BorderPane menuBar_;
    private Stage newStage;
    private GruppenSubject gruppenSubject_;
    private NotificationPane notificationPane_;
    
    public void start(Stage primaryStage) {
        this.primaryStage_ = primaryStage;
        this.primaryStage_.getIcons().add(new Image("/img/Logo3_1.png"));
        this.primaryStage_.setTitle("DLVC Taverne");
        gruppenSubject_ = new GruppenSubject();
        
        initializeMenuBar();
        
        showMainMenu();
    }
    
    
    
    public void initializeMenuBar() {
        try {
            menuBar_ = loadBorderPane();           
            notificationPane_ = getNotificationPane(menuBar_);
            
            primaryStage_.setScene(new Scene(notificationPane_));
            primaryStage_.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    private NotificationPane getNotificationPane(BorderPane border) {
        NotificationPane notificationPane = new NotificationPane(border);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        
        return notificationPane;
    }



    private BorderPane loadBorderPane() throws IOException {
        FXMLLoader loader = getLoaderForXML("/view/MenuBar.fxml");
        return (BorderPane) loader.load();
    }
    
    
    
    public void showMainMenu() {
        try {
            FXMLLoader loader = getLoaderForXML("/view/MainMenu.fxml");
            AnchorPane mainMenu = (AnchorPane) loader.load();
            
            menuBar_.setCenter(mainMenu);

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
        /*try {
            FXMLLoader loader = getLoaderForXML("/view/Wuerfelsimulator.fxml");
            Parent page = loader.load();
            
            
            
            Stage newStage = new Stage();
            newStage.setTitle("W" + UMLAUT_SMALL_UE + "rfelsimulator"));
            newStage.initModality(Modality.NONE);
            newStage.initOwner(primaryStage_);
            newStage.setScene(new Scene(page));
            newStage.getIcons().add(new Image("/img/Logo3_1.png"));
            
            newStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
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
            newStage.getIcons().add(new Image("/img/Logo3_1.png"));
            
            newStage.showAndWait();
            
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
            
            Stage newStage = new Stage();
            newStage.setTitle("Kampf - Waffenwechsel");
            newStage.initModality(Modality.NONE);
            newStage.initOwner(primaryStage_);
            newStage.setScene(new Scene(page));
            newStage.getIcons().add(new Image("/img/Logo3_1.png"));
            
            controller.setStage(newStage);
            
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
        newStage.getIcons().add(new Image("/img/Logo3_1.png"));
        
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
            BorderPane border = this.loadBorderPane();
            NotificationPane notificationPane = this.getNotificationPane(border);
            
            FXMLLoader loader = getLoaderForXML("/view/TeilnehmerAuswahl.fxml");
            Parent page = loader.load();
            TeilnehmerAuswahlController controller = loader.getController();
            controller.setGruppenSubject_(gruppenSubject_);
            controller.setNotificationPane(notificationPane);
            gruppenSubject_.addGruppenObserver(controller);
            controller.setHauptProgramm(this);
            
            border.setCenter(page);
            
            newStage = new Stage();
            newStage.setTitle("Kampf - Teilnehmerauswahl");
            newStage.initModality(Modality.NONE);
            newStage.initOwner(primaryStage_);
            newStage.setScene(new Scene(notificationPane));
            newStage.getIcons().add(new Image("/img/Logo3_1.png"));
            
            newStage.showAndWait();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    public void startKampf(List<Spieler> spieler, List<GegnerEinheit> gegnerEinheiten) {
        try {
            FXMLLoader loader = getLoaderForXML("/view/Kampfsimulator.fxml");
            Parent page = loader.load();
            KampfsimulatorController controller = loader.getController();
            controller.initializeAllTabs(this, spieler, gegnerEinheiten);
            
            newStage.setTitle("Kampf");
            newStage.setScene(new Scene(page));
            newStage.getIcons().add(new Image("/img/Logo3_1.png"));
            newStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    public void createNotification(NotificationPane notification, String text) {
        notification.setText(text);        
        notification.show();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(
             new Runnable() { @Override public void run() { 
                 notification.hide(); try {
                    this.finalize();
                }
                catch (Throwable e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } } },
             3, TimeUnit.SECONDS);
        scheduler.shutdownNow();
    }
    
}
