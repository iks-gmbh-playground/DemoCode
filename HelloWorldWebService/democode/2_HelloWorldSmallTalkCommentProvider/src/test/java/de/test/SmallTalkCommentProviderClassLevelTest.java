package de.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * End-To-End tests for this high level method of this component.
 *   
 * @author Reik Oberrath
 */
public class SmallTalkCommentProviderClassLevelTest {

	private SmallTalkCommentProvider classUnterTest;
	
	@Before
	public void setup() {
		classUnterTest = new SmallTalkCommentProvider();
	}
	
	@Test
	public void returnsEmptyStringForNullInput() 
	{
		String result = classUnterTest.getComment(null);
		assertEquals("result", "", result);
	}
	
	@Test
	public void returnsEmptyStringForEmptyStringInput() 
	{
		String result = classUnterTest.getComment("");
		assertEquals("result", "", result);
	}
	
	@Test
	public void returnsDefaultCommentForUnkownName() 
	{
		final String name = "unkown"; 
		String result = classUnterTest.getComment(name);
		assertEquals("result", name + SmallTalkCommentProvider.DEFAULT_COMMENT_STUB, result);
	}

	@Test
	public void returnsCommentForKnownName() 
	{
		final String name = "Angela"; 
		String result = classUnterTest.getComment(name);
		assertEquals("result", SmallTalkCommentProvider.KNOWN_NAMES.get(name), result);
	}
	
}

