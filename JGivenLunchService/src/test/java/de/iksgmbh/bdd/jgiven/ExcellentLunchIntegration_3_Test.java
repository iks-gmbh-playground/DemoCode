package de.iksgmbh.bdd.jgiven;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import de.iksgmbh.bdd.jgiven.LunchConsumer.Appetite;
import de.iksgmbh.bdd.jgiven.LunchConsumer.Feeling;
import de.iksgmbh.bdd.jgiven.___given.LunchGivenStatements;
import de.iksgmbh.bdd.jgiven.__when.LunchWhenStatements;
import de.iksgmbh.bdd.jgiven._then.LunchThenStatements;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook.Experience;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen.Equipment;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch.Quality;

/**
 * Tests interaction of lunchService and lunchConsumer
 * with extended JGiven solution.
 * 
 * @author Reik Oberrath
 */
public class ExcellentLunchIntegration_3_Test extends ScenarioTest<LunchGivenStatements, 
                                                                   LunchWhenStatements, 
                                                                   LunchThenStatements>
{
	@ScenarioState private LunchService lunchService = new LunchService();

    @Test
    @As( "Good Lunch Scenario" )
    public void goodLunchCreatesGoodFeeling()
    {
        given().aCookWithExperience(Experience.HIGH)
               .aKitchenWithEquipment(Equipment.EXTENDED)
               .aLunchConsumerWithAppetite(Appetite.BIG);

        when().lunchIsProduced()
              .lunchIsConsumed();

        then().lunchHasQuality(Quality.EXCELLENT)
              .lunchConsumerHasFeeling(Feeling.GOOD)
              .lunchConsumerHasAppetite(Appetite.SMALL);
        
        assertEquals("", "");
    }
}

