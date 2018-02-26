package de.iksgmbh.bdd.jgiven;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
 * without JGiven to compare it with a JGiven implementation
 * 
 * Disadvantages of this kind of integration test:
 * 1. Tested functionality hard to read for non-developers
 * 2. JUnit Result hard to read for non-developers
 * 3. Noise in the code (e.g. "Bob" only needed to instantiate a cook) makes test description hard to read.
 * 
 * @author Reik Oberrath
 */
public class ExcellentLunchIntegration_0_Test
{
    private LunchService lunchService = new LunchService();
    private LunchConsumer lunchConsumer = new LunchConsumer("Meggy");

    @Test
    public void goodLunchCreatesGoodFeeling()
    {
        // arrange
        Cook cook = new Cook("Bob", Experience.HIGH);
        Kitchen kitchen = new Kitchen(Equipment.EXTENDED);
        lunchConsumer.setAppetite(Appetite.BIG);

        // act
        Lunch lunch = lunchService.produceLunch(cook, kitchen);
        Feeling feeling = lunchConsumer.consumes(lunch);

        // assert
        assertEquals("Lunch", Quality.EXCELLENT, lunch.getQuality());
        assertEquals("Feeling", Feeling.GOOD, feeling);
        assertEquals("Appetite", Appetite.SMALL, lunchConsumer.getAppetite());
    }
}
