package model;

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
    public void generateAusruestungBeute() {
        if(ruestungIsDropped()) {
            generateRuestungsBeute();
        } else {
            generateWaffenBeute();
        }
    }
    
    
    
    private void generateWaffenBeute() {
        // TODO Auto-generated method stub
        
    }


    private void generateRuestungsBeute() {
        // TODO Auto-generated method stub
        
    }


    private boolean ruestungIsDropped() {
        int wert = Dice.rollDice(5);
        return wert <= 3;
    }
}
