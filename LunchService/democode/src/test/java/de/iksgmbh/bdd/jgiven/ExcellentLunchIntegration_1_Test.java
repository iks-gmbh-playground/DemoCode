package de.iksgmbh.bdd.jgiven;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import de.iksgmbh.bdd.jgiven.LunchConsumer.Appetite;
import de.iksgmbh.bdd.jgiven.LunchConsumer.Feeling;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook.Experience;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen.Equipment;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch.Quality;

/**
 * Tests interaction of lunchService and lunchConsumer
 * with minimum JGiven solution.
 * 
 * No usage of SELF, no fluent api, no usage of @ScenarioState as domain object registry !
 * -> Report contains redundant key words given, when and then !
 * 
 * @author Reik Oberrath
 */
public class ExcellentLunchIntegration_1_Test extends ScenarioTest<ExcellentLunchGivenStatement1, 
                                                                   ExcellentLunchWhenStatement1, 
                                                                   ExcellentLunchThenStatement1>
{
    private LunchService lunchService = new LunchService();

    @Test
    public void goodLunchCreatesGoodFeeling()
    {
        // arrange
        Cook cook = given().aGoodCook();
        Kitchen kitchen = given().aKitchenWithExcellentEquipment();
        LunchConsumer lunchConsumer = given().aLunchConsumerWithBigAppetite();

        // act
        Lunch lunch = when().lunchIsProducedBy(lunchService, cook, kitchen);
        Feeling feeling = when().lunchIsConsumedBy(lunchConsumer, lunch);

        // assert
        then().lunchHasGoodQuality(lunch);
        then().lunchConsumerHasGoodFeeling(feeling);
        then().lunchConsumerHasSmallAppetite(lunchConsumer);
    }
}

class ExcellentLunchGivenStatement1 extends Stage<ExcellentLunchGivenStatement1>
{
    public Cook aGoodCook() {
		return new Cook("Bob", Experience.HIGH);
	}

	public Kitchen aKitchenWithExcellentEquipment() {
    	return new Kitchen(Equipment.EXTENDED);
    }

    public LunchConsumer aLunchConsumerWithBigAppetite() {
        LunchConsumer toReturn = new LunchConsumer("Meggy");
        toReturn.setAppetite(Appetite.BIG);
        return toReturn;
    }
}


class ExcellentLunchWhenStatement1 extends Stage<ExcellentLunchWhenStatement1> {

    public Lunch lunchIsProducedBy(LunchService lunchService, Cook cook, Kitchen kitchen) {
    	return lunchService.produceLunch(cook, kitchen);
    }

    public Feeling lunchIsConsumedBy(LunchConsumer lunchConsumer, Lunch lunch) {
		return lunchConsumer.consumes(lunch);
	}
}

class ExcellentLunchThenStatement1 extends Stage<ExcellentLunchThenStatement1> {

	public void lunchHasGoodQuality(Lunch lunch) {
        assertEquals("Lunch", Quality.EXCELLENT, lunch.getQuality());
	}
	
	public void lunchConsumerHasGoodFeeling(Feeling feeling) {
		assertEquals("Feeling", Feeling.GOOD, feeling);
	}

	public void lunchConsumerHasSmallAppetite(LunchConsumer lunchConsumer) {
        assertEquals("Appetite", Appetite.SMALL, lunchConsumer.getAppetite());
	}

}
