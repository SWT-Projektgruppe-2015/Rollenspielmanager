package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import model.Spieler;

public class SchadenAmSpieler {
    private Spieler spieler_;
    private StringProperty name_;
    private IntegerProperty schaden_; 
    
    
    SchadenAmSpieler(Spieler spieler) {
        spieler_ = spieler;
        name_.set(spieler_.getName_());
        schaden_.set(0);
    }
    
    
    
    public Spieler getSpieler_() {
        return spieler_;
    }
    
    
    
    public void setSpieler_(Spieler spieler_) {
        this.spieler_ = spieler_;
    }
    
    
    
    public String getNamen_() {
        return name_.getValue();
    }
    
    
    
    public void setNamen_(String namen_) {
        this.name_.set(namen_);
    }
    
    
    
    public Integer getSchaden_() {
        return schaden_.getValue();
    }
    
    
    
    public void setSchaden_(Integer schaden_) {
        this.schaden_.set(schaden_);
    }
}
