/**
 * @(#) GroupServiceImpl.java
 *
 * Project: PioneerWS
 * Title: GroupServiceImpl.java
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.
 */
package com.pioneer.intel.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pioneer.intel.dao.GroupDao;
import com.pioneer.intel.dao.RoleDao;
import com.pioneer.intel.dao.UserDao;
import com.pioneer.intel.entity.Group;
import com.pioneer.intel.entity.Role;
import com.pioneer.intel.entity.User;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.GroupEditRest;
import com.pioneer.intel.json.GroupViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.GroupService;
import com.pioneer.intel.service.UserService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdGroupWrapper;

/**
 * Services Interface
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service.impl
 */
@Service("groupService")
@Transactional
public class GroupServiceImpl implements GroupService {

	// Constantes usadas para el mapeo entre objetos
	private static final ModelMapper modelMapper = new ModelMapper();

	// Property Fields
	@Autowired
	private MessageUtil messageUtil;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;

	/**
	 * 
	 * @param IdGroup
	 * @return PioneerResponse<GroupViewRest>
	 */
	public PioneerResponse<GroupViewRest> getGroupById(final Integer idGroup) {
		final Optional<Group> group = this.groupDao.findById(idGroup);
		if (group.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(group.get(), GroupViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Group", idGroup.toString()));
		}
	}

	public PioneerResponse<Map<String, Object>> getGroupByTitleMatch(final String title, Pageable pageable) {
		final Page<Group> groups = this.groupDao.findByTitleStartsWith(title, pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("groups", groups.getContent());
		response.put("currentPage", groups.getNumber());
		response.put("totalItems", groups.getTotalElements());
		response.put("totalPages", groups.getTotalPages());
		if (groups != null && !groups.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Group", title));
		}
	}

	public PioneerResponse<Map<String, Object>> getAllGroups(Pageable pageable) {
		final Page<Group> groups = this.groupDao.findAll(pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("groups", groups.getContent());
		response.put("currentPage", groups.getNumber());
		response.put("totalItems", groups.getTotalElements());
		response.put("totalPages", groups.getTotalPages());
		if (groups != null && !groups.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Groups", "Not Found"));
		}
	}

	public String createGroup(final GroupEditRest groupEditRest) throws PioneerException {
		final Group groupEdit = new Group();
		BeanUtils.copyProperties(groupEditRest, groupEdit);
		groupEdit.setCreatedAt(new Date());
		groupEdit.setUpdatedAt(new Date());
		this.groupDao.save(groupEdit);
		return "GROUP_CREATE";
	}

	public String addRolesToGroup(final IdGenericWrapper body) throws PioneerException {
		final Role role = this.roleDao.getById(body.getSource());
		for (Integer id : body.getDestination()) {
			final Group group = this.groupDao.getById(id);
			if (!group.getRoles().contains(role)) {
				group.getRoles().add(role);
				this.groupDao.save(group);
			}
		}
		return "ROLE_ADDED";
	}

	public String createGroupWithRoles(final IdGroupWrapper body) throws PioneerException {
		final Group groupEdit = new Group();
		BeanUtils.copyProperties(body.getGroupEditRest(), groupEdit);
		groupEdit.setCreatedAt(new Date());
		groupEdit.setUpdatedAt(new Date());
		final Set<Role> roles = new TreeSet<Role>();
		for (Integer id : body.getIdsRoles()) {
			Optional<Role> tmp_r = this.roleDao.findById(id);
			if (tmp_r.isPresent()) {
				roles.add(tmp_r.get());
			}
		}
		if (roles != null && !roles.isEmpty()) {
			groupEdit.setRoles(roles);
			this.groupDao.save(groupEdit);
		}
		return "GROUP_CREATE";
	}

	public String modifyGroup(final IdGroupWrapper body) throws PioneerException {
		final Optional<Group> groupEdit = this.groupDao.findByTitle(body.getGroupEditRest().getTitle());
		if (groupEdit.isPresent() && !(body.getGroupEditRest().getTitle().equals("Super Admin Group")
				|| body.getGroupEditRest().getTitle().equals("Admin Group")
				|| body.getGroupEditRest().getTitle().equals("Default Group"))) {
			final Group tmp_g = groupEdit.get();
			BeanUtils.copyProperties(body.getGroupEditRest(), tmp_g);
			tmp_g.setUpdatedAt(new Date());
			if (!body.getIdsRoles().isEmpty()) {
				final Set<Role> roles = new TreeSet<Role>();
				for (Integer id : body.getIdsRoles()) {
					final Optional<Role> tmp_r = this.roleDao.findById(id);
					if (tmp_r.isPresent()) {
						roles.add(tmp_r.get());
					}
				}
				if (roles.size() > 0) {
					tmp_g.setRoles(roles);
				}
			} else {
				tmp_g.getRoles().clear();
			}
			this.groupDao.saveAndFlush(tmp_g);
			return "GROUP_MODIFY";
		} else {
			return "ERROR";
		}
	}

	public String deleteGroup(final Integer idGroup) throws PioneerException {
		final Optional<Group> group = this.groupDao.findById(idGroup);
		final List<User> users = this.userDao.findByGroups(group.get());
		if (group.isPresent() && !(this.groupDao.findById(idGroup).get().getTitle().equals("Super Admin Group")
				|| this.groupDao.findById(idGroup).get().getTitle().equals("Admin Group")
				|| this.groupDao.findById(idGroup).get().getTitle().equals("Default Group"))) {
			for (User user : users) {
				this.userService.deleteGroupFromUser(user.getIdUser(), idGroup);
			}
			this.groupDao.deleteById(idGroup);
			return "GROUP_DELETE";
		} else {
			return "ERROR";
		}
	}

	public String deleteRoleFromGroup(Integer idGroup, Integer idRole) throws PioneerException {
		final Optional<Group> group = this.groupDao.findById(idGroup);
		if (group.isPresent()) {
			group.get().getRoles().remove(this.roleDao.findById(idRole).get());
			return "ROLE_DELETE";
		} else {
			return "ERROR";
		}
	}

	public PioneerResponse<GroupViewRest> getGroupByTitle(String title) {
		final Optional<Group> group = this.groupDao.findByTitle(title);
		if (group.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(group.get(), GroupViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Role", title));
		}
	}
}