package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductCommentEntity, Long> {

    boolean existsById(Long parentId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM product_comment_entity where  id=?1")
    void deleteProductComment(Long id);

    Page<ProductCommentEntity> findByProductIdAndParentId(Long productId, Long parentId, Pageable pageable);

    List<ProductCommentEntity> findByParentId(Long parentId);
}
