/**
 * @(#) RoleController.java
 *
 * Project: PioneerWS
 * Title: RoleController.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.controller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.RoleEditRest;
import com.pioneer.intel.json.RoleViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.RoleService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.util.ValidationUtil;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdPrincipalWrapper;
import com.pioneer.intel.wrapper.IdRoleWrapper;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Controller User
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/role/v1")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private ValidationUtil validationUtil;
	@Autowired
	private MessageUtil messageUtil;

	private static final ModelMapper modelMapper = new ModelMapper();

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return PioneerResponse<UserViewRest>
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-role-by-id/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Role in Database.")
	public PioneerResponse<RoleViewRest> getRoleById(@RequestParam(required = true) final Integer idRole) {
		return this.roleService.getRoleById(idRole);
	}

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return PioneerResponse<UserViewRest>
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-role-by-title-match/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Role in Database.")
	public PioneerResponse<Map<String, Object>> getRoleByTitleMatch(@RequestParam(required = true) final String title,
			@RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-role-by-title-match/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("title"));
			return this.roleService.getRoleByTitleMatch(title, paging);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return PioneerResponse<UserViewRest>
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-role-by-title/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Role in Database.")
	public PioneerResponse<RoleViewRest> getRoleByTitle(@RequestParam(required = true) final String title) {
		return this.roleService.getRoleByTitle(title);
	}

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return PioneerResponse<UserViewRest>
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-all-roles/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Roles in Database.")
	public PioneerResponse<Map<String, Object>> getAllRoles(@RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-roles/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("title"));
			return this.roleService.getAllRoles(paging);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}

	/**
	 * 
	 * @param userRest
	 * @param username
	 * @return PioneerResponse<String>
	 * @throws PioneerException
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "create-role/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> createRole(@RequestBody final RoleEditRest roleEditRest,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "create-role/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.roleService.createRole(roleEditRest), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}

	/**
	 * 
	 * @param userRest
	 * @param username
	 * @return PioneerResponse<String>
	 * @throws PioneerException
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "add-permissions-role/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> addPermissionsToRole(@RequestBody final IdGenericWrapper body,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "add-permissions-role/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.roleService.addPermissionsToRole(body), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}

	/**
	 * 
	 * @param userRest
	 * @param username
	 * @return PioneerResponse<String>
	 * @throws PioneerException
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "create-role-with-permissions/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> createRoleWithPermissions(@RequestBody final IdRoleWrapper body,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "create-role-with-permissions/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.roleService.createRoleWithPermissions(body), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}

	/**
	 * 
	 * @param userRest
	 * @param username
	 * @return PioneerResponse<String>
	 * @throws PioneerException
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "modify-role/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> modifyRole(@RequestBody final IdRoleWrapper body,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "modify-role/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.roleService.modifyRole(body), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}

	/**
	 * 
	 * @param userRest
	 * @param username
	 * @return PioneerResponse<String>
	 * @throws PioneerException
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "delete-role/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> deleteRole(@RequestBody final IdPrincipalWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(body.getIdPrincipal(), "get-latest-users-group/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.roleService.deleteRole(body.getIdRequest()), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}
}