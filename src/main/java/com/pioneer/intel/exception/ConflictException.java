/**
 * @(#) ConflictException.java
 *
 * Project: PioneerService
 * Title: ConflictException.java
 * Description:
 * Copyright: Copyright(c) 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.exception;

import org.springframework.http.HttpStatus;

/**

 */
@SuppressWarnings("serial")
public class ConflictException extends PioneerException {


	public static final String MESSAGE_CONFLICT = "ERROR2.";

	/**

	 * 
	 * @param functionality
	 * @param codeDescription
	 */
	public ConflictException(final String functionality, final String codeDescription) {
		super(functionality, MESSAGE_CONFLICT, HttpStatus.CONFLICT.value(), codeDescription);
	}
}