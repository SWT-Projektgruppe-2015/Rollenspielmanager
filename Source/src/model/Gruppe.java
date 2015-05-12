package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import controller.manipulators.GruppenManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "GRUPPEN")
public class Gruppe implements DBObject {
    private static GruppenManipulator dbManipulator_ = GruppenManipulator.getInstance();

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int ID_;
    @Column(name = "NAME", columnDefinition = " VARCHAR(30) NOT NULL DEFAULT 'Montags Gruppe'")
    private String name_;
    @ManyToMany(mappedBy = "membership_")
    private Set<Spieler> members_;    
    
    public Gruppe(){
        name_ = "DefaultGruppe";
        members_ = new HashSet<Spieler>();
    }
    
    
    
    private void updateInDB() {
        if(ID_ != 0)
            dbManipulator_.update(this);
    }
    
    public static List<Gruppe> getAll() {
        return dbManipulator_.getAll();
    }
    
    @Override
    public String toString() {
        return name_;
    }

    public void addSpieler(Spieler spieler) {
        if (members_ == null) {
            members_ = new HashSet<Spieler>();
        }
        members_.add(spieler);
        spieler.addToGruppe(this);
    }



    public Set<Spieler> getAllSpieler() {
        if (members_ == null) {
            members_ = new HashSet<Spieler>();
        }
        return members_;
    }



    public void removePlayer(Charakter spieler) {
        if (members_ != null) {
            members_.remove(spieler);
        }
    }



    public String getName() {
        return name_;
    }
    
    

    public void deleteFromDB() {
        dbManipulator_.delete(this);
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
        updateInDB();
    }



    public Set<Spieler> getMembers_() {
        return members_;
    }



    public void setMembers_(Set<Spieler> members_) {
        this.members_ = members_;
    }    

    public void addToDB() {
        dbManipulator_.add(this);
    }
}
