package com.company.modules.common.exception;

import com.company.common.exception.ErongBaseException;

/**
 * 参数解析Exception
 *
 */
public class ArgumentResolutionException extends ErongBaseException {
	
	private static final long serialVersionUID = 1L;

	public ArgumentResolutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArgumentResolutionException(String message) {
		super(message);
	}

}