/**
 * @(#) EncryptException.java
 *
 * Project: PioneerService
 * Title: EncryptException.java
 * Description:
 * Copyright: Copyright(c)
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.exception;

/**

 */
@SuppressWarnings("serial")
public class EncryptException extends Exception {
    
	/**

	 */
	public EncryptException() {
	}

	/**
	 * 
	 * @param message
	 */
	public EncryptException(String message) {
		super(message);
	}

	/**
	 * 
	 * @return object
	 */
	public String toString() {
		return super.toString();
	}
}