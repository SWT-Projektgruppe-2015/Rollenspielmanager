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

import model.interfaces.DBObject;

/** 
 * Die Default-Werte fuer Ausruestung, verwendet bei PrePersist Methoden, 
 * die sicherstellen das ein Fremdschluessel vorhanden ist.
 * DEFR = 1
 * DEFH = 1
 * DEFS = 0
 * 
 */
@Entity
@Table(name = "AUSRUESTUNGEN")
public class Ausruestung implements DBObject {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "DEFR", columnDefinition="INTEGER NOT NULL DEFAULT '1' CHECK(DEFR >= 1)")
	private int defR_;
	@Column(name = "DEFH", columnDefinition="INTEGER NOT NULL DEFAULT '1' CHECK(DEFH >= 1)")
	private int defH_;
	@Column(name = "DEFS", columnDefinition="INTEGER DEFAULT '0' CHECK(DEFS >= 0)")
	private int defS_;
	
	/**
	 * Setzt DefR, DefH und DefS auf die Default-Werte.
	 */
	public Ausruestung() {
		super();
		setDefR_(1);
		setDefH_(1);
		setDefS_(0);
	}
	
	public int getDefR_() {
		return defR_;
	}
	public void setDefR_(int defR_) {
		this.defR_ = defR_;
	}
	public int getDefH_() {
		return defH_;
	}
	
	public void setDefH_(int defH_) {
		this.defH_ = defH_;
	}
	
	public int getDefS_() {
		return defS_;
	}
	
	public void setDefS_(int defS_) {
		this.defS_ = defS_;
	}
	
	public List<Waffen> getWaffen() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Waffen> getWaffenInAusruestung = theManager.createQuery(
        		"FROM Waffen w WHERE w.ausruestung_ = " + ID_, Waffen.class);
        return getWaffenInAusruestung.getResultList();
	}
	
	@PrePersist
	public void onCreate()	{
		if(defH_ == 0)	{
			defH_=1;
		}
		if(defR_ == 0)	{
			defR_ = 1;
		}
		
	}
	
	
	public List<Faehigkeiten> getFaehigkeiten() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Faehigkeiten> getFaehigkeitenInAusruestung = theManager.createQuery(
        		"FROM Faehigkeiten f WHERE f.ausruestung_ = " + ID_, Faehigkeiten.class);
        return getFaehigkeitenInAusruestung.getResultList();
	}

	public void addWaffe(Waffen selectedWaffe) {
		selectedWaffe.setAusruestung_(this);
	}

	public void deleteWaffe(Waffen waffe) {
		waffe.remove();
	}

	public void deleteFaehigkeit(Faehigkeiten faehigkeit) {
		faehigkeit.remove();
	}
}