package de.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;

import de.test.api.utils.XMLGregorianCalendarUtil;

/**
 * Low level tests on which the high level tests of other components
 * depend on.
 * 
 * @author Reik Oberrath
 */
public class XMLGregorianCalendarUtilClassLevelClassLevelTest {

	// #############################################################################
	//                        Unit tests on class level
	// #############################################################################
	
	@Test
	public void returnsDateForXmlGregorianCalendar() throws DatatypeConfigurationException
	{
		// arrange
		Date now = new Date();
		// act
    	Date result = XMLGregorianCalendarUtil.toDate( XMLGregorianCalendarUtil.toGregorianCaldendar(now) );
    	
    	//assert
    	assertEquals("result", now.getTime(), result.getTime());
	}
	
	@Test
	public void returnsNullForNullXmlGregorianCalendar() throws DatatypeConfigurationException
	{
    	Date result = XMLGregorianCalendarUtil.toDate(null);
    	assertNull(result);
	}
}
