package com.pioneer.intel.wrapper;

import java.util.List;

import com.pioneer.intel.json.RoleEditRest;

public class IdRoleWrapper {

	RoleEditRest roleEditRest;

	List<Integer> idsPermissions;

	/**
	 * @return the roleEditRest
	 */
	public RoleEditRest getRoleEditRest() {
		return roleEditRest;
	}

	/**
	 * @param roleEditRest the roleEditRest to set
	 */
	public void setRoleEditRest(RoleEditRest roleEditRest) {
		this.roleEditRest = roleEditRest;
	}

	/**
	 * @return the idsPermissions
	 */
	public List<Integer> getIdsPermissions() {
		return idsPermissions;
	}

	/**
	 * @param idsPermissions the idsPermissions to set
	 */
	public void setIdsPermissions(List<Integer> idsPermissions) {
		this.idsPermissions = idsPermissions;
	}

}
