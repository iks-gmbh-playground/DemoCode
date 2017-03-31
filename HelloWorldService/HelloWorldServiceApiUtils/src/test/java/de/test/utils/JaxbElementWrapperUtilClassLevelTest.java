package de.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import de.test.api.autogen.SayHelloRequest;
import de.test.api.utils.JaxbElementWrapperUtil;

public class JaxbElementWrapperUtilClassLevelTest {

	@Test
	public void returnsFalseForAContractObjectWhichIsNoElement() {
		SayHelloRequest sayHelloRequest = new SayHelloRequest();

		boolean result = JaxbElementWrapperUtil.isContractObjectAnRootElement(sayHelloRequest);
		
		assertEquals("result", false, result);
	}

}
