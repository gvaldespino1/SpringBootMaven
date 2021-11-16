package com.pioneer.intel.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pioneer.intel.entity.Event;
import com.pioneer.intel.entity.LogPioneer;


@Repository("eventDao")
public interface EventDao extends JpaRepository<Event, Integer> { 

	@Query("SELECT model FROM Event model WHERE model.name LIKE %:nameevent% AND model.relatedEntity = :Table")
	Optional<Event> findByEventAndTable(@Param("nameevent") final String nameEvent,
			@Param("Table") final String nameTable);

	void save(LogPioneer logEdit); 


}