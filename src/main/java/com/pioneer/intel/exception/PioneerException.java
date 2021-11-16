/**
 * @(#) PioneerException.java
 *
 * Project: PioneerService
 * Title: PioneerException.java
 * Description: Throw an exception when there is a failure with some service.
 * @author:Gustavo Valdespino
 * @version 1.0
 */ 
package com.pioneer.intel.exception;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import com.pioneer.intel.dto.ErrorDTO; 

/**
 *This exception is thrown when there is a failure with some service.
 */
@SuppressWarnings("serial")
public class PioneerException extends Exception {

    // Fields
	private final String functionality;
    private final Integer codeResponse;
    private final String codeDescription;
    private final List<ErrorDTO> errorDTOs = new LinkedList<>();

	/**
	 * 
	 * @param functionality
	 * @param message
	 * @param codeResponse
	 * @param codeDescription
	 */
	public PioneerException(final String functionality, final String message, final Integer codeResponse,
			final String codeDescription) {
		super(message);
		this.codeResponse = codeResponse;
		this.codeDescription = codeDescription;
		this.functionality = functionality;
	}

	/**
	 * 
	 * @param functionality
	 * @param message
	 * @param codeResponse
	 * @param codeDescription
	 * @param errorDTOs
	 */
	public PioneerException(final String functionality, final String message, final Integer codeResponse,
			final String codeDescription, final List<ErrorDTO> errorDTOs) {
		super(message);
		this.functionality = functionality;
		this.codeResponse = codeResponse;
		this.codeDescription = codeDescription;
		this.errorDTOs.addAll(errorDTOs);
	}

	/**
	 * Method that obtains the message that will be shown to the end user.
	 * 
	 * @param functionality
	 * @param messageUser
	 * @param errorDTOs
	 * @return String
	 */
	protected static String getMessageUser(final String functionality, final String messageUser,
			final List<ErrorDTO> errorDTOs) {
		final StringBuilder params = new StringBuilder();
		if (errorDTOs != null && !errorDTOs.isEmpty()) { 
			for (final ErrorDTO errorDTO : errorDTOs) {
				params.append(errorDTO.toString());
			}
		}
		return MessageFormat.format(messageUser, functionality, params);
	}

	/**
	 *
	 * @return object
	 */
	@Override
	public String toString() {
		return "PaninisException [functionality=" + this.functionality + ", codeResponse=" + this.codeResponse
			+ ", codeDescription=" + this.codeDescription + "]";
	}
}