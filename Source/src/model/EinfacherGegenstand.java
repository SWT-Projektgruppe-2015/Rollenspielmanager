package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import controller.manipulators.EinfacherGegenstandManipulator;
import model.interfaces.DBObject;

/**
 * Einfaches Item für den Händler: Name, Beschreibung, Preis.
 */
@Entity
@Table(name="EINFACHERGEGENSTAND")
public class EinfacherGegenstand implements DBObject, Comparable<EinfacherGegenstand> {
    private static EinfacherGegenstandManipulator dbManipulator_ = EinfacherGegenstandManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(100) NOT NULL default 'Einfacher Gegenstand'")
    private String name_;
    @Column(name = "BESCHREIBUNG", columnDefinition = "VARCHAR(400)")
    private String beschreibung_;
    @Column(name = "KOSTEN", columnDefinition = "INTEGER DEFAULT '0' CHECK(KOSTEN >= 0)")
    private int kosten_;
    @Column(name = "TRAGLAST", columnDefinition = "INTEGER DEFAULT '0' CHECK(KOSTEN >= 0)")
    private int traglast_;
    @Column(name = "STAREKE", columnDefinition = "INTEGER DEFAULT '0' CHECK(KOSTEN >= 0)")
    private int staerke_;
    @Column(name = "WERT", columnDefinition = "INTEGER DEFAULT '0' CHECK(KOSTEN >= 0)")
    private int wert_;
    @Column(name = "KATEGORIE", columnDefinition = "VARCHAR(400)")
    private String kategorie_;
    @Column(name = "VORKOMMEN", columnDefinition = "VARCHAR(400)")
    private String vorkommen_;
    
    
    
    public int getTraglast_() {
        return traglast_;
    }



    public void setTraglast_(int traglast_) {
        this.traglast_ = traglast_;
    }



    public EinfacherGegenstand() {
        name_ = "Einfacher Gegenstand";
        kosten_ = 0;
        traglast_ = 0;
        beschreibung_ = "";
        staerke_ = 0;
        wert_ = 0;
        kategorie_ = "ohne kategorie";
        vorkommen_ = "";
    }
    
    
    
    public String toString() {
        return getName_();
    }
    
    
    public String getName_() {
        return name_;
    }
    
    
    
    public void setName_(String name_) {
        this.name_ = name_;
        updateInDB();
    }
    
    
    
    public String getBeschreibung_() {
        return beschreibung_;
    }
    
    
    
    public void setBeschreibung_(String description_) {
        this.beschreibung_ = description_;
        updateInDB();
    }
    
    
    
    public int getKosten_() {
        return kosten_;
    }
    
    
    
    public void setKosten_(int kosten_) {
        this.kosten_ = kosten_;
        updateInDB();
    }
    
    
    
    private void updateInDB() {
        if(ID_ != 0)
            dbManipulator_.update(this);
    }



    public int getID_() {
        return ID_;
    }



    public static List<EinfacherGegenstand> getAll() {
        List<EinfacherGegenstand> allGegenstaende = dbManipulator_.getAll();
        allGegenstaende.sort(null);
        return allGegenstaende;
    }



    public void addToDB() {
        dbManipulator_.add(this);
    }



    public void deleteFromDB() {
        dbManipulator_.delete(this);
    }



    @Override
    public int compareTo(EinfacherGegenstand otherGegenstand) {
        return getName_().compareTo(otherGegenstand.getName_());
    }
}
