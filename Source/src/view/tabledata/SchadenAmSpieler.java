package view.tabledata;

import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import javafx.beans.value.ObservableObjectValue;
//import javafx.beans.value.ObservableValue;

import model.Spieler;



public class SchadenAmSpieler {
    private Spieler spieler_;
    private StringProperty name_;
    private IntegerProperty schaden_;
    private StringProperty zone_;

    
    
    public StringProperty getNameProperty() {
        return name_;
    }
    
    
    
    public IntegerProperty getSchadenProperty() {
        return schaden_;
    }
    
    
    
    public StringProperty getZoneProperty() {
        return zone_;
    }
    
    
    
    public String getZone_() {
        return zone_.getValue();
    }


    
    public void setZone_(Integer wuerfelErgebnis) {
        if(wuerfelErgebnis < 4) {
            zone_.setValue("Daneben");
        } 
        else if(wuerfelErgebnis < 15) {
            zone_.setValue("Ruestung");
        }
        else if(wuerfelErgebnis < 25) {
            zone_.setValue("Helm");
        }
        else if(wuerfelErgebnis < 35) {
            zone_.setValue("Direkt");
        }
        else
            zone_.setValue("Kritisch!");
    }


    
    public SchadenAmSpieler(Spieler spieler) {
        spieler_ = spieler;
        setName_Property(new SimpleStringProperty(spieler_.getName_()));
        schaden_ = new SimpleIntegerProperty(0);
        zone_ = new SimpleStringProperty("");
    }
    
    
    
    public Spieler getSpieler_() {
        return spieler_;
    }
    
    
    
    public void setSpieler_(Spieler spieler) {
        this.spieler_ = spieler;
    }
    
    
    
    public String getName_() {
        return getName_Property().getValue();
    }
    
    
    
    public void setName_(String name) {
        this.getName_Property().set(name);
    }
    
    
    
    public Integer getSchaden_() {
        return schaden_.getValue();
    }
    
    
    
    public void setSchaden_(Integer schaden) {
        schaden_.setValue(schaden);
    }
    
    
    
    public StringProperty getZonenProperty_() {
        return zone_;
    }


    
    /**
     * @return the name_
     */
    public StringProperty getName_Property() {
        return name_;
    }


    
    /**
     * @param name_ the name_ to set
     */
    public void setName_Property(StringProperty name_) {
        this.name_ = name_;
    }
}
