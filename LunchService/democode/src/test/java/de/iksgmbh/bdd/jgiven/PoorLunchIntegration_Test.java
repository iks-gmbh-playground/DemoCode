package de.iksgmbh.bdd.jgiven;

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
 * concerning negative outcomes.
 * 
 * Features of JGiven not demonstrated here:
 * 1. Pimping the test flow (adding new custom stages)
 * 2. Pimping the result report (couple of was of alternativ formatting)
 * 3. Handling collection data types (Arrays, Lists, Tables)
 * 4. Executing subsets of tests (Using Tags)
 * 
 * @author Reik Oberrath
 */
public class PoorLunchIntegration_Test extends ScenarioTest<LunchGivenStatements, 
                                                           LunchWhenStatements, 
                                                           LunchThenStatements>
{
	@ScenarioState private LunchService lunchService = new LunchService();

    @Test
    @As( "Small appetite causes bad feeling in spite of excellent lunch." )
    public void smallAppetiteCausesBadFeeling()
    {
        given().aCookWithExperience(Experience.HIGH)
               .aKitchenWithEquipment(Equipment.EXTENDED)
               .aLunchConsumerWithAppetite(Appetite.SMALL)
               .assureLunchConsumerHasNoFeelingYet()
        	   .addEmptyLineInReport();

        when().lunchIsProduced()
              .lunchIsConsumed()
       	      .addEmptyLineInReport();

        then().lunchHasQuality(Quality.EXCELLENT)
              .lunchConsumerHasFeeling(Feeling.BAD);
    }

    @Test
    @As( "Basic kitchen equipment causes bad feeling in spite of cook with high experience and big appetite of lunch consumer." )
    public void basicKitchenEquipmentCausesPoorLunch()
    {
        given().aCookWithExperience(Experience.HIGH)
               .aKitchenWithEquipment(Equipment.BASIC)
               .aLunchConsumerWithAppetite(Appetite.BIG)
               .assureLunchConsumerHasNoFeelingYet()
        	   .addEmptyLineInReport();

        when().lunchIsProduced()
              .lunchIsConsumed()
       	      .addEmptyLineInReport();

        then().lunchHasQuality(Quality.POOR)
              .lunchConsumerHasFeeling(Feeling.BAD)
              .lunchConsumerHasAppetite(Appetite.SMALL);
    }

    @Test
    @As( "Low experience of cook causes bad feeling in spite of well equipped kitchen and big appetite of lunch consumer." )
    public void lowExperienceOfCookCausesPoorLunch()
    {
        given().aCookWithExperience(Experience.LOW)
               .aKitchenWithEquipment(Equipment.EXTENDED)
               .aLunchConsumerWithAppetite(Appetite.BIG)
               .assureLunchConsumerHasNoFeelingYet()
        	   .addEmptyLineInReport();
        	   
        when().lunchIsProduced()
              .lunchIsConsumed()
 	          .addEmptyLineInReport();

        then().lunchHasQuality(Quality.POOR)
              .lunchConsumerHasFeeling(Feeling.BAD)
              .lunchConsumerHasAppetite(Appetite.SMALL);
    }
}

