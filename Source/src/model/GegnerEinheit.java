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
    
    
    public GegnerEinheit(GegnerTyp typ, int ID) {
        typ_ = typ;
        name_ = typ.getName_();
        level_ = typ.getLevel_();
        kreis_ = typ.getKreis_();
        erfahrung_ = typ.getErfahrung_();
        staerke_ = typ.getStaerke_();
        geschick_ = typ.getGeschick_();
        maxLebenspunkte_ = typ.getLebenspunkte_();
        lebenspunkte_ = maxLebenspunkte_;
        schaden_ = typ.getSchaden_();
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
        return maxLebenspunkte_;
    }

    

    public void setLebenspunkte_(int lebenspunkte_) {
        this.lebenspunkte_ = lebenspunkte_;
    }

    
    
    public static final List<GegnerEinheit> createEinheiten(GegnerTyp typ, int anzahl) {
        List<GegnerEinheit> gegner = new ArrayList<GegnerEinheit>();
        for(int i = 0; i < anzahl; ++i)
            gegner.add(new GegnerEinheit(typ, i));
        return gegner;
    }

    
    
    public int getDamage() {
        return this.schaden_;
    }

    
    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }


    
    public void setAusruestung_(Ausruestung ausruestung) {
        this.ausruestung_ = ausruestung;
    }


    
    public static boolean detailsAreValid
        (int level, int kreis, int geschick, int staerke, int erfahrung) {
       
        if(level > Charakter.MAX_LEVEL || level < 0)
            return false;
        
        if(kreis > Charakter.MAX_KREIS || kreis < 0)
            return false;
        
        if(geschick < 1)
            return false;
        
        if(staerke < 1)
            return false;
        
        if(erfahrung < 1)
            return false;
        
        return true;
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
