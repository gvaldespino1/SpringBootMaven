/**
 * @(#) ImUsedException.java
 *
 * Project: PioneerService
 * Title: ImUsedException.java
 * Description:
 * Copyright: Copyright(c)
 * @author: Gustavo Valdespino 
 * @version 1.0
 */
package com.pioneer.intel.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when there is a duplicate record in the DB.
 */
@SuppressWarnings("serial")
public class ImUsedException extends PioneerException {


	public static final String MESSAGE_IM_USED = "The record [{0}] is duplicated in the database.";

	/**
	 * 
	 * @param functionality
	 * @param codeDescription
	 */
	public ImUsedException(final String functionality, final String codeDescription) {
		super(functionality, getMessageUser(functionality, MESSAGE_IM_USED, null), HttpStatus.IM_USED.value(),
			codeDescription);
	}
}