package com.example.bekiashop.repository;

import com.example.bekiashop.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Modifying
    @Query(nativeQuery = true
            , value = "DELETE FROM product_entity_categories WHERE categories_id IN (?1) ")
    void deleteProductCategoryById(List<Long> ids);

    List<CategoryEntity> findByParentId(Long id);


    Boolean existsByName(String name);

    CategoryEntity findByName(String name);

    boolean existsById(Long parentId);
}
