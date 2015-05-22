package model;

import java.util.List;

public abstract class Charakter {
    public static final int MAX_KREIS = 4;
    public static final int MAX_LEVEL = 12;
    
    public static final int LOWERBOUND_DIREKT2 = 35;
    public static final int LOWERBOUND_DIREKT = 25;
    public static final int LOWERBOUND_HELM = 15;
    public static final int LOWERBOUND_RUESTUNG = 4;
    
    public abstract String getName_();
    abstract Ausruestung getAusruestung_();
    abstract void setAusruestung_(Ausruestung ausruestung);
    
    
    
    /**
     * Falls keine Ausruestung vorhanden ist, wird eine neue erstellt und mit
     * dem Charakter verbunden.
     * 
     * @return Ausruestung des Charakter, niemals null.
     */
    protected Ausruestung getAusruestungForModification() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null) {
            ausruestung = new Ausruestung();
            setAusruestung_(ausruestung);
        }
        return ausruestung;
    }
    
    
    
    public int getDefR() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 1;

        return getAusruestung_().getDefR_();
    }

    
    
    public void setDefR(int def) {
        Ausruestung ausruestung = getAusruestungForModification();

        if (def > 0)
            ausruestung.setDefR_(def);
    }
    
    
    public int getDefH() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 1;
        
        return getAusruestung_().getDefH_();
    }
    
    

    public void setDefH(int def) {
        Ausruestung ausruestung = getAusruestungForModification();

        if (def > 0)
            ausruestung.setDefH_(def);
    }

    
    
    public int getDefS() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return 0;
        
        return getAusruestung_().getDefS_();
    }
    
    
    
    public void setDefS(int def) {
        Ausruestung ausruestung = getAusruestungForModification();
        
        if (def >= 0)
            ausruestung.setDefS_(def);
    }
    
    
    
    public static boolean ausruestungIsValid(int newDefR, int newDefH, int newDefS) {
        return newDefR > 0 && newDefH > 0 && newDefS >= 0;
    }
    
    
    
    public int getLebensverlust(int schaden, int wuerfelErgebnis) {
        Ausruestung ausruestung = this.getAusruestung_();
        schaden -= ausruestung.getDefS_();
        if(wuerfelErgebnis < 4 || schaden <= 0) {
            return 0;
        } 
        else if(wuerfelErgebnis < 15) {
            return schaden/ausruestung.getDefR_();
        }
        else if(wuerfelErgebnis < 25) {
            return schaden/ausruestung.getDefH_();
        }
        else if(wuerfelErgebnis < 35) {
            return schaden;
        }
        else
            return (int) (schaden*1.1);
    }
    
}