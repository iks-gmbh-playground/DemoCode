package de.test.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import de.test.api.HelloWorld;
import de.test.api.HelloWorldServiceException;
import de.test.api.autogen.SayHelloRequest;

/**
 * Das klappt noch nicht, weil nur RootElements so validiert werden können.
 * Derzeit enthält aber die xsd nur complexe Typen, weil die Verheiratung
 * von der xsd mit der wsdl unter cxf das so erfordert.
 * 
 * @author Reik Oberrath
 */
public class ValidationUtil {

	private static final Logger LOGGER = Logger.getLogger(ValidationUtil.class.getName());
	private static final String XSD_FILE_LOCATION = "../HelloWorldServiceApi/src/main/resources/HelloWorldService.xsd";
	private static final SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	private static final File XSD_FILE = new File(XSD_FILE_LOCATION);

	public static String validateContractObject(Object contractObject)
	{
	    
	    try {
	    	
	    	final Object rootElement;
	    	if ( JaxbElementWrapperUtil.isContractObjectAnRootElement(contractObject) )  {
	    		rootElement = contractObject;
	    	} else {
	    		rootElement = JaxbElementWrapperUtil.createRootElementForType(contractObject);
	    	}
	    	
	    	final JAXBContext jaxbContext = JAXBContext.newInstance(contractObject.getClass());
	        final JAXBSource toValidate = new JAXBSource(jaxbContext, rootElement);        
			final Schema schema = SCHEMA_FACTORY.newSchema(getXsdFile()); 
		    final Validator validator = schema.newValidator();
		    validator.setErrorHandler(new ErrorHandlerImpl());
		    
			validator.validate(toValidate);
			LOGGER.log(Level.INFO, "Contract object is valid: " 
			                       + ContractObjectToStringUtil.toString(contractObject));
			return "";
		} catch (HelloWorldServiceException e) {
			return e.getMessage();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unerwarteter Fehler beim Validieren.", e);
			e.printStackTrace();
			return e.getMessage();
		}
    }
	
	private static File getXsdFile() throws FileNotFoundException {
	    if ( ! XSD_FILE.exists() ) {
			throw new FileNotFoundException("Could not find file: " + XSD_FILE.getAbsolutePath());
		}
		return XSD_FILE;
	}

	public static void main(String[] args) {
		SayHelloRequest request = new SayHelloRequest();
		System.out.println(validateContractObject(request));
	}

     static class ErrorHandlerImpl implements ErrorHandler {
		
        public void warning(SAXParseException spe) throws SAXException {
            throw new HelloWorldServiceException("Validation Error: " + getErrorMessage(spe.getMessage()));
        }

        public void error(SAXParseException spe) throws SAXException {
            throw new HelloWorldServiceException("Validation Error: " + getErrorMessage(spe.getMessage()));
        }

        public void fatalError(SAXParseException spe) throws SAXException {
            throw new HelloWorldServiceException("Validation Error: " + getErrorMessage(spe.getMessage()));
        }

		private String getErrorMessage(String message) {
			String toReturn = message.replace("{\"" + HelloWorld.NAMESPACE + "\":", "")
					                 .replace("}", "");
			
			final int pos = toReturn.indexOf(": ");
			
			return toReturn.substring(pos + 2);
		}
    }	
}
