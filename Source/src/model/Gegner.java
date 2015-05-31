package model;

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

import controller.manipulators.GegnerManipulator;
import controller.manipulators.WaffenManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "GEGNER")

public class Gegner extends Charakter implements DBObject {
    private static GegnerManipulator dbManipulator_ = GegnerManipulator.getInstance();

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
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BEUTETYP_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Beute beuteTyp_;
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Ausruestung ausruestung_;

    
    
    public Gegner() {
        name_ = "Default";
        level_ = 0;
        kreis_ = 1;
        erfahrung_ = 1;
        staerke_ = 1;
        geschick_ = 1;
        lebenspunkte_ = 25;
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
        }
        if (getBeute_() == null) {
            beuteTyp_ = new Beute();
        }
    }
    
    
    
    private void updateInDB() {
        if(getID_() != 0)
            dbManipulator_.update(this);
    }
    
    public Beute getBeute_() {
        return beuteTyp_;
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
        if(name_ != this.name_) {
            this.name_ = name_;
            updateInDB();
        }
    }

    

    public int getKreis_() {
        return kreis_;
    }

    

    public void setKreis_(int kreis_) {
        if(kreis_ != this.kreis_) {
            this.kreis_ = kreis_;
            updateInDB();
        }
    }

    

    public int getLevel_() {
        return level_;
    }

    

    public void setLevel_(int level_) {
        if(level_ != this.level_) {
            this.level_ = level_;
            updateInDB();
        }
    }

    

    public int getErfahrung_() {
        return erfahrung_;
    }

    

    public void setErfahrung_(int erfahrung_) {
        if(erfahrung_ != this.erfahrung_) {
            this.erfahrung_ = erfahrung_;
            updateInDB();
        }
    }

    

    public int getStaerke_() {
        return staerke_;
    }

    

    public void setStaerke_(int staerke_) {
        if(staerke_ != this.staerke_) {
            this.staerke_ = staerke_;
            updateInDB();
        }
    }

    

    public int getGeschick_() {
        return geschick_;
    }

    

    public void setGeschick_(int geschick_) {
        if(geschick_ != this.geschick_) {
            this.geschick_ = geschick_;
            updateInDB();
        }
    }

    

    public int getLebenspunkte_() {
        return lebenspunkte_;
    }

    

    public void setLebenspunkte_(int lebenspunkte_) {
        if (lebenspunkte_ != this.lebenspunkte_) {
            this.lebenspunkte_ = lebenspunkte_;
            updateInDB();
        }
    }

    

    public static List<Gegner> getAll() {
        List<Gegner> allGegner = dbManipulator_.getAll();
        allGegner.sort(null);
        
        return allGegner;
    }

    

    public int getDamage() {
        if(getAusruestung_() == null)
            return 0;
        
        List<Waffen> waffen = WaffenManipulator.getInstance().getWaffenInAusruestung(getAusruestung_());
        if(waffen.isEmpty())
            return 0;
        
        return waffen.get(0).getWaffenSchaden_();
    }

    

	@Override
	public Ausruestung getAusruestung_() {
		return ausruestung_;
	}


	
	@Override
	public void setAusruestung_(Ausruestung ausruestung) {
	    if (ausruestung != ausruestung_) {
            boolean gegnerInDbButAusruestungIsNot = getID_() != 0
                    && ausruestung.getID_() == 0;
            if (gegnerInDbButAusruestungIsNot)
                ausruestung.addToDB();
            ausruestung_ = ausruestung;
        }
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
        dbManipulator_.add(this);
    }

    


    public void setDamage(int newDamage) {
        if(getAusruestung_() == null)
            return;
        
        List<Waffen> waffen = WaffenManipulator.getInstance().getWaffenInAusruestung(getAusruestung_());
        if(waffen.isEmpty()) {
            Waffen waffe = new Waffen();
            getAusruestung_().addWaffe(waffe);
            waffe.setWaffenSchaden_(newDamage);
        }
        else {
            waffen.get(0).setWaffenSchaden_(newDamage); 
        }
    }

    
    

    public void deleteFromDB() {
        dbManipulator_.delete(this);
    }
}
