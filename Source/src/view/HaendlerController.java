package view;

import model.EinfacherGegenstand;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HaendlerController {
    @FXML
    private TextField searchTextField_;
    @FXML
    private ListView<EinfacherGegenstand> gegenstandListView_;
    
    @FXML
    private TextField gegenstandNameTextField_;
    @FXML
    private TextField gegenstandCostTextField_;
    @FXML
    private TextArea gegenstandDescriptionTextField_;
}
