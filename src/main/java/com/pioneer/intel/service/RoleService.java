/**
 * @(#) RoleService.java
 *
 * Project: PioneerWS
 * Title: RoleService.java
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
import com.pioneer.intel.json.RoleEditRest;
import com.pioneer.intel.json.RoleViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdRoleWrapper;

/**
 * Services Sign
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service
 */
public interface RoleService {

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return FundsResponse<UsuarioViewRest>
	 */
	public PioneerResponse<RoleViewRest> getRoleById(final Integer idRole);

	public PioneerResponse<Map<String, Object>> getAllRoles(Pageable pageable);

	public PioneerResponse<Map<String, Object>> getRoleByTitleMatch(final String title, Pageable pageable);

	public PioneerResponse<RoleViewRest> getRoleByTitle(final String title);

	public String createRole(final RoleEditRest roleEditRest) throws PioneerException;

	public String createRoleWithPermissions(final IdRoleWrapper body) throws PioneerException;

	public String modifyRole(final IdRoleWrapper body) throws PioneerException;

	public String deleteRole(final Integer idRole) throws PioneerException;

	public String addPermissionsToRole(final IdGenericWrapper body) throws PioneerException;

	public String deletePermissionFromRole(Integer idRole, Integer idPermission) throws PioneerException;
}