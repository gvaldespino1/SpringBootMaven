package com.pioneer.intel.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "event")
public class Event implements Serializable {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_event", unique = true, nullable = false)
	private Integer idevent;
	@Column(name = "name", nullable = false, length = 20)
	private String name;
	@Column(name = "description", nullable = false, length = 500)
	private String description;
	@Column(name = "related_entity", nullable = false, length = 20)
	private String relatedEntity;
	@Column(name = "status_e", nullable = false)
	private String status;
	@Column(name = "date_creation")
	private Date DateCreation;
	@Column(name = "user_event", nullable = false)
	private String userEvent;
	@Column(name = "date_event")
	private Date dateEvent;

	// Property accessors
	public String getName() {
		return name;
	}

	/**
	 * @return the idevent
	 */
	public Integer getIdevent() {
		return idevent;
	}

	/**
	 * @param idevent the idevent to set
	 */
	public void setIdevent(Integer idevent) {
		this.idevent = idevent;
	}

	/**
	 * @return the relatedEntity
	 */
	public String getRelatedEntity() {
		return relatedEntity;
	}

	/**
	 * @param relatedEntity the relatedEntity to set
	 */
	public void setRelatedEntity(String relatedEntity) {
		this.relatedEntity = relatedEntity;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Date getDateCreation() {
		return DateCreation;
	}

	public void setDateCreation(final Date DateCreation) {
		this.DateCreation = DateCreation;
	}

	public String getUserEvent() {
		return userEvent;
	}

	public void setUserEvent(final String userEvent) {
		this.userEvent = userEvent;
	}

	public Date getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(final Date dateEvent) {
		this.dateEvent = dateEvent;
	}
}