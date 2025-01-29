package com.project.webshopproject.repository;

import com.project.webshopproject.entity.Items;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Items,Long> {
}
