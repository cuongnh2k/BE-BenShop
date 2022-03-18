package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductCommentEntity,Long> {
    boolean existsById(Long parentId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM product_comment_entity where  id=?1")
    void deleteProductComment(Long id);
}
