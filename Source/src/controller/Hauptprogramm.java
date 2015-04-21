package controller;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Hauptprogramm.class.getResource("../view/MenuBar.fxml"));
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
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Hauptprogramm.class.getResource("../view/MainMenu.fxml"));
	        AnchorPane mainMenu = (AnchorPane) loader.load();

            menuBar.setCenter(mainMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
	public Stage getPrimaryStage() {
	    return primaryStage;
	}

	
	
	public static void main(String[] args) {
	    launch(args);
	}
}
