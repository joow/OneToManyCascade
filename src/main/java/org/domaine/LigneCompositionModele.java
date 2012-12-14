package org.domaine;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UK_LIGNECOMPOSITIONMODELE", columnNames = "MODELE_ID, ORDRE"))
@IdClass(LigneCompositionModele.LigneCompositionModeleId.class)
public class LigneCompositionModele {
	@Id
	@ManyToOne
	@SuppressWarnings("unused")
	private Modele modele;

	@Id
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private LigneComposition ligneComposition;

	@SuppressWarnings("unused")
	private int ordre;

	public LigneCompositionModele() {
	}

	public LigneCompositionModele(Modele modele,
			LigneComposition ligneComposition, int ordre) {
		this.modele = modele;
		this.ligneComposition = ligneComposition;
		this.ordre = ordre;
	}

	LigneComposition getLigneComposition() {
		return ligneComposition;
	}

	String genererLibelle() {
		return ligneComposition.genererLibelle();
	}

	void modifier(LigneComposition ligneComposition) {
		this.ligneComposition.modifier(ligneComposition);
	}

	static class LigneCompositionModeleId {
		@SuppressWarnings("unused")
		private int modele;

		@SuppressWarnings("unused")
		private int ligneComposition;
	}
}
