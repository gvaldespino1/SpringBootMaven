package com.pioneer.intel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pioneer.intel.entity.LogPioneer;

@Repository("logDao")
public interface LogDao extends JpaRepository<LogPioneer, Integer> {
}