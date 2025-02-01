package com.project.webshopproject.repository;

import com.project.webshopproject.model.Asks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface InquiryRepo extends JpaRepository<Asks, Long>{
    List<Asks> findByUserID(Long userID);
    Optional<Asks> findByIdAndUserID(Long id, Long userID);
}
