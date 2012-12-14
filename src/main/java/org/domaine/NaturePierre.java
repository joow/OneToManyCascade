package org.domaine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NaturePierre {
	@Id
	@GeneratedValue
	@SuppressWarnings("unused")
	private int id;

	@Column(nullable = false)
	private String libelle;

	public NaturePierre() {

	}

	public NaturePierre(String libelle) {
		this.libelle = libelle;
	}

	String getLibelle() {
		return libelle;
	}
}
