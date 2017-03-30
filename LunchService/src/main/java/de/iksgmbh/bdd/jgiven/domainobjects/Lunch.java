package de.iksgmbh.bdd.jgiven.domainobjects;

public class Lunch {

	public enum Quality {EXCELLENT, POOR}

	private Quality quality;
	
	public Lunch(Quality aQuality) {
		quality = aQuality;
	}

	public Quality getQuality() {
		return quality;
	}

	@Override
	public String toString() {
		return "Lunch [quality=" + quality + "]";
	}
	
}
