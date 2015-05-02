package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import controller.AusruestungsManipulator;
import model.interfaces.DBObject;

@Entity
@Table(name = "FAEHIGKEITEN")
public class Faehigkeiten implements DBObject {
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public int ID_;
	@Column(name = "NAME", columnDefinition="VARCHAR(30) NOT NULL default 'Geheime Magie'")
	private String name_;
	@Column(name = "EFFEKT_TYP", columnDefinition="INTEGER NOT NULL default '0' check(EFFEKT_TYP >= 0 and EFFEKT_TYP <= 4)")
	private int effekttypen_;
	@ManyToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition="INTEGER NOT NULL default '1'")
	private Ausruestung ausruestung_;
	
	@Override
	public String toString() {
		return name_;
	}
	@PrePersist
	public void onCreate()	{
		if(name_ == null)	{
			name_="Geheime Magie";
		}	
		if(ausruestung_ == null)	{
			ausruestung_ = new Ausruestung();
			AusruestungsManipulator.getInstance().add(ausruestung_);
		}
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public int getEffekttypen_() {
		return effekttypen_;
	}
	public void setEffekttypen_(int effekttypen_) {
		this.effekttypen_ = effekttypen_;
	}
	public Ausruestung getAusruestung_() {
		return ausruestung_;
	}
	public void setAusruestung_(Ausruestung ausruestung_) {
		this.ausruestung_ = ausruestung_;
	}
	public void remove() {
		// TODO lösche aus DB mithilfe von DB Manipulatoren	
	}
	
}
