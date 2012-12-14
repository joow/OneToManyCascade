package org.domaine;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DomaineTest {
	private static EntityManagerFactory emf;

	private static EntityManager em;

	@BeforeClass
	public static void initialiserEntityManager() {
		emf = Persistence.createEntityManagerFactory("test");
		em = emf.createEntityManager();
	}

	@AfterClass
	public static void fermerEntityManager() {
		em.close();
		emf.close();
	}

	@Test
	public void creerCompositionPuisSupprimerUneLigne() {
		NaturePierre naturePierre1 = new NaturePierre("Nature 1");
		NaturePierre naturePierre2 = new NaturePierre("Nature 2");
		NaturePierre naturePierre3 = new NaturePierre("Nature 3");
		FormePierre formePierre1 = new FormePierre("Forme 1");
		FormePierre formePierre2 = new FormePierre("Forme 2");
		FormePierre formePierre3 = new FormePierre("Forme 3");
		DimensionPierre dimensionPierre1 = new DimensionPierre("Dimension 1");
		DimensionPierre dimensionPierre2 = new DimensionPierre("Dimension 2");
		DimensionPierre dimensionPierre3 = new DimensionPierre("Dimension 3");
		LigneComposition ligneComposition1 = new LigneComposition.Builder()
				.nbPierres(1).naturePierre(naturePierre1)
				.formePierre(formePierre1).dimensionPierre(dimensionPierre1)
				.poidsTotalCarat(0.1).build();
		LigneComposition ligneComposition2 = new LigneComposition.Builder()
				.nbPierres(2).naturePierre(naturePierre2)
				.formePierre(formePierre2).dimensionPierre(dimensionPierre2)
				.poidsTotalCarat(0.2).build();
		LigneComposition ligneComposition3 = new LigneComposition.Builder()
				.nbPierres(3).naturePierre(naturePierre3)
				.formePierre(formePierre3).dimensionPierre(dimensionPierre3)
				.poidsTotalCarat(0.3).build();

		List<LigneComposition> premiereComposition = Arrays.asList(
				ligneComposition1, ligneComposition2, ligneComposition3);

		Modele modele = new Modele();
		modele.mettreAJourComposition(premiereComposition);

		em.getTransaction().begin();
		em.persist(modele);
		em.getTransaction().commit();

		assertEquals(String.format("%s%n%s%n%s%n",
				"1 Nature 1 - Forme 1 Dimension 1 : 0,1ct.",
				"2 Nature 2 - Forme 2 Dimension 2 : 0,2ct.",
				"3 Nature 3 - Forme 3 Dimension 3 : 0,3ct."),
				modele.getLibelleComposition());

		List<LigneComposition> secondeComposition = Arrays.asList(
				ligneComposition1, ligneComposition3);

		em.getTransaction().begin();
		modele.mettreAJourComposition(secondeComposition);
		em.getTransaction().commit();

		assertEquals(String.format("%s%n%s%n",
				"1 Nature 1 - Forme 1 Dimension 1 : 0,1ct.",
				"3 Nature 3 - Forme 3 Dimension 3 : 0,3ct."),
				modele.getLibelleComposition());
	}
}
