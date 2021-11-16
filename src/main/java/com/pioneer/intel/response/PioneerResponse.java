/**
 * @(#) PioneerResponse.java
 *
 * Project: PioneerWS
 * Title: PioneerResponse.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
 
package com.pioneer.intel.response;

import java.io.Serializable;

/**
 * Transporting Class for Services and Front End.
 *
 * @author Gustavo Valdespino
 * @package  com.pioneer.intel.response
 */
@SuppressWarnings("serial")
public class PioneerResponse<T> implements Serializable { 

	// Fields
	private Integer code;
	private String status;
	private String message;
	private T data;

	// Constructors
	/**
	 * Minimal constructor
	 * 
	 * @param code
	 * @param status
	 * @param message
	 */
	public PioneerResponse(final Integer code, final String status, final String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}

	/**
	 * Full constructor
	 * 
	 * @param code
	 * @param status
	 * @param message
	 * @param data
	 */
	public PioneerResponse(final Integer code, final String status, final String message, final T data) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	// Property accessors
	/**
	 * 
	 * @return service code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 */
	public void setCode(final Integer code) {
		this.code = code;
	}

	/**
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return Message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return data
	 */
	public T getData() {
		return data;
	}
 
	/**
	 * 
	 * @param data
	 */
	public void setData(final T data) {
		this.data = data;
	}
}