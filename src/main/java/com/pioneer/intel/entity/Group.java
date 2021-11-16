package com.pioneer.intel.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "group_")
public class Group implements Comparable<Group> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_group", unique = true, nullable = false)
	private Integer idGroup;
	@Column(name = "title", length = 75, nullable = false, unique = true)
	private String title;
	@Column(name = "description", length = 1)
	private String description;
	@Column(name = "`createdAt`")
	private Date createdAt;
	@Column(name = "`updatedAt`")
	private Date updatedAt;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "group__roles", joinColumns = { @JoinColumn(name = "id_group") }, inverseJoinColumns = {
			@JoinColumn(name = "id_role") })
	private Set<Role> roles;

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Group() {
	}

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
	 * @return the descprition
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param descprition the description to set
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
	public int compareTo(Group o) {
		// TODO Auto-generated method stub
		return this.getIdGroup().compareTo(o.getIdGroup());
	}

}