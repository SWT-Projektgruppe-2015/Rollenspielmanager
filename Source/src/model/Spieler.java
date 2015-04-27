package model;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table( name = "SPIELER")
public class Spieler {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME", nullable = false)
	public String name_;
	@Column(name = "KREIS", nullable = false)
	public int kreis_;
	@Column(name = "LEVEL", nullable = false)
	public int level_;
	@Column(name = "DISZIPLIN")
	public String disziplin_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", nullable = false)
	public Ausruestung ausruestung_;
}
