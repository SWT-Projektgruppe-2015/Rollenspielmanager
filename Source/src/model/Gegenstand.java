package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import controller.manipulators.EinfacherGegenstandManipulator;
import model.interfaces.DBObject;

/**
 * Einfaches Item f�r den H�ndler: Name, Beschreibung, Preis.
 */
@Entity
@Table(name="GEGENSTAND")
public class Gegenstand implements DBObject, Comparable<Gegenstand> {
    private static EinfacherGegenstandManipulator dbManipulator_ = EinfacherGegenstandManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(100) NOT NULL default 'Einfacher Gegenstand'")
    private String name_;
    @Column(name = "BESCHREIBUNG", columnDefinition = "VARCHAR(400)")
    private String beschreibung_;
    @Column(name = "KATEGORIE", columnDefinition = "VARCHAR(400)")
    private String kategorie_;



    @Column(name = "VORKOMMEN", columnDefinition = "VARCHAR(400)")
    private String vorkommen_;
    
    @Column(name = "KOSTEN", columnDefinition = "INTEGER DEFAULT '0' CHECK(KOSTEN >= 0)")
    private int kosten_;
    @Column(name = "TRAGLAST", columnDefinition = "INTEGER DEFAULT '0' CHECK(TRAGLAST >= 0)")
    private int traglast_;
    @Column(name = "STAERKE", columnDefinition = "INTEGER DEFAULT '0' CHECK(STAERKE >= 0)")
    private int staerke_;
    @Column(name = "WERT", columnDefinition = "VARCHAR(400)")
    private String wert_;
    
    
    
    public Gegenstand() {
        name_ = "Einfacher Gegenstand";
        kosten_ = 0;
        traglast_ = 0;
        beschreibung_ = "";
        staerke_ = 0;
        wert_ = "0";
        kategorie_ = "ohne kategorie";
        vorkommen_ = "";
    }
    
    
    
    public String getKategorie_() {
        return kategorie_;
    }



    public void setKategorie_(String kategorie_) {
        this.kategorie_ = kategorie_;
        updateInDB();
    }



    public String getVorkommen_() {
        return vorkommen_;
    }



    public void setVorkommen_(String vorkommen_) {
        this.vorkommen_ = vorkommen_;
        updateInDB();
    }



    public int getStaerke_() {
        return staerke_;
    }



    public void setStaerke_(int staerke_) {
        this.staerke_ = staerke_;
        updateInDB();
    }



    public String getWert_() {
        return wert_;
    }



    public void setWert_(String wert_) {
        this.wert_ = wert_;
        updateInDB();
    }    
    
    
    
    public int getTraglast_() {
        return traglast_;
    }



    public void setTraglast_(int traglast_) {
        this.traglast_ = traglast_;
        updateInDB();
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



    public void addToDB() {
        dbManipulator_.add(this);
    }



    public void deleteFromDB() {
        dbManipulator_.delete(this);
    }



    @Override
    public int compareTo(Gegenstand otherGegenstand) {
        return getName_().compareTo(otherGegenstand.getName_());
    }
    
    
    
    public static List<Gegenstand> getAll() {
        List<Gegenstand> allGegenstaende = dbManipulator_.getAll();
        return allGegenstaende;
    }

    
    
    public static List<String> getKategorien(List<Gegenstand> gegenstaende) {
        List<String> kategorien = new ArrayList<String>();
        if(gegenstaende != null){
            for(Gegenstand current : gegenstaende) {
                String currentKategorie = current.getKategorie_();
                if(!kategorien.contains(currentKategorie))
                    kategorien.add(currentKategorie);
            }
        }
        return kategorien;
    }
    
    
    
    public static List<String> getSubKategories(String kategory) {
        List<String> subKategories = Arrays.asList(kategory.split("\\."));
        return subKategories;
    }
}
