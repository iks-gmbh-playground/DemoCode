package com.iksgmbh.demo.hoo.api.utils;

/**
* Exception class used by all the hoo micro services.
*
* @author Reik Oberrath
*/
public class HOOServiceException extends RuntimeException 
{
	public enum HOOErrorType { MISSING_INPUT, INVALID_INPUT, UNEXPECTED };

	private static final long serialVersionUID = -1;

	private HOOErrorType errorType;
	private String concernedObject = "";


	public HOOServiceException(String message, 
			                   Throwable exception, 
			                   HOOErrorType errorType, 
			                   String concernedObject) 
	{
		super(message, exception);
		this.errorType = errorType;
		this.concernedObject = concernedObject;
	}

	public HOOServiceException(String message) {
		super(message);
		this.errorType = HOOErrorType.UNEXPECTED;
	}
	
	public HOOServiceException(String message, String type) {
        super(message);
		this.errorType = toErrorType(type);
    }

	private HOOErrorType toErrorType(String type) {
	   for (HOOErrorType errorType : HOOErrorType.values()) {
	        if (errorType.name().equals(type)) {
	            return errorType;
	        }
	    }
		return HOOErrorType.UNEXPECTED;
	}

	public HOOServiceException(Throwable e) {
		super(e);
		this.errorType = HOOErrorType.UNEXPECTED;
	}
	
	public HOOServiceException(String message, HOOErrorType errorType, String concernedObject) {
		super(message);
		this.errorType = errorType;
		this.concernedObject = concernedObject;
	}

	@Override
	public String getMessage() 
	{
		String type = "";
		if (errorType != null) {
			type = errorType.name(); 
		}
		
		return type + " " + concernedObject + "! Details: " + super.getMessage();
	}

	public HOOErrorType getErrorType() {
		return errorType;
	}

}