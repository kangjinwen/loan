package com.company.modules.common.exception;

import com.company.common.exception.ErongBaseException;

public class TaskNotFoundException extends ErongBaseException{
	public TaskNotFoundException() {
	}

	public TaskNotFoundException(String message) {
		super(message);
	}

	public TaskNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
