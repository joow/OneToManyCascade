package org.domaine;

import java.math.RoundingMode;
import java.text.NumberFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(name = "UK_LIGNECOMPOSITION", columnNames = "NBPIERRES, NATUREPIERRE_ID, FORMEPIERRE_ID, DIMENSIONPIERRE_ID, POIDSTOTALCARAT"))
public class LigneComposition {
	@Id
	@GeneratedValue
	@SuppressWarnings("unused")
	private int id;

	@Column(nullable = false)
	private int nbPierres;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	private NaturePierre naturePierre;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	private FormePierre formePierre;

	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	private DimensionPierre dimensionPierre;

	@Column(nullable = false)
	private double poidsTotalCarat;

	static class Builder {
		private int nbPierres;

		private NaturePierre naturePierre;

		private FormePierre formePierre;

		private DimensionPierre dimensionPierre;

		private double poidsTotalCarat;

		Builder nbPierres(int nbPierres) {
			this.nbPierres = nbPierres;
			return this;
		}

		Builder naturePierre(NaturePierre naturePierre) {
			this.naturePierre = naturePierre;
			return this;
		}

		Builder formePierre(FormePierre formePierre) {
			this.formePierre = formePierre;
			return this;
		}

		Builder dimensionPierre(DimensionPierre dimensionPierre) {
			this.dimensionPierre = dimensionPierre;
			return this;
		}

		Builder poidsTotalCarat(double poidsTotalCarat) {
			this.poidsTotalCarat = poidsTotalCarat;
			return this;
		}

		LigneComposition build() {
			LigneComposition ligneComposition = new LigneComposition();
			ligneComposition.nbPierres = nbPierres;
			ligneComposition.naturePierre = naturePierre;
			ligneComposition.formePierre = formePierre;
			ligneComposition.dimensionPierre = dimensionPierre;
			ligneComposition.poidsTotalCarat = poidsTotalCarat;

			return ligneComposition;
		}
	}

	String genererLibelle() {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMinimumFractionDigits(1);
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setRoundingMode(RoundingMode.DOWN);
		String poidsTotalCaratTronque = numberFormat.format(poidsTotalCarat);

		return String.format("%d %s - %s %s : %sct.%n", nbPierres,
				naturePierre.getLibelle(), formePierre.getLibelle(),
				dimensionPierre.getLibelle(), poidsTotalCaratTronque);
	}

	void modifier(LigneComposition ligneComposition) {
		nbPierres = ligneComposition.nbPierres;
		naturePierre = ligneComposition.naturePierre;
		formePierre = ligneComposition.formePierre;
		dimensionPierre = ligneComposition.dimensionPierre;
		poidsTotalCarat = ligneComposition.poidsTotalCarat;
	}
}
