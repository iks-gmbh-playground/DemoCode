package de.test;

public class CalcImpl implements IService {

	@Override
	public String calc() {
		return "Result from " + this.getClass().getSimpleName();
	}

}	
