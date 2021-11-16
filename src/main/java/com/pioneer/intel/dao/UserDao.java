/**
 * @(#) UserDao.java
 *
 * Project: PioneerWS
 * Title: UserDao.java 
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
import com.pioneer.intel.entity.User;

/**
 * DAO
 * 
 * @author Gustavo Valdespino
 * @package com.pioneer.intel.dao
 */
@Repository("userDao")
public interface UserDao extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	Page<User> findByUsernameStartsWith(String username, Pageable pageable);

	Page<User> findByGroups(Group groups, Pageable pageable);

	List<User> findByGroups(Group groups);

	List<User> findTop3ByGroupsOrderByRegisteredAtDesc(Group groups);

	
}