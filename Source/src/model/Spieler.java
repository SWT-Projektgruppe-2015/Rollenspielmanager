package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.PrePersist;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import controller.AusruestungsManipulator;
import model.interfaces.DBObject;

@Entity
@Table( name = "SPIELER")

public class Spieler implements DBObject {
	public static final int MAX_KREIS = 4;
	public static final int MAX_LEVEL = 12;
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static EntityManager theManager = factory.createEntityManager();
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int ID_;
	@Column(name = "NAME", columnDefinition="VARCHAR(30) NOT NULL default 'Jane Doe'")
	private String name_;
	@Column(name = "KREIS", columnDefinition="INTEGER NOT NULL default '1' check(KREIS >= 1 and KREIS <= 4)")
	private int kreis_;
	@Column(name = "LEVEL", columnDefinition="INTEGER NOT NULL default '0' check(LEVEL >= 0 and LEVEL <= 12)")
	private int level_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", unique = false, columnDefinition="Integer NOT NULL default '1'")
	private Ausruestung ausruestung_;
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "SPIELER_IN_GRUPPE", 		
		joinColumns=
			{@JoinColumn(name="SPIELER_ID", referencedColumnName="ID")},
        inverseJoinColumns=
            {@JoinColumn(name="GRUPPEN_ID", referencedColumnName="ID")}
	)
	private Set<Gruppe> membership_;
	
	@PrePersist
	public void onCreate()	{
		if(getName_() == null)	{
			setName_("Jane Doe");
		}
		if(getKreis_() == 0)	{
			setKreis_(1);
		}
		if(getAusruestung_() == null)	{
			setAusruestung_(new Ausruestung());
			AusruestungsManipulator.getInstance().add(getAusruestung_());
		}
		
	}
	
	@Override
	public String toString() {
		return getName_();
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
	 * @param membership_ the membership_ to set
	 */
	public void setMembership_(Set<Gruppe> membership_) {
		this.membership_ = membership_;
	}

	/**
	 * @param ausruestung_ the ausruestung_ to set
	 */
	public void setAusruestung_(Ausruestung ausruestung_) {
		this.ausruestung_ = ausruestung_;
	}

	/**
	 * @param level_ the level_ to set
	 */
	public void setLevel_(int level_) {
		this.level_ = level_;
	}

	/**
	 * @param kreis_ the kreis_ to set
	 */
	public void setKreis_(int kreis_) {
		this.kreis_ = kreis_;
	}

	/**
	 * @param name_ the name_ to set
	 */
	public void setName_(String name_) {
		this.name_ = name_;
	}

	/**
	 * @param iD_ the iD_ to set
	 */
	public void setID_(int iD_) {
		ID_ = iD_;
	}

	public int getDefR() {
		Ausruestung ausruestung = getAusruestung_();
		if(ausruestung == null)
			return 1;
		
		return getAusruestung_().getDefR_();
	}
	
	public void setDefR(int def) {
		Ausruestung ausruestung = getAusruestungForModification();
		
		if(def > 0)
			ausruestung.setDefR_(def);
	}
	
	public int getDefH() {
		Ausruestung ausruestung = getAusruestung_();
		if(ausruestung == null)
			return 1;
		
		return getAusruestung_().getDefH_();
	}
	
	public void setDefH(int def) {
		Ausruestung ausruestung = getAusruestungForModification();
		
		if(def > 0)
			ausruestung.setDefH_(def);
	}
	
	public int getDefS() {
		Ausruestung ausruestung = getAusruestung_();
		if(ausruestung == null)
			return 0;
		
		return getAusruestung_().getDefS_();
	}
	
	public void setDefS(int def) {
		Ausruestung ausruestung = getAusruestungForModification();
		
		if(def >= 0)
			ausruestung.setDefS_(def);
	}

	/**
	 * Falls keine Ausruestung vorhanden ist, wird eine neue erstellt und mit dem Spieler verbunden.
	 * @return Ausruestung des Spielers, niemals null.
	 */
	private Ausruestung getAusruestungForModification() {
		Ausruestung ausruestung = getAusruestung_();
		if(ausruestung == null) {
			ausruestung = new Ausruestung();
			setAusruestung_(ausruestung);
		}
		return ausruestung;
	}
	
	
	public List<Waffen> getWaffen() {
		Ausruestung ausruestung = getAusruestung_();
		if(ausruestung == null)
			return new ArrayList<Waffen>();
		
		return getAusruestung_().getWaffen();
	}
	
	
	
	public List<Faehigkeiten> getFaehigkeiten() {
		Ausruestung ausruestung = getAusruestung_();
		if(ausruestung == null)
			return new ArrayList<Faehigkeiten>();
		
		return getAusruestung_().getFaehigkeiten();
	}
	
	
	
	public static List<Spieler> getAllPlayers() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Spieler> getAllRows = theManager.createQuery("FROM Spieler", Spieler.class);
        return getAllRows.getResultList();
	}

	public void remove() {
		// TODO loesche Spieler in der DB mit DB Manipulatoren
		
	}
	
	public void increaseLevel() {
		boolean spielerHasMaximumLevelInKreis = getLevel_() == MAX_LEVEL;
		
		if(!spielerHasMaximumLevelInKreis) {
			setLevel_(getLevel_()+1);
		}
		else {
			increaseKreis();
		}
	}
	
	private void increaseKreis() {
		boolean spielerHasMaximumKreis = getKreis_() == MAX_KREIS;
		
		if(!spielerHasMaximumKreis) {
			setLevel_(1);
			setKreis_(getKreis_()+1);
		}
	}

	public void decreaseLevel() {
		boolean spielerHasMinimumLevelInKreis = getLevel_() == 1;
		
		if(!spielerHasMinimumLevelInKreis) {
			setLevel_(getLevel_()-1);
		}
		else {
			decreaseKreis();
		}
	}
	
	private void decreaseKreis() {
		boolean spielerHasMinimumKreis = getKreis_() == 1;
		
		if(!spielerHasMinimumKreis) {
			setLevel_(MAX_LEVEL);
			setKreis_(getKreis_()-1);
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

	public void deleteFaehigkeit(Faehigkeiten faehigkeit) {
		Ausruestung ausruestung = getAusruestungForModification();
		ausruestung.deleteFaehigkeit(faehigkeit);
	}
}
