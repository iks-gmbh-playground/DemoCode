package de.test.api;

import de.test.api.autogen.ErrorType;
import de.test.api.autogen.HelloWorldServiceError;

/**
* Primary artifact of the Hello World Service.
*
* @author Reik Oberrath
*/
public class HelloWorldServiceException extends RuntimeException {

	private static final long serialVersionUID = 9101264716678773590L;

	private HelloWorldServiceError error;


	public HelloWorldServiceException(String message, Throwable exception, HelloWorldServiceError error) {
		super(message, exception);
		this.error = error;
	}

	public HelloWorldServiceException(String message) {
		super(message);
        this.error = new HelloWorldServiceError();
        this.error.setType(ErrorType.UNEXPECTED);
		this.error.setMessage(message);
	}
	
	public HelloWorldServiceException(String message, String type) {
        super(message);
        this.error = new HelloWorldServiceError();
        this.error.setMessage(message);
        this.error.setType(toErrorType(type));
    }

	private ErrorType toErrorType(String type) {
	   for (ErrorType errorType : ErrorType.values()) {
	        if (errorType.name().equals(type)) {
	            return errorType;
	        }
	    }
		return ErrorType.UNEXPECTED;
	}

	public HelloWorldServiceException(Throwable e) {
		super(e);
		this.error = new HelloWorldServiceError();
	}
	
	public HelloWorldServiceException(String message, Throwable e) {
		super(e);
		this.error = new HelloWorldServiceError();
		this.error.setMessage(message + " " + e.getMessage());
		this.error.setType(ErrorType.UNEXPECTED);
	}

	public HelloWorldServiceException(String message, String type, Throwable e) {
		super(e);
        this.error = new HelloWorldServiceError();
        this.error.setMessage(message);
        this.error.setType(toErrorType(type));
	}

	public HelloWorldServiceError getError() {
		return error;
	}

	public void setError(HelloWorldServiceError error) {
		this.error = error;
	}
}