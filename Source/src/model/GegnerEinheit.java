package model;

import java.util.ArrayList;
import java.util.List;

public class GegnerEinheit extends Charakter {
    private int ID_;
    private String name_;
    private int kreis_;
    private int level_;
    private int erfahrung_;
    private int staerke_;
    private int geschick_;
    private int maxLebenspunkte_;
    private int lebenspunkte_;
    private int schaden_;
    private Beute beuteTyp_;
    private Ausruestung ausruestung_;
    private final GegnerTyp typ_;
    
    
    private GegnerEinheit(GegnerTyp typ, int ID) {
        typ_ = typ;
        name_ = typ.getName_() + " " + Integer.toString(ID);
        level_ = typ.getLevel_();
        kreis_ = typ.getKreis_();
        erfahrung_ = typ.getErfahrung_();
        staerke_ = typ.getStaerke_();
        geschick_ = typ.getGeschick_();
        maxLebenspunkte_ = typ.getMaxLebenspunkte_();
        lebenspunkte_ = maxLebenspunkte_;
        schaden_ = typ.getSchaden_();
        ausruestung_ = typ.getAusruestung_();
        beuteTyp_ = typ.getBeuteTyp_();
        ID_ = ID;
    }
    
    
    
    public int getID_() {
        return ID_;
    }

    

    public void setID_(int iD_) {
        ID_ = iD_;
    }


    
    public Beute getBeute_() {
        return beuteTyp_;
    }

    

    public String toString() {
        return name_;
    }

    
    
    public String getName_() {
        return name_;
    }
    
    
    
    public void setName_(String name) {
        this.name_ = name;
    }

    

    public int getKreis_() {
        return kreis_;
    }

    

    public void setKreis_(int kreis) {
        this.kreis_ = kreis;
    }

    

    public int getLevel_() {
        return level_;
    }

    

    public void setLevel_(int level_) {
        this.level_ = level_;
    }

    

    public int getErfahrung_() {
        return erfahrung_;
    }

    

    public void setErfahrung_(int erfahrung_) {
        this.erfahrung_ = erfahrung_;
    }

    

    public int getStaerke_() {
        return staerke_;
    }

    

    public void setStaerke_(int staerke_) {
        this.staerke_ = staerke_;
    }

    

    public int getGeschick_() {
        return geschick_;
    }

    

    public void setGeschick_(int geschick_) {
        this.geschick_ = geschick_;
    }

    

    public int getLebenspunkte_() {
        return lebenspunkte_;
    }

    

    public int getMaxLebenspunkte_() {
        return maxLebenspunkte_;
    }



    public void setMaxLebenspunkte_(int maxLebenspunkte_) {
        this.maxLebenspunkte_ = maxLebenspunkte_;
    }



    public int getSchaden_() {
        return schaden_;
    }



    public void setSchaden_(int schaden_) {
        this.schaden_ = schaden_;
    }



    public void setLebenspunkte_(int lebenspunkte_) {
        this.lebenspunkte_ = lebenspunkte_;
    }

    
    
    public static final List<GegnerEinheit> createEinheiten(GegnerTyp typ, int anzahl) {
        List<GegnerEinheit> gegner = new ArrayList<GegnerEinheit>();
        for(int id = 1; id <= anzahl; ++id)
            gegner.add(new GegnerEinheit(typ, id));
        return gegner;
    }

    
    
    public int getSchaden() {
        return this.schaden_;
    }

    
    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }


    
    public void setAusruestung_(Ausruestung ausruestung) {
        this.ausruestung_ = ausruestung;
    }


    
    public void setDamage(int schaden) {
        this.schaden_ = schaden;
    }
    
    
    
    public int compareTo(GegnerEinheit other) {
        if(this.ID_ == other.getID_())
            return super.compareTo(other);
        else if(this.ID_ < other.getID_())
            return -1;
        else
            return 1;
    }
}
