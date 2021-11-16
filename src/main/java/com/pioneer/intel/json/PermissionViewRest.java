/**
 * @(#) PermissionViewRest.java
 *
* Project: PioneerWS
 * Title: PermissionViewRest.java
 * Description: 
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.json;

import java.io.Serializable;
import java.util.Date;

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
public class PermissionViewRest implements Serializable {

	// Fields
	@JsonProperty("idPermission")
	private Integer idPermission;
	@JsonProperty("title")
	private String title;
	@JsonProperty("description")
	private String description;
	@JsonProperty("createdAt")
	private Date createdAt;
	@JsonProperty("updatedAt")
	private Date updatedAt;

	/**
	 * @return the idPermission
	 */
	public Integer getIdPermission() {
		return idPermission;
	}

	/**
	 * @param idPermission the idPermission to set
	 */
	public void setIdPermission(Integer idPermission) {
		this.idPermission = idPermission;
	}

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

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}