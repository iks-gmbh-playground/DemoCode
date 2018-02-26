package de.iksgmbh.bdd.jgiven.___given;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.Hidden;
import com.tngtech.jgiven.annotation.ScenarioState;

import de.iksgmbh.bdd.jgiven.LunchConsumer;
import de.iksgmbh.bdd.jgiven.LunchConsumer.Appetite;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook.Experience;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen.Equipment;

/**
 * Statement class for the Given stage of JGiven.
 * 
 * @author Reik Oberrath
 */
public class LunchGivenStatements extends Stage<LunchGivenStatements>
{
	@ScenarioState private Cook cook;
	@ScenarioState private Kitchen kitchen;
	@ScenarioState private LunchConsumer lunchConsumer;
	
    @As( "a good cook with $ experience and" )
    public LunchGivenStatements aCookWithExperience(Experience aExperience) {
    	assertNotNull(aExperience);
		cook = new Cook("Bob", aExperience);
		return self();
	}

    @As( "a kitchen with $ equipment and" )
	public LunchGivenStatements aKitchenWithEquipment(Equipment aEquipment) {
    	assertNotNull(aEquipment);
    	kitchen = new Kitchen(aEquipment);
        return self();
    }

    @As( "a lunchConsumer with a $ appetite." )
	public LunchGivenStatements aLunchConsumerWithAppetite(Appetite aAppetite) {
    	assertNotNull(aAppetite);
        lunchConsumer = new LunchConsumer("Meggy");
        lunchConsumer.setAppetite(aAppetite);        
        return self();
      }
    
    @Hidden
	public LunchGivenStatements assureLunchConsumerHasNoFeelingYet() {
    	assertNull(lunchConsumer.getFeeling());
        return self();
	}
	
    @As( " " )
	public LunchGivenStatements addEmptyLineInReport() {
        return self();
	}

	
}
