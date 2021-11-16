/**
 * @(#) PermissionController.java
 *
 * Project: PioneerWS
 * Title: PermissionController.java
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
import com.pioneer.intel.json.PermissionEditRest;
import com.pioneer.intel.json.PermissionViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.PermissionService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.util.ValidationUtil;
import com.pioneer.intel.wrapper.IdPrincipalWrapper;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Controller User
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/permission/v1")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
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
	@RequestMapping(value = "get-permission-by-id/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Permission in Database.")
	public PioneerResponse<PermissionViewRest> getPermissionById(
			@RequestParam(required = true) final Integer idPermission) {
		return this.permissionService.getPermissionById(idPermission);
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
	@RequestMapping(value = "get-permission-by-title-match/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Permission in Database.")
	public PioneerResponse<Map<String, Object>> getPermissionByTitleMatch(
			@RequestParam(required = true) final String title, @RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-permission-by-title-match/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("title"));
			return this.permissionService.getPermissionByTitleMatch(title, paging);
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
	@RequestMapping(value = "get-permission-by-title/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Role in Database.")
	public PioneerResponse<PermissionViewRest> getPermissionByTitle(@RequestParam(required = true) final String title) {
		return this.permissionService.getPermissionByTitle(title);
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
	@RequestMapping(value = "get-all-permissions/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Permissions in Database.")
	public PioneerResponse<Map<String, Object>> getAllPermissions(
			@RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-permissions/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("title"));
			return this.permissionService.getAllPermissions(paging);
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
	@RequestMapping(value = "create-permission/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> createPermission(@RequestBody final PermissionEditRest permissionEditRest,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "create-permission/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.permissionService.createPermission(permissionEditRest), String.class));
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
	@RequestMapping(value = "modify-permission/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> modifyPermission(@RequestBody final PermissionEditRest permissionEditRest,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "modify-permission/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.permissionService.modifyPermission(permissionEditRest), String.class));
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
	@RequestMapping(value = "delete-permission/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> deletePermission(@RequestBody final IdPrincipalWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(body.getIdPrincipal(), "delete-permission/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.permissionService.deletePermission(body.getIdRequest()), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}
}