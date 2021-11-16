/**
 * @(#) RoleServiceImpl.java
 *
 * Project: PioneerWS
 * Title: RoleServiceImpl.java
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
import com.pioneer.intel.dao.PermissionDao;
import com.pioneer.intel.dao.RoleDao;
import com.pioneer.intel.entity.Group;
import com.pioneer.intel.entity.Permission;
import com.pioneer.intel.entity.Role;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.RoleEditRest;
import com.pioneer.intel.json.RoleViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.GroupService;
import com.pioneer.intel.service.RoleService;
import com.pioneer.intel.util.MessageUtil;
import com.pioneer.intel.wrapper.IdGenericWrapper;
import com.pioneer.intel.wrapper.IdRoleWrapper;

/**
 * Services Interface
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service.impl
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	// Constantes usadas para el mapeo entre objetos
	private static final ModelMapper modelMapper = new ModelMapper();

	// Property Fields
	@Autowired
	private MessageUtil messageUtil;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private GroupService groupService;

	/**
	 * 
	 * @param IdRole
	 * @return PioneerResponse<RoleViewRest>
	 */
	public PioneerResponse<RoleViewRest> getRoleById(final Integer idRole) {
		final Optional<Role> role = this.roleDao.findById(idRole);
		if (role.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(role.get(), RoleViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Role", idRole.toString()));
		}
	}

	public PioneerResponse<Map<String, Object>> getRoleByTitleMatch(final String title, Pageable pageable) {
		final Page<Role> roles = this.roleDao.findByTitleStartsWith(title, pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("roles", roles.getContent());
		response.put("currentPage", roles.getNumber());
		response.put("totalItems", roles.getTotalElements());
		response.put("totalPages", roles.getTotalPages());
		if (!roles.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Role", title));
		}
	}

	public PioneerResponse<Map<String, Object>> getAllRoles(Pageable pageable) {
		final Page<Role> roles = this.roleDao.findAll(pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("roles", roles.getContent());
		response.put("currentPage", roles.getNumber());
		response.put("totalItems", roles.getTotalElements());
		response.put("totalPages", roles.getTotalPages());
		if (roles != null && !roles.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Roles", "Not Found"));
		}
	}

	public String createRole(final RoleEditRest roleEditRest) throws PioneerException {
		final Role roleEdit = new Role();
		BeanUtils.copyProperties(roleEditRest, roleEdit);
		roleEdit.setCreatedAt(new Date());
		roleEdit.setUpdatedAt(new Date());
		this.roleDao.save(roleEdit);
		return "ROLE_CREATE";
	}

	public String addPermissionsToRole(final IdGenericWrapper body) throws PioneerException {
		final Permission permission = this.permissionDao.getById(body.getSource());
		for (Integer id : body.getDestination()) {
			final Role role = this.roleDao.getById(id);
			if (!role.getPermissions().contains(permission)) {
				role.getPermissions().add(permission);
				this.roleDao.save(role);
			}
		}
		return "ROLE_ADDED";
	}

	public String createRoleWithPermissions(final IdRoleWrapper body) throws PioneerException {
		final Role roleEdit = new Role();
		BeanUtils.copyProperties(body.getRoleEditRest(), roleEdit);
		roleEdit.setCreatedAt(new Date());
		roleEdit.setUpdatedAt(new Date());
		final Set<Permission> permissions = new TreeSet<Permission>();
		for (Integer id : body.getIdsPermissions()) {
			Optional<Permission> tmp_p = this.permissionDao.findById(id);
			if (tmp_p.isPresent()) {
				permissions.add(tmp_p.get());
			}
		}
		if (permissions != null && !permissions.isEmpty()) {
			roleEdit.setPermissions(permissions);
			this.roleDao.save(roleEdit);
		}
		return "ROLE_CREATE";
	}

	public String modifyRole(final IdRoleWrapper body) throws PioneerException {
		final Optional<Role> roleEdit = this.roleDao.findByTitle(body.getRoleEditRest().getTitle());
		if (roleEdit.isPresent()) {
			final Role tmp_r = roleEdit.get();
			BeanUtils.copyProperties(body.getRoleEditRest(), tmp_r);
			tmp_r.setUpdatedAt(new Date());
			if (!body.getIdsPermissions().isEmpty()) {
				final Set<Permission> permissions = new TreeSet<Permission>();
				for (Integer id : body.getIdsPermissions()) {
					final Optional<Permission> tmp_p = this.permissionDao.findById(id);
					if (tmp_p.isPresent()) {
						permissions.add(tmp_p.get());
					}
				}
				if (permissions.size() > 0) {
					tmp_r.setPermissions(permissions);
				}
			} else {
				tmp_r.getPermissions().clear();
			}
			this.roleDao.saveAndFlush(tmp_r);
			return "ROLE_MODIFY";
		} else {
			return "ERROR";
		}
	}

	public String deleteRole(final Integer idRole) throws PioneerException {
		final Optional<Role> role = this.roleDao.findById(idRole);
		final List<Group> groups = this.groupDao.findByRoles(role.get());
		if (role.isPresent()) {
			for (Group group : groups) {
				this.groupService.deleteRoleFromGroup(group.getIdGroup(), idRole);
			}
			this.roleDao.deleteById(idRole);
			return "GROUP_DELETE";
		} else {
			return "ERROR";
		}
	}

	public String deletePermissionFromRole(Integer idRole, Integer idPermission) throws PioneerException {
		final Optional<Role> role = this.roleDao.findById(idRole);
		if (role.isPresent()) {
			role.get().getPermissions().remove(this.permissionDao.findById(idPermission).get());
			return "ROLE_DELETE";
		} else {
			return "ERROR";
		}
	}

	public PioneerResponse<RoleViewRest> getRoleByTitle(String title) {
		final Optional<Role> role = this.roleDao.findByTitle(title);
		if (role.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(role.get(), RoleViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Role", title));
		}
	}
}