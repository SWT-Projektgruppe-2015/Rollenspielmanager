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
public class Waffen implements Named {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "EFFEKT_TYP")
	public int effektTyp_;
	@Column(name = "NAME")
	public String waffenName_;
	@Column(name = "SCHADEN")
	public int waffenSchaden_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID")
	public Ausruestung ausruestung_;
	
	@Override
	public String getName() {
		return waffenName_;
	}
}
