package de.iksgmbh.bdd.jgiven.domainobjects;

public class Kitchen {

	public enum Equipment {EXTENDED, BASIC};
	
	private Equipment equipment;
	
	public Kitchen(Equipment aEquipment) {
		equipment = aEquipment;
	}

	public Equipment getEquipment()  {
		return equipment;
	}

	@Override
	public String toString() {
		return "Kitchen [equipment=" + equipment + "]";
	}
	
}
