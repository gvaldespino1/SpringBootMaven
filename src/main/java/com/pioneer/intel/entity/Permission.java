package com.pioneer.intel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "permission")
public class Permission implements Comparable<Permission> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_permission", unique = true, nullable = false)
	private Integer idPermission;
	@Column(name = "title", length = 75, nullable = false, unique = true)
	private String title;
	@Column(name = "description", length = 1)
	private String description;
	@Column(name = "`createdAt`")
	private Date createdAt;
	@Column(name = "`updatedAt`")
	private Date updatedAt;

	public Permission() {
	}

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

	@Override
	public int compareTo(Permission o) {
		// TODO Auto-generated method stub
		return this.getIdPermission().compareTo(o.getIdPermission());
	}

}