/**
 * @(#) GroupDao.java
 *
 * Project: PioneerWS
 * Title: GroupDao.java 
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

import com.pioneer.intel.entity.Group;
import com.pioneer.intel.entity.Role;

/**
 * DAO
 * 
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.dao
 */
@Repository("groupDao")
public interface GroupDao extends JpaRepository<Group, Integer> {

	Page<Group> findByTitleStartsWith(String title, Pageable pageable);

	Optional<Group> findByTitle(String title);

	List<Group> findByRoles(Role roles);


}