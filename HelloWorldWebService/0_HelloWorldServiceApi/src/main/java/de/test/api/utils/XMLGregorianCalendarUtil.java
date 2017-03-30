package de.test.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Helper for easy handling of date values.
 * @author Reik Oberrath
 */
public class XMLGregorianCalendarUtil {

	public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
	private static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);
    	
    public static XMLGregorianCalendar toGregorianCaldendar(final String dateString) throws ParseException {
    	return toGregorianCaldendar(dateString, DEFAULT_DATE_FORMATTER);
    }

    public static XMLGregorianCalendar toGregorianCaldendar(final String dateString, 
    		                                                final SimpleDateFormat formatter) 
    		                                                	  throws ParseException 
    {
    	if (dateString == null || dateString.trim().length() == 0) {
    		return null;
    	}
    	
    	final Date toReturn = formatter.parse(dateString);
    	
    	return toGregorianCaldendar(toReturn);
    }	
	
	public static XMLGregorianCalendar toGregorianCaldendar(final Date date) {
	    if (date != null)
	    {
	    	GregorianCalendar c = new GregorianCalendar();
	    	c.setTime(date);
	    	try {
				return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
	    }
	    
	    return null;
	}
	
	public static Date toDate(final XMLGregorianCalendar xmlGregorianCalendar) {
		if (xmlGregorianCalendar != null)  {
			return xmlGregorianCalendar.toGregorianCalendar().getTime();
		}
		return null;
	}
	
	
	
}
