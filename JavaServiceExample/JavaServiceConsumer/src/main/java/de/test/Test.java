package de.test;
import java.util.Iterator;
import java.util.ServiceLoader;


public class Test {

	public static void main(String[] args) 
	{
		System.out.println("Test:");
		Iterator<IService> iter = ServiceLoader.load(IService.class).iterator();
		while (iter.hasNext()) {
			IService impl = iter.next();
			System.out.println(impl.calc());
		}
	}
	
}
