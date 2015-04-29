package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import model.interfaces.DBObject;

@Entity
@Table(name = "GRUPPEN")
public class Gruppe implements DBObject {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public int ID_;
	@Column(name = "NAME", columnDefinition =" VARCHAR(30) NOT NULL DEFAULT 'Montags Gruppe'")
	public String name_;

	@Override
	public String toString() {
		return name_;
	}
}
