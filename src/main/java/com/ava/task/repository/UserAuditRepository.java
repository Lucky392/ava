package com.ava.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ava.task.model.impl.UserAudit;

public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {

}
