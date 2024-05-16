package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.CodeDepartementInconnuException;
import fr.diginamic.recensement.exceptions.InputException;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws InputException, CodeDepartementInconnuException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		throwIfCodeDepartementDontExist(choix, rec, "Ce département n'existe pas");

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		throwIfNotNumeric(saisieMin, "Saisie max doit être un nombre");

		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();
		throwIfNotNumeric(saisieMax, "Saisie max doit être un nombre");

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;

		throwIfNotPositive(min, max);

		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
	}
}
