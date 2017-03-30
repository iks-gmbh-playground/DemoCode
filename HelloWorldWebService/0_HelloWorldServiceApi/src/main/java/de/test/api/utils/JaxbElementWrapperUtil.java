package de.test.api.utils;

import java.lang.annotation.Annotation;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Util to wrap complex type defined in the xsd with an JAXB element.
 * It is needed because the ContractObjectToStringUtil and the ValidationUtil
 * can only handle xsd elements, but the xsd contains complex types.
 * 
 * @author Reik Oberrath
 */
public class JaxbElementWrapperUtil 
{
    private static final String DEFAULT_NAMESPACE = "http://test.de/api/";

    /**
     * Wraps a complex type defined in the xsd by an element wrapper.
     * 
     * @param complexType type to wrap
     * @param namespace to be used for the wrapping
     * @return element (wrapped type)
     */
	@SuppressWarnings("unchecked")
	public static <T> JAXBElement<T> createRootElementForType(T complexType, String namespace) {
		final Class<T> clazz = (Class<T>) complexType.getClass();
		QName qname = new QName(namespace, complexType.getClass().getSimpleName());
        return new JAXBElement<T>(qname,  clazz, null, (T) complexType);
	}
	
    /**
     * Wraps a complex type defined in the xsd by an element wrapper.
     * 
     * @param complexType type to wrap
     * @return element (wrapped type)
     */
	public static <T> JAXBElement<T> createRootElementForType(T complexType) {
        return createRootElementForType(complexType, DEFAULT_NAMESPACE);
	}
	

	/**
	 * Root elements are defined in the xsd as an element!
	 * JAXB will generate the xsd definition into a java class
	 * which will be annotated as RootElement.
	 *  
	 * @param contractObject
	 * @return true if the RootElement-annotation is found 
	 *         in the class description of the contract object
	 */
	public static boolean isContractObjectAnRootElement(Object contractObject) {
    	final Annotation[] annotations = contractObject.getClass().getAnnotations();
    	for (Annotation annotation : annotations) {
			if (annotation.toString().contains("Element")) {
				return true;
			}
		}		
    	return false;
	}
	
}
