package com.ava.task.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.task.model.impl.UserAudit;
import com.ava.task.repository.UserAuditRepository;

@Service
public class UserAuditService {
	
	@Autowired
	private UserAuditRepository userAuditRepository;

	public void createAudit(String path, String method, String username, Date date) {
		userAuditRepository.save(new UserAudit(path, username, method, date));
	}
	
}
