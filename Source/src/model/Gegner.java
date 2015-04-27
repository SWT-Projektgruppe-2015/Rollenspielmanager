package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEGNER")
public class Gegner {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME")
	public String name_;
	@Column(name = "KREIS")
	public int kreis_;
	@Column(name = "STUFE")
	public int stufe_;
	@Column(name = "DISZIPLIN")
	public String disziplin_;
	@Column(name = "RASSE")
	public String rasse_;
	@Column(name = "ERFAHRUNG")
	public int erfahrung_;
	@Column(name = "STAERKE")
	public int staerke_;
	@Column(name = "GESCHICK")
	public int geschick_;
	@OneToOne(optional=false)
	@JoinColumn(name = "BEUTETYP_ID")
	public Beute beuteTyp;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID")
	public Ausruestung ausruestung_;
}
