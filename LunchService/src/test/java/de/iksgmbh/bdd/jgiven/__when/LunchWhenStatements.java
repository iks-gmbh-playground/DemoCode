package de.iksgmbh.bdd.jgiven.__when;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import de.iksgmbh.bdd.jgiven.LunchConsumer;
import de.iksgmbh.bdd.jgiven.LunchConsumer.Feeling;
import de.iksgmbh.bdd.jgiven.LunchService;
import de.iksgmbh.bdd.jgiven.domainobjects.Cook;
import de.iksgmbh.bdd.jgiven.domainobjects.Kitchen;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch;

/**
 * Statement class for the When stage of JGiven.
 * 
 * @author Reik Oberrath
 */
public class LunchWhenStatements extends Stage<LunchWhenStatements> {

	// input fields used here, but created in other Statement classes
	@ProvidedScenarioState private Cook cook;
	@ProvidedScenarioState private Kitchen kitchen;
	@ProvidedScenarioState private LunchConsumer lunchConsumer;
	@ProvidedScenarioState private LunchService lunchService;
	
	// output fields created here and needed later for asserts
	@ExpectedScenarioState private Lunch lunch;
	@ExpectedScenarioState private Feeling feeling;
	
    @As( "lunch is cooked and" )
    public LunchWhenStatements lunchIsProduced() {
    	lunch = lunchService.produceLunch(cook, kitchen);
    	return self();
    }

    @As( "lunchConsumer eats lunch," )
    public LunchWhenStatements lunchIsConsumed() {
		feeling = lunchConsumer.consumes(lunch);
    	return self();
	}
    
    @As( " " )
	public LunchWhenStatements addEmptyLineInReport() {
        return self();
	}
    
}
