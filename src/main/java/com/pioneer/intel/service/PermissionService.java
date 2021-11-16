/**
 * @(#) PermissionService.java
 *
 * Project: PioneerWS
 * Title: PermissionService.java
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
import com.pioneer.intel.json.PermissionEditRest;
import com.pioneer.intel.json.PermissionViewRest;
import com.pioneer.intel.response.PioneerResponse;

/**
 * Services Sign
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service
 */
public interface PermissionService {

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return FundsResponse<UsuarioViewRest>
	 */
	public PioneerResponse<PermissionViewRest> getPermissionById(final Integer idGroup);

	public PioneerResponse<Map<String, Object>> getAllPermissions(final Pageable pageable);

	public PioneerResponse<PermissionViewRest> getPermissionByTitle(final String title);

	public PioneerResponse<Map<String, Object>> getPermissionByTitleMatch(final String title, final Pageable pageable);

	public String createPermission(final PermissionEditRest permissionEditRest) throws PioneerException;

	public String modifyPermission(final PermissionEditRest permissionEditRest) throws PioneerException;

	public String deletePermission(final Integer idPermission) throws PioneerException;
}