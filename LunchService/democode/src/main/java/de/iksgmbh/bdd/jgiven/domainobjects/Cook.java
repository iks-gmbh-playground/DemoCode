package de.iksgmbh.bdd.jgiven.domainobjects;

public class Cook 
{
	public enum Experience {HIGH, LOW}
	
	private String name;
	private Experience experience;

	public Cook(String aName, Experience aExperience) {
		name = aName;
		experience = aExperience;
	}

	@Override
	public String toString() {
		return "Cook [experience=" + experience + ", name=" + name + "]";
	}

	public String getName()  {
		return name;
	}

	public Experience getExperience()  {
		return experience;
	}
	
}
