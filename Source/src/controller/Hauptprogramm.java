package controller;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import view.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Startet das Programm und zeigt das Hauptmenue an.
 * @author Britta Heymann
 *
 */
public class Hauptprogramm extends Application {

	private Stage primaryStage;
	private BorderPane menuBar;

	public void start(Stage primaryStage) {
	   this.primaryStage = primaryStage;
	   this.primaryStage.setTitle("Rollenspielmanager");

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
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	public void showMainMenu() {
	    try {
	        FXMLLoader loader = getLoaderForXML("../view/MainMenu.fxml");
	        AnchorPane mainMenu = (AnchorPane) loader.load();

            menuBar.setCenter(mainMenu);
            
            MainMenuController controller = loader.getController();
            controller.setHauptProgramm(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean openWuerfelSimulator() {
		try {
	        openNewWindow("../view/Wuerfelsimulator.fxml", "Würfelsimulator");
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	private void openNewWindow(String resourceFile, String title) throws IOException {
		AnchorPane page = (AnchorPane) getLoaderForXML(resourceFile).load();
		
		Stage newStage = new Stage();
		newStage.setTitle(title);
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(primaryStage);
		newStage.setScene(new Scene(page));
		
		newStage.showAndWait();
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
}
