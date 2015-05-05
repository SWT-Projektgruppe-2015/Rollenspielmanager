package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controller.AusruestungsManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "GEGNER")
public class Gegner implements DBObject {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(30) NOT NULL default 'Gegner Nr. 420'")
    public String name_;
    @Column(name = "KREIS", columnDefinition = "INTEGER NOT NULL default '1' check(KREIS >= 1 and KREIS<=4)")
    public int kreis_;
    @Column(name = "LEVEL", columnDefinition = "INTEGER NOT NULL default '0' check(LEVEL >= 0 and LEVEL<=12)")
    public int level_;
    @Column(name = "ERFAHRUNG", columnDefinition = "INTEGER NOT NULL default '1' check(ERFAHRUNG >= 1)")
    public int erfahrung_;
    @Column(name = "STAERKE", columnDefinition = "INTEGER NOT NULL default '1' check(STAERKE >= 1)")
    public int staerke_;
    @Column(name = "GESCHICK", columnDefinition = "INTEGER NOT NULL default '1' check(GESCHICK >= 1)")
    public int geschick_;
    @Column(name = "LEBENSPUNKTE", columnDefinition = "INTEGER NOT NULL default '1' CHECK(LEBENSPUNKTE >= 0)")
    public int lebenspunkte_;
    @OneToOne(optional = false)
    @JoinColumn(name = "BEUTETYP_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    public Beute beuteTyp;
    @OneToOne(optional = false)
    @JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition = "INTEGER NOT NULL default '1'")
    public Ausruestung ausruestung_;
    
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
            ausruestung_.defH_ = 1;
            ausruestung_.defR_ = 1;
            ausruestung_.defS_ = 0;
            AusruestungsManipulator.getInstance().add(ausruestung_);
        }
    }
}
