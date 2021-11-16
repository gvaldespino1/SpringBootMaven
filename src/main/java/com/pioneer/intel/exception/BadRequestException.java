/**
 * @(#) BadRequestException.java
 *
 * Project: PioneerService 
 * Title:  BadRequestException.java
 * Description: 
 * Copyright: Copyright(c) 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.exception;

import org.springframework.http.HttpStatus;

/** 
 * Paramtehers Exception
 */
@SuppressWarnings("serial")
public class BadRequestException extends PioneerException {


	public static final String MESSAGE_BAD_REQUEST = "ERROR.";

	/**
	 * 
	 * @param functionality
	 * @param codeDescription
	 */
	public BadRequestException(final String functionality, final String codeDescription) {
		super(functionality, MESSAGE_BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), codeDescription);
	}
}