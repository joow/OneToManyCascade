package org.domaine;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Modele {
	@Id
	@GeneratedValue
	@SuppressWarnings("unused")
	private int id;

	@OneToMany(mappedBy = "modele", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("ordre ASC")
	private List<LigneCompositionModele> lignesCompositionModele;
	
	void mettreAJourComposition(List<LigneComposition> lignesComposition) {
		int ordre = 1;
		for (LigneComposition ligneComposition : lignesComposition) {
			ajouterLigneComposition(ligneComposition, ordre++);
		}
		
		ajusterTailleListeCompositionModele(lignesComposition.size());
	}

	private void ajouterLigneComposition(LigneComposition ligneComposition, int ordre) {
		if (lignesCompositionModele == null) {
			lignesCompositionModele = new ArrayList<LigneCompositionModele>();
		}

		LigneCompositionModele ligneCompositionModele = (lignesCompositionModele
				.size() < ordre) ? null : lignesCompositionModele
				.get(ordre - 1);

		if (ligneCompositionModele == null) {
			lignesCompositionModele.add(new LigneCompositionModele(this,
					ligneComposition, ordre));
		} else {
			if (!ligneCompositionModele.getLigneComposition().equals(
					ligneComposition)) {
				ligneCompositionModele.modifier(ligneComposition);
			}
		}
	}
	
	private void ajusterTailleListeCompositionModele(int nouvelleTaille) {
		if (lignesCompositionModele.size() > nouvelleTaille) {
			for (int i = lignesCompositionModele.size(); i > nouvelleTaille; i--) {
				lignesCompositionModele.remove(i - 1);
			}
		}
	}

	String getLibelleComposition() {
		StringBuilder stringBuilder = new StringBuilder();

		for (LigneCompositionModele ligneCompositionModele : lignesCompositionModele) {
			stringBuilder.append(ligneCompositionModele.genererLibelle());
		}

		return stringBuilder.toString();
	}
}
