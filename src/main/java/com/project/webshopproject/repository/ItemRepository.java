package com.project.webshopproject.repository;

import com.project.webshopproject.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Items,Long> {
    @Query("select i from Items i left join fetch i.itemImages where i.id = :id")
    Optional<Items> findByIdWithImages(@Param("id") Long id);
}
