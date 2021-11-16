package com.pioneer.intel.util;

public enum EventType {

	// Enumerator
	SEARCH("Search"), NEW("New"), CHANGE("Change"), DELETE("DELETE");

    // Constructors
    /**
     * Constructor por default
     */ 
	EventType(final String name) {
        this.name = name;
    }

    // Fields
    private final String name;

    // Property accessors
    public String getName() {
		return name;
	}
}