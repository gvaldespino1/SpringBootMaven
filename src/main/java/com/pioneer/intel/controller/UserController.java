/**
 * @(#) UserController.java
 *
 * Project: PioneerWS
 * Title: UserController.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pioneer.intel.entity.User;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.UserEditRest;
import com.pioneer.intel.json.UserViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.UserService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.util.ValidationUtil;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdPrincipalWrapper;
import com.pioneer.intel.wrapper.IdUserWrapper;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Controller User
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/user/v1")
public class UserController {

	@Autowired
	private UserService userService;
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
	@RequestMapping(value = "get-user-by-id/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "User in Database.")
	public PioneerResponse<UserViewRest> getUserById(@RequestParam(required = true) final Integer idUser) {
		return this.userService.getUserById(idUser);
	}

	/**
	 * 
	 * @param IdUser
	 * @param userLogin
	 * @return PioneerResponse<UserViewRest>
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-user-by-username/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "User in Database.")
	public PioneerResponse<UserViewRest> getUserByUsername(@RequestParam(required = true) final String username) {
		return this.userService.getUserByUsername(username);
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-user-by-username-match/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "User in Database.")
	public PioneerResponse<Map<String, Object>> getUserByUsernameMatch(
			@RequestParam(required = true) final String idPrincipal,
			@RequestParam(required = true) final String username, @RequestParam(defaultValue = "0") final Integer page,
			@RequestParam(defaultValue = "5") final Integer size) throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-user-by-username-match/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("username"));
			return this.userService.getUserByUsernameMatch(paging, username.toLowerCase());
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
	@RequestMapping(value = "get-user-by-group/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "User in Database.")
	public PioneerResponse<Map<String, Object>> getUserByGroup(@RequestParam(required = true) final String idPrincipal,
			@RequestParam(required = true) final Integer idGroup, @RequestParam(defaultValue = "0") final Integer page,
			@RequestParam(defaultValue = "5") final Integer size) throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-user-by-group/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("username"));
			return this.userService.getUsersByGroup(idGroup, paging);
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
	@RequestMapping(value = "get-all-users/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Users in Database.")
	public PioneerResponse<Map<String, Object>> getAllUsers(@RequestParam(required = true) final String idPrincipal,
			@RequestParam(defaultValue = "0") final Integer page, @RequestParam(defaultValue = "5") final Integer size)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-users/")) {
			Pageable paging = PageRequest.of(page, size, Sort.by("username"));
			return this.userService.getAllUsers(paging);
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
	 * @throws IOException
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "get-all-users-file/", method = RequestMethod.GET)
	@Operation(summary = "Users in Database.")
	public void getAllUsersFile(@RequestParam(required = true) final String idPrincipal, HttpServletResponse response)
			throws IOException {
		if (this.validationUtil.validateUser(idPrincipal, "get-all-users-file/")) {
			String csvFileName = "users.csv";

			response.setContentType("text/csv");

			// creates mock data
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
			response.setHeader(headerKey, headerValue);

			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			String[] header = { "idUser", "username", "firstName", "middleName", "lastName", "email", "registeredAt",
					"lastLogin", "groupsIds", "groupsTitles", "rolesIds", "rolesTitles", "permissionsIds",
					"permissionsTitles" };

			csvWriter.writeHeader(header);

			List<User> users = this.userService.getAllUsersFile();

			for (User user : users) {
				csvWriter.write(user, header);
			}

			csvWriter.close();
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
	@RequestMapping(value = "get-latest-users-group/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Latest Users in Database.")
	public PioneerResponse<List<UserViewRest>> getLatestUsersByGroup(@RequestBody final IdPrincipalWrapper body)
			throws JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(body.getIdPrincipal(), "get-latest-users-group/")) {
			return this.userService.getLatestUsersByGroup(body.getIdRequest());
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
	@RequestMapping(value = "create-user/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<Integer> createUser(@RequestParam(required = true) final String idPrincipal,
			@RequestBody final UserEditRest userRest)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "create-user/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.userService.createUser(userRest), Integer.class));
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
	@RequestMapping(value = "add-groups-user/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> addGroupsToUser(@RequestParam(required = true) final String idPrincipal,
			@RequestBody final IdGenericWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "add-groups-user/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.userService.addGroupsToUser(body), String.class));
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
	@RequestMapping(value = "create-user-with-group/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<Integer> createUserWithGroup(@RequestParam(required = true) final String idPrincipal,
			@RequestBody final IdUserWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "create-user-with-group/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.userService.createUserWithGroup(body), Integer.class));
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
	@RequestMapping(value = "modify-user/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> modifyUser(@RequestParam(required = true) final String idPrincipal,
			@RequestBody final IdUserWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(idPrincipal, "modify-user/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.userService.modifyUser(body), String.class));
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
	@RequestMapping(value = "delete-user/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PioneerResponse<String> deleteUser(@RequestBody final IdPrincipalWrapper body)
			throws PioneerException, JsonMappingException, JsonProcessingException {
		if (this.validationUtil.validateUser(body.getIdPrincipal(), "delete-user/")) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(this.userService.deleteUser(body.getIdRequest()), String.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Restricted", "Access"));
		}

	}
}