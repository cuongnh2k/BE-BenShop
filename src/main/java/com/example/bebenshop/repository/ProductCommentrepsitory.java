package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCommentrepsitory extends JpaRepository<ProductCommentEntity,Long> {
    boolean existsById(Long parentId);
}
