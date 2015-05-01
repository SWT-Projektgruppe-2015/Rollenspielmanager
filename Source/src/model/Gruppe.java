package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import model.interfaces.DBObject;

@Entity
@Table(name = "GRUPPEN")
public class Gruppe implements DBObject {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME", columnDefinition =" VARCHAR(30) NOT NULL DEFAULT 'Montags Gruppe'")
	private String name_;
	@ManyToMany(mappedBy = "membership_")
	private Set<Spieler> members_;

	@Override
	public String toString() {
		return name_;
	}
	
	public void addSpieler(Spieler spieler) {
		if(members_ == null) {
			members_ = new HashSet<Spieler>();
		}
		members_.add(spieler);
	}
	
	public Set<Spieler> getAllSpieler() {
		if(members_ == null) {
			members_ = new HashSet<Spieler>();
		}
		return members_;
	}

	public void removePlayer(Spieler spieler) {
		if(members_ != null) {
			members_.remove(spieler);
		}
	}
	
	public String getName() {
		return name_;
	}
	
	public void setName(String name) {
		name_ = name;
	}
	
	public void remove() {
	}
}
