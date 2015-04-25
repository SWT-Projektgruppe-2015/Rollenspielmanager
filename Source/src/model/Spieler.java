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

@Entity
@Table( name = "SPIELER")
public class Spieler {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME")
	public String name_;
	@Column(name = "KREIS")
	public int kreis_;
	@Column(name = "LEVEL")
	public int level_;
	@Column(name = "DISZIPLIN")
	public String disziplin_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID")
	public Ausruestung ausruestung_;
	
	public static List<Spieler> getAllPlayers() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Spieler> getAllRows = theManager.createQuery("SELECT s FROM SPIELER s", Spieler.class);
        return getAllRows.getResultList();
	}
}
