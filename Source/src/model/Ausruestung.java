package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TypedQuery;


@Entity
@Table( name = "AUSRUESTUNGEN")
public class Ausruestung {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "DEFR")
	public int defR_;
	@Column(name = "DEFH")
	public int defH_;
	@Column(name = "DEFS")
	public int defS_;
	
	public List<Waffen> getWaffen() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Waffen> getWaffenInAusruestung = theManager.createQuery(
        		"FROM Waffen w WHERE w.ausruestung_ = " + ID_, Waffen.class);
        return getWaffenInAusruestung.getResultList();
	}

	
	
	public List<Faehigkeiten> getFaehigkeiten() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager theManager = factory.createEntityManager();
        TypedQuery<Faehigkeiten> getFaehigkeitenInAusruestung = theManager.createQuery(
        		"FROM Faehigkeiten f WHERE f.ausruestung_ = " + ID_, Faehigkeiten.class);
        return getFaehigkeitenInAusruestung.getResultList();
	}
}