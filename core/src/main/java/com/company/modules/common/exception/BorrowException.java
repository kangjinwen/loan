package com.company.modules.common.exception;

import com.company.common.exception.BaseRuntimeException;

/**
 * 借贷异常
 * 
 * @author xx
 * @version 2.0
 * @since 2014年3月4日
 */
public class BorrowException extends BaseRuntimeException {

	private static final long serialVersionUID = -7400559552805824955L;

	public BorrowException() {
		super();
	}

	public BorrowException(String message) {
		super(message);
	}

	public BorrowException(String message, String url) {
		super(message, url);
	}

	public BorrowException(String message, int type) {
		super(message, type);
	}

}
