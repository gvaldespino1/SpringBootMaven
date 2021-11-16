package com.pioneer.intel.wrapper;

import java.util.List;

import com.pioneer.intel.json.UserEditRest;

public class IdUserWrapper {

	UserEditRest userEditRest;

	List<Integer> idsGroup;

	/**
	 * @return the userEditRest
	 */
	public UserEditRest getUserEditRest() {
		return userEditRest;
	}

	/**
	 * @param userEditRest the userEditRest to set
	 */
	public void setUserEditRest(UserEditRest userEditRest) {
		this.userEditRest = userEditRest;
	}

	/**
	 * @return the idsGroup
	 */
	public List<Integer> getIdsGroup() {
		return idsGroup;
	}

	/**
	 * @param idsGroup the idsGroup to set
	 */
	public void setIdsGroup(List<Integer> idsGroup) {
		this.idsGroup = idsGroup;
	}

}
