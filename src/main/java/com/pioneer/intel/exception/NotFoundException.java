/**
 * @(#) NotFoundException.java
 *
 * Project: PioneerService
 * Title: NotFoundException.java
 * Description: Throw an exception when a record does not exist in the Database.
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.pioneer.intel.dto.ErrorDTO;

/**
* This exception is thrown when a record does not exist in the Database.
 */
@SuppressWarnings("serial")
public class NotFoundException extends PioneerException {


	public static final String MESSAGE_NOT_FOUND = "Sorry, we can't find the record [{0}]. "
		+ "For the following parameters used: [{1}].";

	/**
	 * 
	 * @param functionality
	 * @param message
	 * @param codeDescription
	 */
	public NotFoundException(final String functionality, final String message, final String codeDescription) {
		super(functionality, message, HttpStatus.NOT_FOUND.value(), codeDescription);
	}

	/**
	 * @param functionality
	 * @param codeDescription
	 * @param errorDTOs
	 */
	public NotFoundException(final String functionality, final String codeDescription, final List<ErrorDTO> errorDTOs) {
		super(functionality, getMessageUser(functionality, MESSAGE_NOT_FOUND, errorDTOs), HttpStatus.NOT_FOUND.value(),
			codeDescription, errorDTOs);
	}
}