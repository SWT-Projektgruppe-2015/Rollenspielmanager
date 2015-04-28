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
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public int ID_;
	@Column(name = "NAME", columnDefinition="VARCHAR(30) NOT NULL default 'Geheime Magie'")
	public String name_;
	@Column(name = "EFFEKT_TYP", columnDefinition="INTEGER NOT NULL default '0' check(EFFEKT_TYP >= 0 and EFFEKT_TYP <= 4)")
	public int effekttypen_;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition="INTEGER NOT NULL default '1'")
	public Ausruestung ausruestung_;
	
	@Override
	public String getName() {
		return name_;
	}
	
	public void onCreate()	{
		if(name_ == null)	{
			name_="Geheime Magie";
		}	
	}
}
