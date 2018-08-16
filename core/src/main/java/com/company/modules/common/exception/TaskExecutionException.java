package com.company.modules.common.exception;

import com.company.common.exception.ErongBaseException;

public class TaskExecutionException extends ErongBaseException{
	public TaskExecutionException() {
	}

	public TaskExecutionException(String message) {
		super(message);
	}

	public TaskExecutionException(String message, Throwable cause) {
		super(message, cause);
	}
}
