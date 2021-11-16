/**
 * @(#) RoleDao.java
 *
 * Project: PioneerWS
 * Title: RoleDao.java 
 * Description:
 * Copyright: Copyright(c)2021
 * Company: 
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pioneer.intel.entity.Permission;
import com.pioneer.intel.entity.Role;

/**
 * DAO
 * 
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.dao
 */
@Repository("roleDao")
public interface RoleDao extends JpaRepository<Role, Integer> {

	Page<Role> findByTitleStartsWith(String title, Pageable pageable);

	Optional<Role> findByTitle(String title);

	List<Role> findByPermissions(Permission permissions);

	
}