/**
 * @(#) MessageUtil.java
 *
 * Project: PioneerWS
 * Title: MessageUtil.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.util;

import java.text.MessageFormat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Propierties
 * [messages.properties],
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.util
 */
@Configuration
@PropertySource("classpath:messages.properties")
@ConfigurationProperties(prefix = "message")
public class MessageUtil {

	// Fields
	private String successOperation;
	private String objectDoesNotExists;
	private String duplicateObject;
	private String unexpectedException;
	private String usernotexist;
	private String userduplicated;
    private String usermodify;
	private String userdelete;
	private String rolenotexist;
	private String roleduplicated;
	private String rolemodify;
	private String roledelete;
	private String groupnotexist;
	private String groupduplicated;
	private String groupmodify;
	private String groupdelete;
	private String permissionnotexist;
	private String permissionduplicated;
	private String permissionmodify;
	private String permissiondelete;



	// Constructors
	/**
	 * Constructor por Defecto
	 */
	public MessageUtil() {
	}

	// Property accessors
	/**
	 * 
	 * @return String successOperation
	 */
	public String getSuccessOperation() {
		return successOperation;
	}

	/**
	 * 
	 * @param String successOperation
	 */
	public void setSuccessOperation(final String successOperation) {
		this.successOperation = successOperation;
	}

	/**
	 * 
	 * @param functionality
	 * @param fields
	 * @return String objectDoesNotExists
	 */
	public String getObjectDoesNotExists(final String functionality, final String fields) {
		return MessageFormat.format(this.objectDoesNotExists, functionality, fields);
	}

	/**
	 * 
	 * @param String objectDoesNotExists
	 */
	public void setObjectDoesNotExists(final String objectDoesNotExists) {
		this.objectDoesNotExists = objectDoesNotExists;
	}
	
	/**
	 * 
	 * @param functionality
	 * @param fields
	 * @return String duplicateObject
	 */
	public String getDuplicateObject(final String functionality, final String fields) {
		return MessageFormat.format(this.duplicateObject, functionality, fields);
	}

	/**
	 * 
	 * @param String duplicateObject
	 */
	public void setDuplicateObject(final String duplicateObject) {
		this.duplicateObject = duplicateObject;
	}

	/**
	 * 
	 * @return String unexpectedException
	 */
	public String getUnexpectedException() {
		return unexpectedException;
	}

	/**
	 * 
	 * @param String unexpectedException
	 */
	public void setUnexpectedException(final String unexpectedException) {
		this.unexpectedException = unexpectedException;
	}
	
	public String getUsernotexist() {
		return usernotexist;
	}

	public void setUsernotexist(String usernotexist) {
		this.usernotexist = usernotexist;
	}

	public String getUserduplicated() {
		return userduplicated;
	}

	public void setUserduplicated(String userduplicated) {
		this.userduplicated = userduplicated;
	}

	public String getUsermodify() {
		return usermodify;
	}

	public void setUsermodify(String usermodify) {
		this.usermodify = usermodify;
	}

	public String getUserdelete() {
		return userdelete;
	}

	public void setUserdelete(String userdelete) {
		this.userdelete = userdelete;
	}

	public String getRolenotexist() {
		return rolenotexist;
	}

	public void setRolenotexist(String rolenotexist) {
		this.rolenotexist = rolenotexist;
	}

	public String getRoleduplicated() {
		return roleduplicated;
	}

	public void setRoleduplicated(String roleduplicated) {
		this.roleduplicated = roleduplicated;
	}

	public String getRolemodify() {
		return rolemodify;
	}

	public void setRolemodify(String rolemodify) {
		this.rolemodify = rolemodify;
	}

	public String getRoledelete() {
		return roledelete;
	}

	public void setRoledelete(String roledelete) {
		this.roledelete = roledelete;
	}

	public String getGroupnotexist() {
		return groupnotexist;
	}

	public void setGroupnotexist(String groupnotexist) {
		this.groupnotexist = groupnotexist;
	}

	public String getGroupduplicated() {
		return groupduplicated;
	}

	public void setGroupduplicated(String groupduplicated) {
		this.groupduplicated = groupduplicated;
	}

	public String getGroupmodify() {
		return groupmodify;
	}

	public void setGroupmodify(String groupmodify) {
		this.groupmodify = groupmodify;
	}

	public String getGroupdelete() {
		return groupdelete;
	}

	public void setGroupdelete(String groupdelete) {
		this.groupdelete = groupdelete;
	}

	public String getPermissionnotexist() {
		return permissionnotexist;
	}

	public void setPermissionnotexist(String permissionnotexist) {
		this.permissionnotexist = permissionnotexist;
	}

	public String getPermissionduplicated() {
		return permissionduplicated;
	}

	public void setPermissionduplicated(String permissionduplicated) {
		this.permissionduplicated = permissionduplicated;
	}

	public String getPermissionmodify() {
		return permissionmodify;
	}

	public void setPermissionmodify(String permissionmodify) {
		this.permissionmodify = permissionmodify;
	}

	public String getPermissiondelete() {
		return permissiondelete;
	}

	public void setPermissiondelete(String permissiondelete) {
		this.permissiondelete = permissiondelete;
	}
}