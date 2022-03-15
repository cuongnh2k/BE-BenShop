package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    @Modifying
    @Query(nativeQuery = true
            , value = "DELETE FROM product_entity_categories WHERE categories_id = ?1 ")
    void deleteProductCategoryById(Long id);

    ProductEntity findByIdAndDeletedFlagFalse(Long id);
}
