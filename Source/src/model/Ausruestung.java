package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


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
}
