package de.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import de.test.api.autogen.SayHelloRequest;
import de.test.api.utils.ContractObjectToStringUtil;

public class ContractObjectToStringUtilClassLevelTest {

	@Test
	public void returnsStringRepresentation() {
		SayHelloRequest sayHelloRequest = new SayHelloRequest();
		String result = ContractObjectToStringUtil.toString(sayHelloRequest);
		assertTrue(result.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
		assertTrue(result.contains("<SayHelloRequest xmlns=\"http://test.de/api/\"/>"));
		assertEquals("Result length", 103, result.length());
	}

}
