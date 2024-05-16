package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.InputException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées d'une région
 * donnée
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesRegion extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws InputException, NoSuchElementException {

		System.out.println("Veuillez saisir un nom de région:");
		String nomRegion = scanner.nextLine();
		validateConditionExists(ville -> ville.getNomRegion().equals(nomRegion), recensement, nomRegion);

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();
		throwIfNotNumeric(nbVillesStr, "Ce n'est pas un nombre");
		int nbVilles = Integer.parseInt(nbVillesStr);
		throwIfNotPositive(nbVilles);

		List<Ville> villesRegions = new ArrayList<Ville>();

		List<Ville> villes = recensement.getVilles();
		for (Ville ville : villes) {
			if (ville.getNomRegion().toLowerCase().startsWith(nomRegion.toLowerCase())) {
				villesRegions.add(ville);
			}
		}

		Collections.sort(villesRegions, new EnsemblePopComparateur(false));
		System.out.println("Les " + nbVilles + " villes les plus peuplées de la région " + nomRegion + " sont :");
		if (villesRegions.size() > 0) {
			for (int i = 0; i < nbVilles; i++) {
				Ville ville = villesRegions.get(i);
				System.out.println(ville.getNom() + " : " + ville.getPopulation() + " habitants.");
			}
		}

	}

}
