package com.project.webshopproject.repository;

import com.project.webshopproject.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface InquiryRepo extends JpaRepository<Inquiry, Long>{
    List<Inquiry> findByUserID(Long userID);
    Optional<Inquiry> findByIdAndUserID(Long id, Long userID);
}
