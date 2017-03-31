package de.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import de.test.api.autogen.SayHelloRequest;
import de.test.api.utils.ValidationUtil;

public class ValidationUtilClassLevelTest {

	@Test
	public void test() {
		SayHelloRequest sayHelloRequest = new SayHelloRequest();
		
		String result = ValidationUtil.validateContractObject(sayHelloRequest);
		
		assertNotNull(result);
	}

}
