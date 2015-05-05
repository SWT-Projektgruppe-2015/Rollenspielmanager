package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import model.interfaces.DBObject;

@Entity
@Table(name = "WAFFEN")
public class Waffen implements DBObject {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int ID_;
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
        super();
        setWaffenSchaden_(0);
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
        this.effektTyp_ = effektTyp_;
    }

    public String getWaffenName_() {
        return waffenName_;
    }

    public void setWaffenName_(String waffenName_) {
        this.waffenName_ = waffenName_;
    }

    public int getWaffenSchaden_() {
        return waffenSchaden_;
    }

    public void setWaffenSchaden_(int waffenSchaden_) {
        this.waffenSchaden_ = waffenSchaden_;
    }

    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }

    public void setAusruestung_(Ausruestung ausruestung_) {
        this.ausruestung_ = ausruestung_;
    }

    public void deleteFromDB() {
        // entferne Waffe aus DB mithilfe von Manipulatoren
    }
}
