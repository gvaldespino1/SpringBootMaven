/**
 * @(#) InternalServerErrorException.java
 *
 * Project: PioneerService
 * Title: InternalServerErrorException.java 
 * Description: Throws an exception when there is a connectivity error with the DB
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.exception;

import org.springframework.http.HttpStatus;

/**
 *This exception is thrown when there is a connectivity error with the DB
 */
@SuppressWarnings("serial") 
public class InternalServerErrorException extends PioneerException {


	public static final String MESSAGE_INTERNAL_SERVER = "Unexpected error has ocurred. Please try again.";

	/**
	 * 
	 * @param functionality
	 * @param codeDescription
	 */
	public InternalServerErrorException(final String functionality, final String codeDescription) {
		super(functionality, MESSAGE_INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR.value(), codeDescription);
	}
}