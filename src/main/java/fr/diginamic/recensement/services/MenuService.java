package fr.diginamic.recensement.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.CodeDepartementInconnuException;
import fr.diginamic.recensement.exceptions.InputException;

/**
 * Classe représentant un service
 * 
 * @author DIGINAMIC
 *
 */
public abstract class MenuService {

	/**
	 * Méthode abstraite de traitement que doivent posséder toutes les méthodes de
	 * services
	 * 
	 * @param lignes  lignes du fichier
	 * @param scanner scanner
	 */
	public abstract void traiter(Recensement recensement, Scanner scanner) throws InputException, CodeDepartementInconnuException, NoSuchElementException;

	protected void throwIfNotNumeric(String string, String messageIfError) throws InputException {
		if(!NumberUtils.isCreatable(string))
			throw new InputException(messageIfError);
	}

	protected void throwIfCodeDepartementDontExist(String codeDepartement, Recensement rec, String messageIfError) throws CodeDepartementInconnuException{
		List<Ville> cities = rec.getVilles();
		long knownCitesWith = cities.stream().filter(city -> city.getCodeDepartement().equals(codeDepartement)).count();

		if (knownCitesWith == 0) {
			throw new CodeDepartementInconnuException(messageIfError);
		}
	}

	protected void throwIfNotPositive(Integer... integers) throws InputException {
		for(Integer integer : integers){
			if(integer < 0)
				throw new InputException("Nombre saisi non positif");
		}
	}

	protected void validateConditionExists(Predicate<Ville> predicate, Recensement rec, String messageIfError) throws NoSuchElementException {
        List<Ville> cities = rec.getVilles();
        long knownCitiesWithCondition = cities.stream().filter(predicate).count();

        if (knownCitiesWithCondition == 0) {
            throw new NoSuchElementException(messageIfError);
        }
    }
}
