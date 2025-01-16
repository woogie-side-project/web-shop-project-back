package com.project.webshopproject.repository;

import com.project.webshopproject.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Items,Long> {
}
