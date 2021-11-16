/**
 * @(#) GroupViewRest.java
 *
* Project: PioneerWS
 * Title: GroupViewRest.java
 * Description: 
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Transporting Class for Services and Front End.
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.json
 */
@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupEditRest implements Serializable {

	// Fields
	@JsonProperty("title")
	private String title;
	@JsonProperty("description")
	private String description;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}