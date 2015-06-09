package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import controller.manipulators.WaffenManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "WAFFEN")
public class Waffen implements DBObject, Comparable<Waffen> {
    private static WaffenManipulator dbManipulator_ = WaffenManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "EFFEKT_TYP")
    private int effektTyp_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(30) NOT NULL default 'Deus Ex Machina'")
    private String waffenName_;
    @Column(name = "SCHADEN", columnDefinition = "INTEGER NOT NULL default '0' check(SCHADEN >= 0)")
    private int waffenSchaden_;
    @ManyToOne(optional = false)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    private Ausruestung ausruestung_;

    public Waffen() {
        waffenSchaden_ = 0;
        ausruestung_ = new Ausruestung();
    }
    
    
    
    public Waffen(Ausruestung ausruestung) {
        waffenName_ = "Default GegnerTyp Waffe";
        waffenSchaden_ = 0;    
        ausruestung_ = ausruestung;
    }



    private void updateInDB() {
        if(getID_() != 0)
            dbManipulator_.add(this);
    }
    
    
    @Override
    public String toString() {
        return waffenName_;
    }
    
    
    
    @PrePersist
    public void onCreate() {
        if (waffenName_ == null) {
            waffenName_ = "Deus Ex Machina";
        }
    }

    public int getEffektTyp_() {
        return effektTyp_;
    }

    public void setEffektTyp_(int effektTyp_) {
        if(this.effektTyp_ != effektTyp_) {
            this.effektTyp_ = effektTyp_;
            updateInDB();
        }
    }

    public String getWaffenName_() {
        return waffenName_;
    }

    public void setWaffenName_(String waffenName_) {
        if(!waffenName_.equals(this.waffenName_)) {
            this.waffenName_ = waffenName_;
            updateInDB();
        }
    }

    public int getWaffenSchaden_() {
        return waffenSchaden_;
    }

    public void setWaffenSchaden_(int waffenSchaden_) {
        if(this.waffenSchaden_ != waffenSchaden_) {
            this.waffenSchaden_ = waffenSchaden_;
            updateInDB();
        }
    }

    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }

    public void setAusruestung_(Ausruestung ausruestung_) {
        if(!ausruestung_.equals(this.ausruestung_)) {
            this.ausruestung_ = ausruestung_;
            updateInDB();
        }
    }

    
    public int getID_() {
        return ID_;
    }



    public void deleteFromDB() {
        if(getID_() != 0)
            dbManipulator_.delete(this);
    }
    
    
    
    public void addToDB() {
        dbManipulator_.add(this);
    }



    public int compareTo(Waffen secondWaffe) {
        return getWaffenName_().compareTo(secondWaffe.getWaffenName_());
    }
}
