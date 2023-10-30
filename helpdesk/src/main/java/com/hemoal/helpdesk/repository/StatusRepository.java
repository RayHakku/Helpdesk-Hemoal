package com.hemoal.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hemoal.helpdesk.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    
}
