package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.interfaces.DBObject;

@Entity
@Table(name = "SPIELER_IN_GRUPPE")
public class SpielerInGruppe implements DBObject {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@OneToOne(optional = false)
	@JoinColumn(name = "SPIELER_ID", columnDefinition = "INTEGER NOT NULL")
	public Spieler mitglied_;
	@OneToOne(optional = false)
	@JoinColumn(name = "GRUPPEN_ID", columnDefinition = "INTEGER NOT NULL")
	public Gruppe gruppe_;
}
