package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    ProductEntity findByIdAndDeletedFlagFalse(Long id);
}
