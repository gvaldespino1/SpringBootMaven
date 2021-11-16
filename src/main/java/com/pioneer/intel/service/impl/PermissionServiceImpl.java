/**
 * @(#) PermissionServiceImpl.java
 *
 * Project: PioneerWS
 * Title: PermissionServiceImpl.java
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

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pioneer.intel.dao.PermissionDao;
import com.pioneer.intel.dao.RoleDao;
import com.pioneer.intel.entity.Permission;
import com.pioneer.intel.entity.Role;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.json.PermissionEditRest;
import com.pioneer.intel.json.PermissionViewRest;
import com.pioneer.intel.response.PioneerResponse;
import com.pioneer.intel.service.PermissionService;
import com.pioneer.intel.service.RoleService;
import com.pioneer.intel.util.MessageUtil;

/**
 * Services Interface
 *
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.service.impl
 */
@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService {

	// Constantes usadas para el mapeo entre objetos
	private static final ModelMapper modelMapper = new ModelMapper();

	// Property Fields
	@Autowired
	private MessageUtil messageUtil;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @param IdUser
	 * @return PioneerResponse<UsuarioViewRest>
	 */
	public PioneerResponse<PermissionViewRest> getPermissionById(final Integer idPermission) {
		final Optional<Permission> permission = this.permissionDao.findById(idPermission);
		if (permission.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(permission.get(), PermissionViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Permission", idPermission.toString()));
		}
	}

	public PioneerResponse<Map<String, Object>> getPermissionByTitleMatch(final String title, final Pageable pageable) {
		final Page<Permission> permissions = this.permissionDao.findByTitleStartsWith(title, pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("permissions", permissions.getContent());
		response.put("currentPage", permissions.getNumber());
		response.put("totalItems", permissions.getTotalElements());
		response.put("totalPages", permissions.getTotalPages());
		if (!permissions.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Permission", title));
		}
	}

	public PioneerResponse<Map<String, Object>> getAllPermissions(final Pageable pageable) {
		final Page<Permission> permissions = this.permissionDao.findAll(pageable);
		Map<String, Object> response = new HashMap<>();
		response.put("permissions", permissions.getContent());
		response.put("currentPage", permissions.getNumber());
		response.put("totalItems", permissions.getTotalElements());
		response.put("totalPages", permissions.getTotalPages());
		if (!permissions.isEmpty()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(), response);
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Permission", "Not Found"));
		}
	}

	public String createPermission(final PermissionEditRest permissionEditRest) throws PioneerException {
		final Permission permissionEdit = new Permission();
		BeanUtils.copyProperties(permissionEditRest, permissionEdit);
		permissionEdit.setCreatedAt(new Date());
		permissionEdit.setUpdatedAt(new Date());
		this.permissionDao.save(permissionEdit);
		return "PERMISSION_CREATE";
	}

	public String modifyPermission(final PermissionEditRest permissionEditRest) throws PioneerException {
		final Optional<Permission> permissionEdit = this.permissionDao.findByTitle(permissionEditRest.getTitle());
		if (permissionEdit.isPresent()) {
			final Permission tmp_p = permissionEdit.get();
			BeanUtils.copyProperties(permissionEditRest, tmp_p);
			tmp_p.setUpdatedAt(new Date());
			this.permissionDao.saveAndFlush(tmp_p);
			return "PERMISSION_MODIFY";
		} else {
			return "ERROR";
		}
	}

	public String deletePermission(final Integer idPermission) throws PioneerException {
		final Optional<Permission> permission = this.permissionDao.findById(idPermission);
		final List<Role> roles = this.roleDao.findByPermissions(permission.get());
		if (permission.isPresent()) {
			for (Role role : roles) {
				this.roleService.deletePermissionFromRole(role.getIdRole(), idPermission);
			}
			this.permissionDao.deleteById(idPermission);
			return "PERMISSION_DELETE";
		} else {
			return "ERROR";
		}
	}

	public PioneerResponse<PermissionViewRest> getPermissionByTitle(String title) {
		final Optional<Permission> permission = this.permissionDao.findByTitle(title);
		if (permission.isPresent()) {
			return new PioneerResponse<>(0, "Success", this.messageUtil.getSuccessOperation(),
					modelMapper.map(permission.get(), PermissionViewRest.class));
		} else {
			return new PioneerResponse<>(HttpStatus.NOT_FOUND.value(), "Failure",
					messageUtil.getObjectDoesNotExists("Role", title));
		}
	}
}