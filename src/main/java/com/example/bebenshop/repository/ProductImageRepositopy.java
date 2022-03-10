package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepositopy extends JpaRepository<ProductImageEntity, Long> {
}
