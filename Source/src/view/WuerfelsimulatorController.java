package view;

import controller.Dice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WuerfelsimulatorController {
    @FXML
    private Label rollResult_;

    
    
    @FXML
    private void onW2ButtonClick() {
        showResult(Dice.RollW2());
    }

    
    
    @FXML
    private void onW3ButtonClick() {
        showResult(Dice.RollW3());
    }

    
    
    @FXML
    private void onW4ButtonClick() {
        showResult(Dice.RollW4());
    }

    
    
    @FXML
    private void onW6ButtonClick() {
        showResult(Dice.RollW6());
    }

    
    
    @FXML
    private void onW8ButtonClick() {
        showResult(Dice.RollW8());
    }

    
    
    @FXML
    private void onW10ButtonClick() {
        showResult(Dice.RollW10());
    }

    
    
    @FXML
    private void onW12ButtonClick() {
        showResult(Dice.RollW12());
    }

    
    
    @FXML
    private void onW20ButtonClick() {
        showResult(Dice.RollW20());
    }

    
    
    @FXML
    private void onW30ButtonClick() {
        showResult(Dice.RollW30());
    }

    
    
    private void showResult(int result) {
        rollResult_.setText(Integer.toString(result));
    }
}
