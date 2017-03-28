package de.iksgmbh.bdd.jgiven;

import de.iksgmbh.bdd.jgiven.domainobjects.Cook;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook.Experience;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen.Equipment;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch.Quality;;

public class LunchService {

	public Lunch produceLunch(Cook cook, Kitchen kitchen) 
	{
		if (cook.getExperience() == Experience.HIGH  && kitchen.getEquipment() == Equipment.EXTENDED)  {
			return new Lunch(Quality.EXCELLENT);
		}
		
		return new Lunch(Quality.POOR);
	}

}
