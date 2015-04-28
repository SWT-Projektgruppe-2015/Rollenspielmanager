package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "GRUPPEN")
public class Gruppe {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME", columnDefinition =" VARCHAR(30) NOT NULL DEFAULT 'Montags Gruppe'")
	String name_;
}
