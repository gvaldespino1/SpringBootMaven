/**
 * @(#) PermissionDao.java
 *
 * Project: PioneerWS
 * Title: PermissionDao.java 
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pioneer.intel.entity.Permission;

/**
 * DAO
 * 
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.dao
 */
@Repository("permissionDao")
public interface PermissionDao extends JpaRepository<Permission, Integer> {

	Optional<Permission> findByTitle(String title);

	Page<Permission> findByTitleStartsWith(String title, Pageable pageable);

	
}