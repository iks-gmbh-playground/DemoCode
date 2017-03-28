package de.iksgmbh.bdd.jgiven;

import de.iksgmbh.bdd.jgiven.domainobjects.Lunch;
import de.iksgmbh.bdd.jgiven.domainobjects.Lunch.Quality;

public class LunchConsumer {
	
	@Override
	public String toString() {
		return "LunchConsumer [name=" + name + ", appetite=" + appetite + "]";
	}

	public enum Feeling {GOOD, BAD};
	public enum Appetite{BIG, SMALL}


	private String name;
	private Appetite appetite;
	private Feeling feeling;

	public LunchConsumer(String aName) {
		name = aName;
	}


	public String getName()  {
		return name;
	}

	public Appetite getAppetite()  {
		return appetite;
	}

	public Feeling getFeeling()  {
		return feeling;
	}
	
	public void setAppetite(Appetite aAppetite)  {
		appetite = aAppetite;
	}

	public Feeling consumes(Lunch lunch) 
	{
		Feeling toReturn = Feeling.BAD; 
		
		if (lunch.getQuality() == Quality.EXCELLENT && appetite == Appetite.BIG)  {
			toReturn = Feeling.GOOD;
		}
		
		appetite = Appetite.SMALL;
		feeling = toReturn;
		return toReturn;
	}
	
}
