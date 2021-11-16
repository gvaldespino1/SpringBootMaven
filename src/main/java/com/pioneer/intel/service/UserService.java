/**
 * @(#) UserService.java
 *
 * Project: PioneerWS
 * Title: UserService.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.pioneer.intel.entity.User;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.UserEditRest;
import com.pioneer.intel.json.UserViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdUserWrapper;

/**
 * Services Sign
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service
 */
public interface UserService {

	public PioneerResponse<UserViewRest> getUserById(final Integer idUser);

	/**
	 * Invoke the service looking for a user in DB.
	 * 
	 * @param username
	 * @param userLogin
	 * @return PioneerResponse<UsuarioViewRest>
	 */
	public PioneerResponse<UserViewRest> getUserByUsername(final String username);

	public PioneerResponse<Map<String, Object>> getUserByUsernameMatch(final Pageable pageable, final String username);

	/**
	 * Invoke the service looking for a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */

	public PioneerResponse<Map<String, Object>> getAllUsers(Pageable pageable);

	/**
	 * Invoke the service looking for a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */

	public List<User> getAllUsersFile();

	/**
	 * Invokes the service that stores a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public Integer createUser(final UserEditRest userRest) throws PioneerException;

	/**
	 * Invokes the service that stores a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public Integer createUserWithGroup(final IdUserWrapper body) throws PioneerException;

	/**
	 * Invokes the service that modifies a user in DB.
	 * 
	 * @param userRest
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public String modifyUser(final IdUserWrapper userRest) throws PioneerException;

	/**
	 * Invokes the service that deletes a user in DB.
	 * 
	 * @param iduser
	 * @param userLogin
	 * @return String
	 * @throws PioneerException
	 */
	public String deleteUser(Integer idUser) throws PioneerException;

	public String addGroupsToUser(final IdGenericWrapper body) throws PioneerException;

	public String deleteGroupFromUser(Integer idUser, Integer idGroup) throws PioneerException;

	public PioneerResponse<List<UserViewRest>> getLatestUsersByGroup(Integer idGroup);

	public PioneerResponse<Map<String, Object>> getUsersByGroup(Integer idGroup, Pageable pageable);
}