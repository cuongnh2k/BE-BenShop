package com.example.bekiashop.repository;

import com.example.bekiashop.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

   Boolean existsByPath(String path);
}