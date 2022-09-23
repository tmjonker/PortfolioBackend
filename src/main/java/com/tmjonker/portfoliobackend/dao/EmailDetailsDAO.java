package com.tmjonker.portfoliobackend.dao;

import com.tmjonker.portfoliobackend.entities.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDetailsDAO extends JpaRepository<EmailDetails, Integer> {


}
