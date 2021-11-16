/**
 * @(#) BitacoraServiceImpl.java
 *
 * Project: PioneerService
 * Title: BitacoraServiceImpl.java
 * Description: Class that provides the services of the Event entity
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pioneer.intel.dao.EventDao;
import com.pioneer.intel.dao.LogDao;
import com.pioneer.intel.dao.UserDao;
import com.pioneer.intel.dto.ErrorDTO;
import com.pioneer.intel.entity.Event;
import com.pioneer.intel.entity.LogPioneer;
import com.pioneer.intel.exception.InternalServerErrorException;
import com.pioneer.intel.exception.NotFoundException;
import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.service.LogService;
import com.pioneer.intel.util.EventType;

/**
 * Service interface implementer
 *
 * @author Gustavo Valdespino
 */
@Service("logService")
@Transactional
public class LogServiceImpl implements LogService {

	// Property Fields Logger
	private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class.getName());

	// Property Fields
	@Autowired
	private LogDao logDao;
	@Autowired
	private EventDao eventDao;
	@Autowired
	private UserDao userDao;

	/**
	 * Invokes the service that stores a trace in the DB log
	 * 
	 * @param tipoMovimiento
	 * @param nombreTabla
	 * @param descripcion
	 * @param usuarioLogin
	 * @throws PaninisException
	 */
	public void createLog(final EventType eventType, final String nameTable, final String description,
			final String userLogin) throws PioneerException {
		final LogPioneer logEdit = new LogPioneer();

		final List<ErrorDTO> filtersEvent = new ArrayList<ErrorDTO>();
		filtersEvent.add(new ErrorDTO("Event", eventType.getName()));
		filtersEvent.add(new ErrorDTO("Table", nameTable));

		final Event event = this.eventDao.findByEventAndTable(eventType.getName(), nameTable)
				.orElseThrow(() -> new NotFoundException("Event", "EVENT_NOT_FOUND", filtersEvent));
		logEdit.setEvent(event);
		logEdit.setDescription(description);

		final List<ErrorDTO> filtersUser = new ArrayList<ErrorDTO>();
		filtersUser.add(new ErrorDTO("User", userLogin));

		/*
		 * final User userlogin = this.userDao.findByUsername(userlogin) .orElseThrow(()
		 * -> new NotFoundException("User", "USER_NOT_FOUND", filtersUsuario));
		 * logEdit.setUserCreation(userlogin); logEdit.setDateCreation(new Date());
		 */
		try {
			this.eventDao.save(logEdit);
		} catch (final Exception e) {
			log.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("LOG", "INTERNAL_SERVER_ERROR");
		}
	}
}