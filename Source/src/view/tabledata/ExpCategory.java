package view.tabledata;

import model.Spieler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExpCategory {
    private StringProperty name_;

    private IntegerProperty exp_;
    
    public ExpCategory(Spieler spieler, int exp) {
        name_ = new SimpleStringProperty();
        exp_ = new SimpleIntegerProperty();
        if(spieler == null) {
            name_.setValue("normal");
            exp_.setValue(exp);
        }
        else{
            name_.setValue(spieler.getName_());
            exp_.setValue(exp * spieler.getExpFactor());
        }
    }

    public String getName_() {
        return name_.get();
    }
    
    public Integer getExp_() {
        return exp_.get();
    }
    
}
