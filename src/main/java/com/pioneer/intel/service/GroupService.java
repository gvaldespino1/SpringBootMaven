/**
 * @(#) GroupService.java
 *
 * Project: PioneerWS
 * Title: GroupService.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.GroupEditRest;
import com.pioneer.intel.json.GroupViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdGroupWrapper;

/**
 * Services Sign
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service
 */
public interface GroupService {

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return FundsResponse<UsuarioViewRest>
	 */
	public PioneerResponse<GroupViewRest> getGroupById(final Integer idGroup);

	public PioneerResponse<Map<String, Object>> getAllGroups(Pageable pageable);

	public PioneerResponse<GroupViewRest> getGroupByTitle(final String title);

	public PioneerResponse<Map<String, Object>> getGroupByTitleMatch(final String title, Pageable pageable);

	/**
	 * Invokes the service that stores a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public String createGroup(final GroupEditRest groupEditRest) throws PioneerException;

	public String createGroupWithRoles(final IdGroupWrapper body) throws PioneerException;

	/**
	 * Invokes the service that modifies a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public String modifyGroup(final IdGroupWrapper body) throws PioneerException;

	/**
	 * Invokes the service that deletes a user in DB.
	 * 
	 * @param iduser
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public String deleteGroup(final Integer idGroup) throws PioneerException;

	public String deleteRoleFromGroup(Integer idGroup, Integer idRole) throws PioneerException;

	public String addRolesToGroup(final IdGenericWrapper body) throws PioneerException;
}