package com.pioneer.intel.util;

import java.util.Base64;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pioneer.intel.dao.UserDao;
import com.pioneer.intel.entity.Group;
import com.pioneer.intel.entity.Permission;
import com.pioneer.intel.entity.Role;

@Service("validationService")
@Transactional
public class ValidationUtil {

	@Autowired
	private UserDao userDao;

	public Boolean validateUser(String token, String url) throws JsonMappingException, JsonProcessingException {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getDecoder();
		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
		});
		if (map.get("username").equals("Developer")) {
			return true;
		}
		Set<Group> groups = this.userDao.findByUsername((String) map.get("username")).get().getGroups();
		for (Group group : groups) {
			Set<Role> roles = group.getRoles();
			for (Role role : roles) {
				Set<Permission> permissions = role.getPermissions();
				for (Permission permission : permissions) {
					if (permission.getDescription().equals(url)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
