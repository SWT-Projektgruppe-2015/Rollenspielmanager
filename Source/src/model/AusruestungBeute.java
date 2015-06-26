package model;

import java.util.List;

import controller.Dice;

public class AusruestungBeute extends Beute {

    private Gegenstand ruestungsTeil_;
    private int gesamtMalus_;
    
    
    public AusruestungBeute(int malus, int streuung) {
        gesamtMalus_ = (int)generateGaussianValue(malus, streuung);
    }
    
    public AusruestungBeute() {
        gesamtMalus_ = 0;
    }
    
    
   //  0.5 Harnisch + 1/3 * 0.5 ...
    public Gegenstand generateAusruestungBeute(GegnerEinheit gegner) {
        if(ruestungIsDropped()) {
            ruestungsTeil_ = generateRuestungsBeute(gegner);
        } else {
            ruestungsTeil_ = generateWaffenBeute(gegner.getSchaden_());
        }
        return ruestungsTeil_;
    }
    
    
    
    protected Gegenstand generateWaffenBeute(int waffenSchaden) {
        List<Gegenstand> sortedList = Gegenstand.getAllWaffen();
        Gegenstand.sortByValue(sortedList);
        return getItemWithApproximateValue((int)(waffenSchaden*(1.- gesamtMalus_/100.)), sortedList);
    }


    private Gegenstand generateRuestungsBeute(GegnerEinheit gegner) {
        // TODO Auto-generated method stub
        return null;
    }


    private boolean ruestungIsDropped() {
        int wert = Dice.rollDice(5);
        return wert <= 3;
    }
    
    
    
    public int getGesamtMalus() {
        return gesamtMalus_;
    }
}
