package com.project.webshopproject.repository;

import com.project.webshopproject.model.Ask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface AskRepo extends JpaRepository<Ask, Long>{
    List<Ask> findByUserID(Long userID);
    Optional<Ask> findByIdAndUserID(Long id, Long userID);
}
