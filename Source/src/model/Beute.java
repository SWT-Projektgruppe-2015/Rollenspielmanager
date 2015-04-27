package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BEUTE")
public class Beute {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "PROFIL")
	public String profil_;
	@Column(name = "WAFFEN_WKT")
	public double waffenWkt_;
	@Column(name = "RUESTUNG_WKT")
	public double ruestungWkt_;
	@Column(name = "SCHWEACHUNGS_FAKTOR")
	public double schwaechungsFaktor_;
	@Column(name = "BESONDERES_WKT")
	public double besonderesWkt_;
	@Column(name = "GELDBETRAG")
	public int geldBetrag_;
}
