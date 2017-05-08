package com.iksgmbh.demo.hoo.api.utils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Util to create a string representation for objects that are build from 
 * java classes generated by JAXB using the object definitions of a xsd file. 
 * This is necessary because JAXB does not generate a toString() method. 
 * 
 * @author Reik Oberrath
 */
public class JaxbToStringUtil 
{	
	private static final HashMap<Class<?>, Marshaller> marshallerMap = new HashMap<Class<?>, Marshaller>();
    private static final Logger LOG = Logger.getLogger(JaxbToStringUtil.class.getName());

	/**
	 * Uses a JAXB Marshaller to convert an object that is defined in the XSD
	 * into a String represention. 
	 *   
	 * @param contract object defined in the xsd 
	 * @return string representation with all field contents
	 */
	public static String toString(final Object contractObject)
	{
		if (contractObject == null) {
			return "";
		}
		
		try {
			final StringWriter sw = new StringWriter();		
			getMarshaller(contractObject.getClass()).marshal(contractObject, sw);
			return sw.toString();
		} catch (JAXBException e) {
			LOG.log(Level.WARNING, "Failed to build string representation "
					+ "with JAXB Marshaller for " + contractObject.toString(), e);
			return contractObject.toString();  // bad fall back
		}
	}

	private static Marshaller getMarshaller(Class<?> clazz) throws JAXBException  {
		if (! marshallerMap.containsKey(clazz))
		{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallerMap.put(clazz, marshaller);
		}
		return marshallerMap.get(clazz);
	}
}