package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import controller.manipulators.BeuteManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "BEUTE")
public class Beute implements DBObject {
    public static final String DEFAULTNAME = "Standard#DontUseThis";
    private static BeuteManipulator dbManipulator_ = BeuteManipulator.getInstance();
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int ID_;
    @Column(name = "PROFIL")
    public String profil_;
    @Column(name = "WAFFEN_WKT")
    public double waffenWkt_;
    @Column(name = "RUESTUNG_WKT")
    public double ruestungWkt_;
    @Column(name = "SCHWEACHUNGS_FAKTOR")
    public double schwaechungsFaktor_;
    @Column(name = "BESONDERES_WKT")
    public double besonderesWkt_;
    @Column(name = "GELDBETRAG")
    public int geldBetrag_;
    
    public Beute() {
        profil_ = DEFAULTNAME;
        waffenWkt_ = 0.1;
        ruestungWkt_ = 0.1;
        schwaechungsFaktor_ = 0.8;
        besonderesWkt_ = 0.01;
        geldBetrag_ = 5;
    }
    
    private void updateInDB() {
        if(getID_() != 0)
            dbManipulator_.update(this);
    }
    
    public int getID_() {
        return ID_;
    }

    public void setID_(int iD_) {
        ID_ = iD_;
    }

    public String getProfil_() {
        return profil_;
    }

    public void setProfil_(String profil_) {
        this.profil_ = profil_;
        updateInDB();
    }

    public double getWaffenWkt_() {
        return waffenWkt_;
    }

    public void setWaffenWkt_(double waffenWkt_) {
        this.waffenWkt_ = waffenWkt_;
        updateInDB();
    }

    public double getRuestungWkt_() {
        return ruestungWkt_;
    }

    public void setRuestungWkt_(double ruestungWkt_) {
        this.ruestungWkt_ = ruestungWkt_;
        updateInDB();
    }

    public double getSchwaechungsFaktor_() {
        return schwaechungsFaktor_;
    }

    public void setSchwaechungsFaktor_(double schwaechungsFaktor_) {
        this.schwaechungsFaktor_ = schwaechungsFaktor_;
        updateInDB();
    }

    public double getBesonderesWkt_() {
        return besonderesWkt_;
    }

    public void setBesonderesWkt_(double besonderesWkt_) {
        this.besonderesWkt_ = besonderesWkt_;
        updateInDB();
    }

    public int getGeldBetrag_() {
        return geldBetrag_;
    }

    public void setGeldBetrag_(int geldBetrag_) {
        this.geldBetrag_ = geldBetrag_;
        updateInDB();
    }

    
    
    public void addToDB() {
        dbManipulator_.add(this);
    }
}
