package view.tabledata;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Spieler;
import model.Waffen;

public class SpielerMitWaffe implements Comparable<SpielerMitWaffe> {
    private Spieler spieler_;
    private Waffen currentWaffe_ = new Waffen();
    private StringProperty spielerName_ = new SimpleStringProperty("name");
    private StringProperty waffenName_ = new SimpleStringProperty("");
    
    public SpielerMitWaffe(Spieler spieler) {
        spieler_ = spieler;
        spielerName_.set(spieler.getName_());
        List<Waffen> spielerWaffen = spieler.getWaffen();
        if(spielerWaffen != null && !spielerWaffen.isEmpty()) {
            setWaffe(spielerWaffen.get(0));
        }
    }
    
    public SpielerMitWaffe(Spieler spieler, Waffen waffe) {
        spieler_ = spieler;
        spielerName_.set(spieler.getName_());
        setWaffe(waffe);
    }    
    
    public String getSpielerName_() {
        return spielerName_.get();
    }
    
    public String getWaffenName_() {
        return waffenName_.get();
    }

    public void setWaffe(Waffen waffe) {
        currentWaffe_ = waffe;
        waffenName_.set(waffe.getWaffenName_());
    }
    
    public Spieler getSpieler() {
        return spieler_;
    }
    
    public Waffen getWaffe() {
        return currentWaffe_;
    }
    
    
    
    public boolean isArmed() {
        return currentWaffe_.getID_() > 0;
    }

    @Override
    public int compareTo(SpielerMitWaffe o) {
        return getSpielerName_().compareTo(o.getSpielerName_());
    }

    public boolean hasWaffenEffekt() {
        if(currentWaffe_.hasEffekt())   {
            return true;
        }
        return false;
    }
}
