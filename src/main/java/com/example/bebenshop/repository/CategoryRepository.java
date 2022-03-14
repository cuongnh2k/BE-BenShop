package com.example.bebenshop.repository;

import com.example.bebenshop.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Modifying
    @Query(nativeQuery = true
            , value = "DELETE FROM product_entity_categories WHERE categories_id = ?1 ")
    void deleteProductCategoryById(Long id);

    CategoryEntity findByName(String name);
}
