package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import manipulators.AusruestungsManipulator;
import manipulators.WaffenManipulator;
import model.interfaces.DBObject;

/**
 * Die Default-Werte fuer Ausruestung, verwendet bei PrePersist Methoden, die
 * sicherstellen das ein Fremdschluessel vorhanden ist. DEFR = 1 DEFH = 1 DEFS =
 * 0
 * 
 */
@Entity
@Table(name = "AUSRUESTUNGEN")
public class Ausruestung implements DBObject {
    private static AusruestungsManipulator dbManipulator_ = AusruestungsManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "DEFR", columnDefinition = "INTEGER NOT NULL DEFAULT '1' CHECK(DEFR >= 1)")
    private int defR_;
    @Column(name = "DEFH", columnDefinition = "INTEGER NOT NULL DEFAULT '1' CHECK(DEFH >= 1)")
    private int defH_;
    @Column(name = "DEFS", columnDefinition = "INTEGER DEFAULT '0' CHECK(DEFS >= 0)")
    private int defS_;
    
    private void updateInDB() {
        if(getID_() != 0)
            dbManipulator_.update(this);
    }
    
    /**
     * Setzt DefR, DefH und DefS auf die Default-Werte.
     */
    public Ausruestung() {
        setDefR_(1);
        setDefH_(1);
        setDefS_(0);
    }
    
    public int getDefR_() {
        return defR_;
    }
    
    public void setDefR_(int defR_) {
        this.defR_ = defR_;
        updateInDB();
    }
    
    public int getDefH_() {
        return defH_;
    }
    
    public void setDefH_(int defH_) {
        this.defH_ = defH_;
        updateInDB();
    }
    
    public int getDefS_() {
        return defS_;
    }
    
    public void setDefS_(int defS_) {
        this.defS_ = defS_;
        updateInDB();
    }
    
    public List<Waffen> getWaffen() {
        return WaffenManipulator.getInstance().getWaffenInAusruestung(this);
    }
    
    @PrePersist
    public void onCreate() {
        if (defH_ == 0) {
            defH_ = 1;
        }
        if (defR_ == 0) {
            defR_ = 1;
        }
        
    }
    
    public List<Faehigkeiten> getFaehigkeiten() {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Faehigkeiten> getFaehigkeitenInAusruestung = theManager
                .createQuery("FROM Faehigkeiten f WHERE f.ausruestung_ = "
                        + getID_(), Faehigkeiten.class);
        return getFaehigkeitenInAusruestung.getResultList();
    }
    
    public void addWaffe(Waffen selectedWaffe) {       
        selectedWaffe.setAusruestung_(this);
        boolean ausruestungInDbButWaffeIsNot = selectedWaffe.ID_ == 0 && getID_() != 0;
        if(ausruestungInDbButWaffeIsNot) {
            selectedWaffe.addToDB();
        }
    }
    
    public void deleteWaffe(Waffen waffe) {
        waffe.deleteFromDB();
    }
    
    public void addFaehigkeit(Faehigkeiten faehigkeit) {
        faehigkeit.setAusruestung_(this);
    }
    
    public void deleteFaehigkeit(Faehigkeiten faehigkeit) {
        faehigkeit.remove();
    }

    /**
     * @return the iD_
     */
    public int getID_() {
        return ID_;
    }

    /**
     * @param iD_ the iD_ to set
     */
    public void setID_(int iD_) {
        ID_ = iD_;
    }

    public void addToDB() {
        dbManipulator_.add(this);
    }

    public void deleteFromDB() {
        dbManipulator_.delete(this);
    }
}