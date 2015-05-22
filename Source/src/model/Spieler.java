package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.PrePersist;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import controller.manipulators.SpielerManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "SPIELER")
public class Spieler extends Charakter implements DBObject {
    private static SpielerManipulator spielerManipulator_ = SpielerManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(30) NOT NULL default 'Jane Doe'")
    private String name_;
    @Column(name = "KREIS", columnDefinition = "INTEGER NOT NULL default '1' check(KREIS >= 1 and KREIS <= 4)")
    private int kreis_;
    @Column(name = "LEVEL", columnDefinition = "INTEGER NOT NULL default '0' check(LEVEL >= 0 and LEVEL <= 12)")
    private int level_;
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "AUSRUESTNGS_ID", unique = false, columnDefinition = "Integer NOT NULL default '1'")
    private Ausruestung ausruestung_;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "SPIELER_IN_GRUPPE", joinColumns = { @JoinColumn(name = "SPIELER_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "GRUPPEN_ID", referencedColumnName = "ID") })
    private Set<Gruppe> membership_;    
    
    
    
    @PrePersist
    public void onCreate() {
        if (getName_() == null) {
            name_ = "Jane Doe";
        }
        if (getKreis_() == 0) {
            kreis_ = 1;
        }
        if (getAusruestung_() == null) {
            ausruestung_ = new Ausruestung();
        }
    }
    
    
    
    @Override
    public String toString() {
        return getName_();
    }
    
    
    
    public static List<Spieler> getAll() {
        return spielerManipulator_.getAll();
    }
    
    

    public void deleteFromDB() {
        spielerManipulator_.delete(this);
    }

    
    
    public void addToDB() {
        spielerManipulator_.add(this);
    }


    
    private void updateInDB() {
        if(this.getID_() != 0)
            spielerManipulator_.update(this);
    }
    
    
    /**
     * @return the iD_
     */
    public int getID_() {
        return ID_;
    }
    
    
    
    /**
     * @return the name_
     */
    public String getName_() {
        return name_;
    }
    
    
    
    /**
     * @return the kreis_
     */
    public int getKreis_() {
        return kreis_;
    }
    
    
    
    /**
     * @return the level_
     */
    public int getLevel_() {
        return level_;
    }
    
    
    
    /**
     * @return the ausruestung_
     */
    public Ausruestung getAusruestung_() {
        return ausruestung_;
    }
    
    

    /**
     * @return the membership_
     */
    public Set<Gruppe> getMembership_() {
        return membership_;
    }
    
    
    
    /**
     * @param ausruestung_
     *            the ausruestung_ to set
     */
    public void setAusruestung_(Ausruestung ausruestung) {
        if(ausruestung != ausruestung_) {
            boolean spielerInDbButAusruestungIsNot = getID_() != 0 && ausruestung.getID_() == 0;
            if(spielerInDbButAusruestungIsNot)
                ausruestung.addToDB();
            ausruestung_ = ausruestung;
        }
    }
    
    
    
    /**
     * @param level_
     *            the level_ to set
     */
    public void setLevel_(int level_) {
        if(level_ != this.level_) {
            this.level_ = level_;
            updateInDB();
        }
    }
    
    
    
    /**
     * @param kreis_
     *            the kreis_ to set
     */
    public void setKreis_(int kreis_) {
        if(kreis_ != this.kreis_) {
            this.kreis_ = kreis_;
            updateInDB();
        }
    }
    
    
    
    /**
     * @param name_
     *            the name_ to set
     */
    public void setName_(String name_) {
        if(name_ != this.name_) {
            this.name_ = name_;
            updateInDB();
        }
    }
    
    
    
    /**
     * @param iD_
     *            the iD_ to set
     */
    public void setID_(int iD_) {
        ID_ = iD_;
    }

    
    
    public List<Waffen> getWaffen() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return new ArrayList<Waffen>();
        
        return getAusruestung_().getWaffen();
    }

    
    
    public List<Faehigkeiten> getFaehigkeiten() {
        Ausruestung ausruestung = getAusruestung_();
        if (ausruestung == null)
            return new ArrayList<Faehigkeiten>();
        
        return getAusruestung_().getFaehigkeiten();
    }


    
    public static List<Spieler> getAllPlayers() {
        return spielerManipulator_.getAll();
    }

    

    public void increaseLevel() {
        boolean spielerHasMaximumLevelInKreis = getLevel_() == MAX_LEVEL;
        
        if (!spielerHasMaximumLevelInKreis) {
            setLevel_(getLevel_() + 1);
        }
        else {
            increaseKreis();
        }
    }
    
    

    private void increaseKreis() {
        boolean spielerHasMaximumKreis = getKreis_() == MAX_KREIS;

        if (!spielerHasMaximumKreis) {
            setLevel_(1);
            setKreis_(getKreis_() + 1);
        }
    }

    

    public void decreaseLevel() {
        boolean spielerHasMinimumLevelInKreis = getLevel_() == 1;

        if (!spielerHasMinimumLevelInKreis) {
            setLevel_(getLevel_() - 1);
        }
        else {
            decreaseKreis();
        }
    }
    
    

    private void decreaseKreis() {
        boolean spielerHasMinimumKreis = getKreis_() == 1;

        if (!spielerHasMinimumKreis) {
            setLevel_(MAX_LEVEL);
            setKreis_(getKreis_() - 1);
        }
    }
    
    
    
    public void addWaffe(Waffen waffe) {
        Ausruestung ausruestung = getAusruestungForModification();
        ausruestung.addWaffe(waffe);
    }
    
    
    
    public void deleteWaffe(Waffen waffe) {
        Ausruestung ausruestung = getAusruestungForModification();
        ausruestung.deleteWaffe(waffe);
    }
    
    
    
    public void addFaehigkeit(Faehigkeiten faehigkeit) {
        Ausruestung ausruestung = getAusruestungForModification();
        ausruestung.addFaehigkeit(faehigkeit);
    }
    
    
    
    public void deleteFaehigkeit(Faehigkeiten faehigkeit) {
        Ausruestung ausruestung = getAusruestungForModification();
        ausruestung.deleteFaehigkeit(faehigkeit);
    }



    void addToGruppe(Gruppe gruppe) {
        if(membership_ == null)
            membership_ = new HashSet<Gruppe>();
        membership_.add(gruppe);
        gruppe.getMembers_().add(this);
        updateInDB();
    }
    

    
    void removeFromGruppe(Gruppe gruppe) {
        membership_.remove(gruppe);
        gruppe.getMembers_().remove(this);
        updateInDB();
    }
}
