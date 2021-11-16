package com.pioneer.intel.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ErrorDTO implements Serializable {

	// Fields
	private String name;
	private String value;

	// Constructors
	/** 
	 * Full constructor
	 * 
	 * @param name
	 * @param value
	 */
	public ErrorDTO(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	// Property accessors
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "(" + this.name + "= " + this.value + ")";
	}
}