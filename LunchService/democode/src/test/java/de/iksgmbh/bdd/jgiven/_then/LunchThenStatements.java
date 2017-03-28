package de.iksgmbh.bdd.jgiven._then;

import static org.junit.Assert.*;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ScenarioState;

import de.iksgmbh.bdd.jgiven.LunchConsumer;
import de.iksgmbh.bdd.jgiven.LunchConsumer.Appetite;
import de.iksgmbh.bdd.jgiven.LunchConsumer.Feeling;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch.Quality;

/**
 * Statement class for the Then stage of JGiven.
 * 
 * @author Reik Oberrath
 */
public class LunchThenStatements extends Stage<LunchThenStatements> {

	@ScenarioState private LunchConsumer lunchConsumer;
	@ScenarioState private Lunch lunch;
	@ScenarioState private Feeling feeling;
	
    @As( "lunch is $ and" )
	public LunchThenStatements lunchHasQuality(Quality aQuality) {
    	assertNotNull(aQuality);
    	assertNotNull(lunch);
        assertEquals("Lunch", aQuality, lunch.getQuality());
    	return self();
	}
	
    @As( "lunchConsumer has a $ feeling and" )   
	public LunchThenStatements lunchConsumerHasFeeling(Feeling expectedFeeling) {
    	assertNotNull(expectedFeeling);
    	assertNotNull(feeling);
    	assertEquals("Feeling", expectedFeeling, feeling);
    	return self();
	}

    @As( "now a $ appetite! ")
	public LunchThenStatements lunchConsumerHasAppetite(Appetite aAppetite) {
    	assertNotNull(aAppetite);
    	assertNotNull(lunchConsumer);
        assertEquals("Appetite", aAppetite, lunchConsumer.getAppetite());
    	return self();
	}

}