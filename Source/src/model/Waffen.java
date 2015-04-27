package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
	@Column(name = "NAME", columnDefinition="VARCHAR(30) NOT NULL default 'Deus Ex Machina'")
	public String waffenName_;
	@Column(name = "SCHADEN", columnDefinition="INTEGER NOT NULL default '0' check(SCHADEN >= 0)")
	public int waffenSchaden_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition="INTEGER NOT NULL default '1'")
	public Ausruestung ausruestung_;
	
	@Override
	public String getName() {
		return waffenName_;
	}
	
	@PrePersist
	public void onCreate()	{
		if(waffenName_ == null)	{
			waffenName_="Deus Ex Machina";
		}
	}
}
