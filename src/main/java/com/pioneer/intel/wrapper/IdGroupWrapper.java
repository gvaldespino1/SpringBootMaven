package com.pioneer.intel.wrapper;

import java.util.List;

import com.pioneer.intel.json.GroupEditRest;

public class IdGroupWrapper {

	GroupEditRest groupEditRest;

	List<Integer> idsRoles;

	/**
	 * @return the groupEditRest
	 */
	public GroupEditRest getGroupEditRest() {
		return groupEditRest;
	}

	/**
	 * @param groupEditRest the groupEditRest to set
	 */
	public void setGroupEditRest(GroupEditRest groupEditRest) {
		this.groupEditRest = groupEditRest;
	}

	/**
	 * @return the idsRoles
	 */
	public List<Integer> getIdsRoles() {
		return idsRoles;
	}

	/**
	 * @param idsRoles the idsRoles to set
	 */
	public void setIdsRoles(List<Integer> idsRoles) {
		this.idsRoles = idsRoles;
	}

}
