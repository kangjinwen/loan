package com.company.modules.common.exception;

import com.company.common.exception.ErongBaseException;

public class ObjectNotFoundException extends ErongBaseException{
	public ObjectNotFoundException() {
	}
	
	public ObjectNotFoundException(String message) {
		super(message);
	}
	
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
