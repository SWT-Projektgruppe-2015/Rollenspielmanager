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
	@Column(name = "NAME", columnDefinition="VARCHAR(30) NOT NULL default 'Gegner Nr. 460'")
	public String name_;
	@Column(name = "KREIS", columnDefinition="INTEGER NOT NULL default '1' check(KREIS >= 1 and KREIS<=4)")
	public int kreis_;
	@Column(name = "STUFE", columnDefinition="INTEGER NOT NULL default '0' check(STUFE >= 0 and STUFE<=12)")
	public int stufe_;
	@Column(name = "DISZIPLIN")
	public String disziplin_;
	@Column(name = "RASSE")
	public String rasse_;
	@Column(name = "ERFAHRUNG", columnDefinition="INTEGER NOT NULL default '1' check(ERFAHRUNG >= 1)")
	public int erfahrung_;
	@Column(name = "STAERKE", columnDefinition="INTEGER NOT NULL default '1' check(STAERKE >= 1)")
	public int staerke_;
	@Column(name = "GESCHICK", columnDefinition="INTEGER NOT NULL default '1' check(GESCHICK >= 1)")
	public int geschick_;
	@OneToOne(optional=false)
	@JoinColumn(name = "BEUTETYP_ID", columnDefinition="INTEGER NOT NULL default '1'")
	public Beute beuteTyp;
	@OneToOne(optional=false)
	@JoinColumn(name = "AUSRUESTNGS_ID", columnDefinition="INTEGER NOT NULL default '1'")
	public Ausruestung ausruestung_;
	
	public void onCreate()	{
		if(name_ == null)	{
			name_="Gegner Nr. 460";
		}
		if(kreis_ == 0)	{
			kreis_ = 1;
		}
		if(erfahrung_ == 0)	{
			erfahrung_ = 1;
		}
		if(staerke_ == 0)	{
			staerke_ = 1;
		}
		if(geschick_ == 0)	{
			geschick_ = 1;
		}
	}
}
