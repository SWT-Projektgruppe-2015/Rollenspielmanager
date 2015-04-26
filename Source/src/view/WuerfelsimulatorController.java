package view;

import controller.DiceRoller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WuerfelsimulatorController {
	@FXML
	private Label rollResult_;
	
	
    
    @FXML
    private void onW4ButtonClick() {
    	showResult(DiceRoller.RollW4());
    }
    
    @FXML
    private void onW6ButtonClick() {
    	showResult(DiceRoller.RollW6());
    }
    
    @FXML
    private void onW8ButtonClick() {
    	showResult(DiceRoller.RollW8());
    }
    
    @FXML
    private void onW10ButtonClick() {
    	showResult(DiceRoller.RollW10());
    }
    
    @FXML
    private void onW12ButtonClick() {
    	showResult(DiceRoller.RollW12());
    }
    
    @FXML
    private void onW20ButtonClick() {
    	showResult(DiceRoller.RollW20());
    }
    
    @FXML
    private void onW30ButtonClick() {
    	showResult(DiceRoller.RollW30());
    }
    
    private void showResult(int result) {
    	rollResult_.setText(Integer.toString(result));
    }
}
