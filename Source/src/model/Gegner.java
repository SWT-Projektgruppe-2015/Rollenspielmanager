package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import controller.AusruestungsManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "GEGNER")

public class Gegner extends Charakter implements DBObject {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(30) NOT NULL default 'Gegner Nr. 420'")
    private String name_;
    @Column(name = "KREIS", columnDefinition = "INTEGER NOT NULL default '1' check(KREIS >= 1 and KREIS<=4)")
    private int kreis_;
    @Column(name = "LEVEL", columnDefinition = "INTEGER NOT NULL default '0' check(LEVEL >= 0 and LEVEL<=12)")
    private int level_;
    @Column(name = "ERFAHRUNG", columnDefinition = "INTEGER NOT NULL default '1' check(ERFAHRUNG >= 1)")
    private int erfahrung_;
    @Column(name = "STAERKE", columnDefinition = "INTEGER NOT NULL default '1' check(STAERKE >= 1)")
    private int staerke_;
    @Column(name = "GESCHICK", columnDefinition = "INTEGER NOT NULL default '1' check(GESCHICK >= 1)")
    private int geschick_;
    @Column(name = "LEBENSPUNKTE", columnDefinition = "INTEGER NOT NULL default '1' CHECK(LEBENSPUNKTE >= 0)")
    private int lebenspunkte_;
    @ManyToOne(optional = false)
    @JoinColumn(name = "BEUTETYP_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Beute beuteTyp;
    @OneToOne(optional = false)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Ausruestung ausruestung_;

    
    public Gegner() {
        name_ = "Gegner Nr. 460";
        kreis_ = 1;
        erfahrung_ = 1;
        staerke_ = 1;
        geschick_ = 1;
        lebenspunkte_ = 25;
        
        ausruestung_ = new Ausruestung();
        Waffen defaultWaffe = new Waffen();
        defaultWaffe.setWaffenName_("Default Gegner Waffe");
        defaultWaffe.setWaffenSchaden_(0);
        ausruestung_.addWaffe(defaultWaffe);
        
    }
    
    
    @PrePersist
    public void onCreate() {
        if (name_ == null) {
            name_ = "Gegner Nr. 460";
        }
        if (kreis_ == 0) {
            kreis_ = 1;
        }
        if (erfahrung_ == 0) {
            erfahrung_ = 1;
        }
        if (staerke_ == 0) {
            staerke_ = 1;
        }
        if (geschick_ == 0) {
            geschick_ = 1;
        }
        if (ausruestung_ == null) {
            ausruestung_ = new Ausruestung();
            Waffen defaultWaffe = new Waffen();
            defaultWaffe.setWaffenName_("Default Gegner Waffe");
            defaultWaffe.setWaffenSchaden_(0);
            ausruestung_.addWaffe(defaultWaffe);
            AusruestungsManipulator.getInstance().add(ausruestung_);
        }
    }
    
    
    public String toString() {
    	return name_;
    }

    
    
    public int getID_() {
        return ID_;
    }


    public void setID_(int iD_) {
        ID_ = iD_;
    }


    public String getName_() {
        return name_;
    }

    
    
    public void setName_(String name_) {
        this.name_ = name_;
    }

    public int getKreis_() {
        return kreis_;
    }

    public void setKreis_(int kreis_) {
        this.kreis_ = kreis_;
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

    public void setLebenspunkte_(int lebenspunkte_) {
        this.lebenspunkte_ = lebenspunkte_;
    }

    public static List<Gegner> getAllGegner() {
        // TODO:: Hier sollte der DB Manipulator verwendet werden. 
        return new ArrayList<Gegner>();
    }

    public int getDamage() {
        // TODO:: Nutze den DB Manipulator um den Waffenschaden der Ausruestung zu bestimmen.
//        List<Waffen> waffen = this.getAusruestung_().getWaffen();
//        return waffen.get(0).getWaffenSchaden_();
        return 0;
    }


	@Override
	Ausruestung getAusruestung_() {
		return ausruestung_;
	}


	@Override
	void setAusruestung_(Ausruestung ausruestung) {
		ausruestung_ = ausruestung;
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


    public void addToDB() {
        // TODO Auto-generated method stub
        
    }


    public void setDamage(int newDamage) {
        // TODO Mit DB Manipulatoren Waffe aendern.       
    }


    public void deleteFromDB() {
        // TODO Auto-generated method stub
        
    }
}
