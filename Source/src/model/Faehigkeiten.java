package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FAEHIGKEITEN")
public class Faehigkeiten implements Named {
	
	public String name_;
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "EFFEKT_TYP")
	public int effekttypen_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID")
	public Ausruestung ausruestung_;
	
	@Override
	public String getName() {
		return name_;
	}
}
