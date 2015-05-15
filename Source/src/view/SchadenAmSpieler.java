package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Spieler;

public class SchadenAmSpieler {
    private Spieler spieler_;
    private StringProperty name_;
    private IntegerProperty schaden_; 
    
    SchadenAmSpieler(Spieler spieler) {
        spieler_ = spieler;
        name_ = new SimpleStringProperty(spieler_.getName_());
        schaden_ = new SimpleIntegerProperty(0);
    }
    
    
    
    public Spieler getSpieler_() {
        return spieler_;
    }
    
    
    
    public void setSpieler_(Spieler spieler_) {
        this.spieler_ = spieler_;
    }
    
    
    
    public String getName_() {
        return name_.getValue();
    }
    
    
    
    public void setName_(String namen_) {
        this.name_.set(namen_);
    }
    
    
    
    public Integer getSchaden_() {
        return schaden_.getValue();
    }
    
    
    
    public void setSchaden_(Integer schaden_) {
        this.schaden_.set(schaden_);
    }
}
