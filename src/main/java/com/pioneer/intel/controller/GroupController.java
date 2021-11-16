/**
 * @(#) GroupController.java
 *
 * Project: PioneerWS
 * Title: GroupController.java
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
import com.pioneer.intel.json.GroupEditRest;
import com.pioneer.intel.json.GroupViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.GroupService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.util.ValidationUtil;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdGroupWrapper;
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
@RequestMapping(path = "/group/v1")
public class GroupController {

	@Autowired
	private GroupService groupService;
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
	@RequestMapping(value = "get-group-by-id/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Group in Database.")
	public PioneerResponse<GroupViewRest> getGroupById(@RequestParam(required = true) final Integer idGroup) {
		return this.groupService.getGroupById(idGroup);
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
	@RequestMapping(value = "get-group-by-title/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Role in Database.")
	public PioneerResponse<GroupViewRest> getGroupByTitle(@RequestParam(required = true) final String title) {
		return this.groupService.getGroupByTitle(title);
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
	@RequestMapping(value = "get-group-by-title-match/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Group in Database.")
	public PioneerResponse<Map<String, Object>> getGroupByTitleMatch(@RequestParam(required = true) final String title,
			@RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-group-by-title-match/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("title"));
			return this.groupService.getGroupByTitleMatch(title, paging);
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
	@RequestMapping(value = "get-all-groups/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Group in Database.")
	public PioneerResponse<Map<String, Object>> getAllGroups(@RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-groups/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("title"));
			return this.groupService.getAllGroups(paging);
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
	@RequestMapping(value = "create-group/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> createGroup(@RequestBody final GroupEditRest groupEditRest,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "create-group/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.groupService.createGroup(groupEditRest), String.class));
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
	@RequestMapping(value = "add-roles-group/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> addRolesToGroup(@RequestBody final IdGenericWrapper body,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "add-roles-group/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.groupService.addRolesToGroup(body), String.class));
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
	@RequestMapping(value = "create-group-with-roles/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> createGroupWithRoles(@RequestBody final IdGroupWrapper body,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-users/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.groupService.createGroupWithRoles(body), String.class));
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
	@RequestMapping(value = "modify-group/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> modifyGroup(@RequestBody final IdGroupWrapper body,
			@RequestParam(required = true) final String idPrincipal)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-users/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.groupService.modifyGroup(body), String.class));
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
	@RequestMapping(value = "delete-group/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> deleteGroup(@RequestBody final IdPrincipalWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(body.getIdPrincipal(), "get-latest-users-group/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.groupService.deleteGroup(body.getIdRequest()), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}
	}
}