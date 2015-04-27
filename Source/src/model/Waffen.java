package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "WAFFEN")
public class Waffen {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "EFFEKT_TYP")
	public int effektTyp_;
	@Column(name = "NAME", nullable = false)
	public String waffenName_;
	@Column(name = "SCHADEN", nullable = false)
	public int waffenSchaden_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", nullable = false)
	public Ausruestung ausruestung_;
}
