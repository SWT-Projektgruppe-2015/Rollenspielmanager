package model;

import java.util.List;

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


@Entity
@Table( name = "SPIELER")
public class Spieler {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME", columnDefinition="VARCHAR(30) NOT NULL default 'Jane Doe'")
	public String name_;
	@Column(name = "KREIS", columnDefinition="INTEGER NOT NULL default '1' check(KREIS >= 1 and KREIS <= 4)")
	public int kreis_;
	@Column(name = "LEVEL", columnDefinition="INTEGER NOT NULL default '0' check(LEVEL >= 0 and LEVEL <= 12)")
	public int level_;
	@Column(name = "DISZIPLIN")
	public String disziplin_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition="Integer NOT NULL default '1'")
	public Ausruestung ausruestung_;
	
	@PrePersist
	public void onCreate()	{
		if(name_ == null)	{
			name_="Jane Doe";
		}
		if(kreis_ == 0)	{
			kreis_ = 1;
		}
		
	}
	
	@Override
	public String toString() {
		return name_;
	}
	
	
	
	public int getDefR() {
		return ausruestung_.defR_;
	}
	
	
	
	public int getDefH() {
		return ausruestung_.defH_;
	}
	
	
	
	public int getDefS() {
		return ausruestung_.defS_;
	}
	
	
	
	public List<Waffen> getWaffen() {
		return ausruestung_.getWaffen();
	}
	
	
	
	public List<Faehigkeiten> getFaehigkeiten() {
		return ausruestung_.getFaehigkeiten();
	}
	
	
	
	public static List<Spieler> getAllPlayers() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Spieler> getAllRows = theManager.createQuery("FROM Spieler", Spieler.class);
        return getAllRows.getResultList();
	}
}
