package com.pioneer.intel.service;

import com.pioneer.intel.exception.PioneerException;
import com.pioneer.intel.util.EventType;

public interface LogService {

	/**
	 * Invokes the service that stores a trace in the DB log. 
	 * 
	 * @param eventType
	 * @param nombreTabla
	 * @param descripcion
	 * @param usuarioLogin
	 * @throws PaninisException
	 */ 
	public void createLog(final EventType eventType, final String nameTable, final String description,
		final String username) throws PioneerException;
}