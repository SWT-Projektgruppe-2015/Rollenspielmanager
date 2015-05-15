package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Spieler;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;

public class SchadenAmSpieler {
    private Spieler spieler_;
    private StringProperty name_;
    private IntegerProperty schaden_;
    private IntegerProperty zone_;
//    private ObjectProperty<Angriff> angriff_; 
    
    public Integer getZone_() {
        return zone_.getValue();
    }


    public void setZone_(Integer zone) {
        this.zone_.setValue(zone);
    }


    SchadenAmSpieler(Spieler spieler) {
        spieler_ = spieler;
        name_ = new SimpleStringProperty(spieler_.getName_());
        schaden_ = new SimpleIntegerProperty(0);
        zone_ = new SimpleIntegerProperty(1);
    }
    
    
    public Spieler getSpieler_() {
        return spieler_;
    }
    
    
    
    public void setSpieler_(Spieler spieler) {
        this.spieler_ = spieler;
    }
    
    
    
    public String getName_() {
        return name_.getValue();
    }
    
    
    
    public void setName_(String name) {
        this.name_.set(name);
    }
    
    
    
    public Integer getSchaden_() {
        return schaden_.getValue();
    }
    
    
    
    public void setSchaden_(Integer schaden) {
        schaden_.setValue(schaden);
    }
    
    
    
    public IntegerProperty getZonenProperty_() {
        return this.zone_;
    }
    
    
    
//    public ObservableValue<ObjectProperty<Angriff>> getAngriff_() {
//        ObservableValue<ObjectProperty<Angriff>> tmp = new SimpleObjectProperty<ObjectProperty<Angriff>>();
//        return tmp;
//    }
//    
//    
//    
//    public Angriff getAngriffValue_() {
//        return this.angriff_.getValue();
//    }
}
