/**
 * @(#) UserServiceImpl.java
 *
 * Project: PioneerWS
 * Title: UserServiceImpl.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pioneer.intel.dao.GroupDao;
import com.pioneer.intel.dao.UserDao;
import com.pioneer.intel.entity.Group;
import com.pioneer.intel.entity.User;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.UserEditRest;
import com.pioneer.intel.json.UserViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.UserService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdUserWrapper;

/**
 * Services Interface
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service.impl
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	// Property Fields Logger

	private static final ModelMapper modelMapper = new ModelMapper();

	// Property Fields
	@Autowired
	private MessageUtil messageUtil;
	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupDao groupDao;

	public PioneerResponse<UserViewRest> getUserById(final Integer idUser) {
		final Optional<User> user = this.userDao.findById(idUser);
		if (user.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(user.get(), UserViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("User", idUser.toString()));
		}
	}

	public PioneerResponse<Map<String, Object>> getUsersByGroup(final Integer idGroup, Pageable pageable) {
		final Optional<Group> group = this.groupDao.findById(idGroup);
		final Page<User> users = this.userDao.findByGroups(group.get(), pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("users", users.getContent());
		response.put("currentPage", users.getNumber());
		response.put("totalItems", users.getTotalElements());
		response.put("totalPages", users.getTotalPages());
		if (users != null && !users.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Users", "Not Found"));
		}
	}

	/**
	 * 
	 * @param IdUser
	 * @return PioneerResponse<UsuarioViewRest>
	 */
	public PioneerResponse<UserViewRest> getUserByUsername(final String username) {
		final Optional<User> user = this.userDao.findByUsername(username);
		if (user.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(user.get(), UserViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("User", username));
		}
	}

	public PioneerResponse<Map<String, Object>> getUserByUsernameMatch(final Pageable pageable, final String username) {
		final Page<User> users = this.userDao.findByUsernameStartsWith(username, pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("users", users.getContent());
		response.put("currentPage", users.getNumber());
		response.put("totalItems", users.getTotalElements());
		response.put("totalPages", users.getTotalPages());
		if (!users.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("User", username));
		}
	}

	public PioneerResponse<Map<String, Object>> getAllUsers(Pageable pageable) {
		final Page<User> users = this.userDao.findAll(pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("users", users.getContent());
		response.put("currentPage", users.getNumber());
		response.put("totalItems", users.getTotalElements());
		response.put("totalPages", users.getTotalPages());
		if (users.getContent() != null && !users.getContent().isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Users", "Not Found"));
		}
	}

	public List<User> getAllUsersFile() {
		final List<User> users = this.userDao.findAll();
		return users;
	}

	public Integer createUser(final UserEditRest userRest) throws PioneerException {
		final User userEdit = new User();
		BeanUtils.copyProperties(userRest, userEdit);
		userEdit.setRegisteredAt(new Date());
		userEdit.setLastLogin(new Date());
		final Set<Group> groups = new HashSet<Group>();
		groups.add(this.groupDao.findByTitle("Default Group").get());
		userEdit.setGroups(groups);
		this.userDao.save(userEdit);
		Integer tmp = this.userDao.findByUsername(userRest.getUsername()).get().getIdUser();
		return tmp;
	}

	public Integer createUserWithGroup(final IdUserWrapper body) throws PioneerException {
		final User userEdit = new User();
		BeanUtils.copyProperties(body.getUserEditRest(), userEdit);
		userEdit.setRegisteredAt(new Date());
		userEdit.setLastLogin(new Date());
		final Set<Group> groups = new HashSet<Group>();
		for (Integer id : body.getIdsGroup()) {
			Optional<Group> tmp_g = this.groupDao.findById(id);
			if (tmp_g.isPresent()) {
				groups.add(tmp_g.get());
			}
		}
		userEdit.setGroups(groups);
		this.userDao.save(userEdit);
		Integer tmp = this.userDao.findByUsername(body.getUserEditRest().getUsername()).get().getIdUser();
		return tmp;
	}

	public String addGroupsToUser(final IdGenericWrapper body) throws PioneerException {
		final Group group = this.groupDao.getById(body.getSource());
		for (Integer id : body.getDestination()) {
			final User user = this.userDao.getById(id);
			if (!user.getGroups().contains(group)) {
				user.getGroups().add(group);
				this.userDao.save(user);
			}
		}
		return "GROUP_ADDED";
	}

	public String modifyUser(final IdUserWrapper body) throws PioneerException {
		final Optional<User> userEdit = this.userDao.findByUsername(body.getUserEditRest().getUsername());
		if (userEdit.isPresent()) {
			final User tmp_u = userEdit.get();
			BeanUtils.copyProperties(body.getUserEditRest(), tmp_u);
			tmp_u.setLastLogin(new Date());
			if (!body.getIdsGroup().isEmpty()) {
				final Set<Group> groups = new TreeSet<Group>();
				for (Integer id : body.getIdsGroup()) {
					final Optional<Group> tmp_g = this.groupDao.findById(id);
					if (tmp_g.isPresent()) {
						groups.add(tmp_g.get());
					}
				}
				if (groups.size() > 0) {
					tmp_u.setGroups(groups);
				}
			} else {
				tmp_u.getGroups().clear();
			}
			this.userDao.saveAndFlush(tmp_u);
			return "USER_MODIFY";
		} else {
			return "ERROR";
		}
	}

	public String deleteUser(final Integer idUser) throws PioneerException {
		final Optional<User> user = this.userDao.findById(idUser);
		if (user.isPresent()) {
			this.userDao.deleteById(idUser);
			return "USER_DELETE";
		} else {
			return "ERROR";
		}
	}

	public String deleteGroupFromUser(Integer idUser, Integer idGroup) throws PioneerException {
		final Optional<User> user = this.userDao.findById(idUser);
		if (user.isPresent() && !(this.groupDao.findById(idGroup).get().getTitle().equals("Super Admin Group")
				|| this.groupDao.findById(idGroup).get().getTitle().equals("Admin Group")
				|| this.groupDao.findById(idGroup).get().getTitle().equals("Default Group"))) {
			user.get().getGroups().remove(this.groupDao.findById(idGroup).get());
			return "USER_DELETE";
		} else {
			return "ERROR";
		}
	}

	public PioneerResponse<List<UserViewRest>> getLatestUsersByGroup(Integer idGroup) {
		final Optional<Group> group = this.groupDao.findById(idGroup);
		if (group.isPresent()) {
			final List<User> users = this.userDao.findTop3ByGroupsOrderByRegisteredAtDesc(group.get());
			if (users != null && !users.isEmpty()) {
				return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
						users.parallelStream().map(user -> modelMapper.map(user, UserViewRest.class))
								.collect(Collectors.toList()));
			} else {
				return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
						messageUtil.getObjectDoesNotExists("Users", "Not Found"));
			}
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Group", "Not Found"));
		}
	}
}