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
 * with standard JGiven solution.
 * 
 * Statement methods without parameters and AS-Annotations. 
 * -> Report based on method names which is less readable as the AS-Texts with embedded parameters
 * 
 * @author Reik Oberrath
 */
public class ExcellentLunchIntegration_2_Test extends ScenarioTest<ExcellentLunchGivenStatement2, 
                                                                   ExcellentLunchWhenStatement2, 
                                                                   ExcellentLunchThenStatement2>
{
	@ScenarioState private LunchService lunchService = new LunchService();

    @Test
    public void goodLunchCreatesGoodFeeling()
    {
        given().aGoodCook()
               .aKitchenWithExcellentEquipment()
               .aLunchConsumerWithBigAppetite();

        when().lunchIsProduced()
              .lunchIsConsumed();

        then().lunchHasGoodQuality()
              .lunchConsumerHasGoodFeeling()
              .lunchConsumerHasSmallAppetite();
    }
}

class ExcellentLunchGivenStatement2 extends Stage<ExcellentLunchGivenStatement2>
{
	@ScenarioState private Cook cook;
	@ScenarioState private Kitchen kitchen;
	@ScenarioState private LunchConsumer lunchConsumer;
	
    public ExcellentLunchGivenStatement2 aGoodCook() {
		cook = new Cook("Bob", Experience.HIGH);
		return self();
	}

	public ExcellentLunchGivenStatement2 aKitchenWithExcellentEquipment() {
    	kitchen = new Kitchen(Equipment.EXTENDED);
        return self();
    }

    public ExcellentLunchGivenStatement2 aLunchConsumerWithBigAppetite() {
        lunchConsumer = new LunchConsumer("Meggy");
        lunchConsumer.setAppetite(Appetite.BIG);        
        return self();
      }
}


class ExcellentLunchWhenStatement2 extends Stage<ExcellentLunchWhenStatement2> {

	// input fields (TODO @ExpectedScenarioState and remove this comment)
	@ScenarioState private Cook cook;
	@ScenarioState private Kitchen kitchen;
	@ScenarioState private LunchConsumer lunchConsumer;
	@ScenarioState private LunchService lunchService;
	
	// output fields  (TODO @ProvidedScenarioState and remove this comment)
	@ScenarioState private Lunch lunch;
	@ScenarioState private Feeling feeling;
	
    public ExcellentLunchWhenStatement2 lunchIsProduced() {
    	lunch = lunchService.produceLunch(cook, kitchen);
    	return self();
    }

    public ExcellentLunchWhenStatement2 lunchIsConsumed() {
		feeling = lunchConsumer.consumes(lunch);
    	return self();
	}
}

class ExcellentLunchThenStatement2 extends Stage<ExcellentLunchThenStatement2> {

	@ScenarioState private LunchConsumer lunchConsumer;
	@ScenarioState private Lunch lunch;
	@ScenarioState private Feeling feeling;
	
	public ExcellentLunchThenStatement2 lunchHasGoodQuality() {
        assertEquals("Lunch", Quality.EXCELLENT, lunch.getQuality());
    	return self();
	}
	
	public ExcellentLunchThenStatement2 lunchConsumerHasGoodFeeling() {
		assertEquals("Feeling", Feeling.GOOD, feeling);
    	return self();
	}

	public ExcellentLunchThenStatement2 lunchConsumerHasSmallAppetite() {
        assertEquals("Appetite", Appetite.SMALL, lunchConsumer.getAppetite());
    	return self();
	}

}
