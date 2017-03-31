package de.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Unit tests for the low level methods of this component.
 *   
 * @author Reik Oberrath
 */
public class CommentStringUtilsClassLevelTest {

	@Test
	public void returnsNullForNullInput() 
	{
		String result = CommentStringUtils.appendStrings(null);
		assertNull(result);
	}

	@Test
	public void returnsEmptyStringForNullContentArray() 
	{
		String[] nullContentArray = { null };
		String result = CommentStringUtils.appendStrings(nullContentArray);
		assertNotNull(result);
		assertEquals("result", "", result);
	}
	
	@Test
	public void returnsSingleInputValue() 
	{
		String result = CommentStringUtils.appendStrings("1");
		assertNotNull(result);
		assertEquals("result", "1", result);
	}
	
	@Test
	public void returnsSumOfTwoInputValues() 
	{
		String result = CommentStringUtils.appendStrings("1", "2");
		assertNotNull(result);
		assertEquals("result", "12", result);
	}
	
	@Test
	public void returnsSumOfFiveInputValues() 
	{
		String result = CommentStringUtils.appendStrings("1", "2", "3", "4", "5");
		assertNotNull(result);
		assertEquals("result", "12345", result);
	}
	
}
