package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import model.interfaces.DBObject;

/**
 * Einfaches Item für den Händler: Name, Beschreibung, Preis.
 */
@Entity
@Table(name = "SIMPLESALESITEM")
public class SimpleSalesItem implements DBObject {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int ID_;
    @Column(name = "NAME", columnDefinition = "VARCHAR(100) NOT NULL default 'Einfacher Gegenstand'")
    private String name_;
    @Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(400)")
    private String description_;
    @Column(name = "COST", columnDefinition = "INTEGER DEFAULT '0' CHECK(COST >= 0)")
    private int cost_;
    
    public SimpleSalesItem() {
        name_ = "Einfacher Gegenstand";
        cost_ = 0;
    }
    
    
    
    public String getName_() {
        return name_;
    }
    
    
    
    public void setName_(String name_) {
        this.name_ = name_;
    }
    
    
    
    public String getDescription_() {
        return description_;
    }
    
    
    
    public void setDescription_(String description_) {
        this.description_ = description_;
    }
    
    
    
    public int getCost_() {
        return cost_;
    }
    
    
    
    public void setCost_(int cost_) {
        this.cost_ = cost_;
    }
    
    
    
    public int getID_() {
        return ID_;
    }
}
