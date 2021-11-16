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
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pioneer.intel.entity.Role;

/**
 * Transporting Class for Services and Front End.
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.json
 */
@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupViewRest implements Serializable {

	// Fields
	@JsonProperty("idGroup")
	private Integer idGroup;
	@JsonProperty("title")
	private String title;
	@JsonProperty("description")
	private String description;
	@JsonProperty("createdAt")
	private Date createdAt;
	@JsonProperty("updatedAt")
	private Date updatedAt;
	@JsonProperty("roles")
	private List<Role> roles;

	/**
	 * @return the idGroup
	 */
	public Integer getIdGroup() {
		return idGroup;
	}

	/**
	 * @param idGroup the idGroup to set
	 */
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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