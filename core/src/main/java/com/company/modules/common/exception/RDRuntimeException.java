package com.company.modules.common.exception;

import com.company.common.exception.BaseRuntimeException;

public class RDRuntimeException extends BaseRuntimeException{
	public RDRuntimeException() {
	}
	
	public RDRuntimeException(String message) {
		super(message);
	}
	
	public RDRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
