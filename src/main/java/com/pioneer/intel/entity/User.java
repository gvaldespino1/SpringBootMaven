/**
 * @(#) User.java
 *
 * Project: PioneerWS
 * Title: User.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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

/**
 * Entity [user]
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.entity
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements Serializable, Comparable<User> {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", unique = true)
	private Integer idUser;
	@Column(name = "username", nullable = false, length = 50, unique = true)
	private String username;
	@Column(name = "`firstName`", nullable = false, length = 50)
	private String firstName;
	@Column(name = "`middleName`", length = 50)
	private String middleName;
	@Column(name = "`lastName`", nullable = false, length = 50)
	private String lastName;
	@Column(name = "email", nullable = false, length = 50, unique = true)
	private String email;
	@Column(name = "`registeredAt`", nullable = false)
	private Date registeredAt;
	@Column(name = "`lastLogin`", nullable = false)
	private Date lastLogin;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_group", joinColumns = { @JoinColumn(name = "id_user") }, inverseJoinColumns = {
			@JoinColumn(name = "id_group") })
	private Set<Group> groups;

	// Constructors
	/**
	 */
	public User() {
	}

	/**
	 * @return the idUser
	 */
	public Integer getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the registeredAt
	 */
	public Date getRegisteredAt() {
		return registeredAt;
	}

	/**
	 * @param registeredAt the registeredAt to set
	 */
	public void setRegisteredAt(Date registeredAt) {
		this.registeredAt = registeredAt;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the groups
	 */
	public Set<String> getGroupsIds() {
		Set<String> groupsIds = new HashSet<String>();
		for (Group group : groups) {
			groupsIds.add(group.getIdGroup().toString());
		}
		return groupsIds;
	}

	/**
	 * @return the groups
	 */
	public Set<String> getGroupsTitles() {
		Set<String> groupsTitles = new HashSet<String>();
		for (Group group : groups) {
			groupsTitles.add(group.getTitle());
		}
		return groupsTitles;
	}

	public Set<String> getRolesIds() {
		Set<String> rolesIds = new HashSet<String>();
		for (Group group : groups) {
			for (Role role : group.getRoles()) {
				rolesIds.add(role.getIdRole().toString());
			}
		}
		return rolesIds;
	}

	/**
	 * @return the groups
	 */
	public Set<String> getRolesTitles() {
		Set<String> rolesTitles = new HashSet<String>();
		for (Group group : groups) {
			for (Role role : group.getRoles()) {
				rolesTitles.add(role.getTitle());
			}
		}
		return rolesTitles;
	}

	public Set<String> getPermissionsIds() {
		Set<String> permissionsIds = new HashSet<String>();
		for (Group group : groups) {
			for (Role role : group.getRoles()) {
				for (Permission permission : role.getPermissions()) {
					permissionsIds.add(permission.getIdPermission().toString());
				}
			}
		}
		return permissionsIds;
	}

	/**
	 * @return the groups
	 */
	public Set<String> getPermissionsTitles() {
		Set<String> permissionsTitles = new HashSet<String>();
		for (Group group : groups) {
			for (Role role : group.getRoles()) {
				for (Permission permission : role.getPermissions()) {
					permissionsTitles.add(permission.getTitle());
				}
			}
		}
		return permissionsTitles;
	}

	/**
	 * @return the groups
	 */
	public Set<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.getIdUser().compareTo(o.getIdUser());
	}
}