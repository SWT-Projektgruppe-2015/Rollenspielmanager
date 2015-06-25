package controller;

import java.util.ArrayList;
import java.util.List;

import model.Gegenstand;

public class Beute {
    
    private int geldWert_;
    private int inventarWert_;
    
    private List<Gegenstand> inventar_;
    
    public Beute() {
        geldWert_ = 0;
        inventarWert_ = 0;
        inventar_ = new ArrayList<Gegenstand>();
    }
    
    public Beute(int gesamtWert, int streuung) {
        gesamtWert += Dice.rollDice(2*streuung) - streuung;
        inventar_ = new ArrayList<Gegenstand>();
        splitGesamtWert(gesamtWert);
    }
    
    
    public void splitGesamtWert(int gesamtWert) {
        setGeldWert(Dice.rollDice(gesamtWert));
        setInventarWert(gesamtWert - geldWert_);
    }
    
    
    
    public void generateInventarBeute() {
        List<Gegenstand> sortedList = Gegenstand.getAll();
        Gegenstand.sortByKosten(sortedList);
        
        int resumingValue = inventarWert_;
        while(resumingValue > 0) {
            Gegenstand chosen = Beute.getRandomItemWithMaxValue(resumingValue, sortedList);
            resumingValue = updateResumingValue(resumingValue, chosen);
        }
    }
    
    
    
    protected static Gegenstand getRandomItemWithMaxValue(int maxValue, List<Gegenstand> sortedList) {
        int range = sortedList.size();
        while(range > 0) {
            int sample = Dice.rollDice(range) - 1;
            Gegenstand chosen = sortedList.get(sample);
            if(chosen.getKosten_() <= maxValue)
                return chosen;
            range = sample;
        }
        return null;
    }

    

    private int updateResumingValue(int resumingValue, Gegenstand chosen) { 
        if(chosen == null) {  // TODO: should be tested as well
            geldWert_ += resumingValue;
            inventarWert_ -= resumingValue;
        }
        else {  // TODO: What to do with value 0 stuff?
            if(chosen.getKosten_() == 0){
                resumingValue -= 1;
                geldWert_ += 1;
                inventarWert_ -= 1;
            }
            resumingValue -= chosen.getKosten_();
            inventar_.add(chosen);
        }
        return resumingValue;
    }
    
    
    
    
    
    public int getGeldWert() {
        return geldWert_;
    }

    public void setGeldWert(int geldWert) {
        this.geldWert_ = geldWert;
    }
    
    public List<Gegenstand> getInventarBeute() {
        return inventar_;
    }
    
    public int getInventarWert() {
        return inventarWert_;
    }

    public void setInventarWert(int inventarWert) {
        this.inventarWert_ = inventarWert;
    }
}
