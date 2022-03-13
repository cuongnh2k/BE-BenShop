package com.example.bebenshop.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository {

    @Query(nativeQuery = true, value = "DELETE FROM product_entity_categories WHERE categories_id = ?1")
    void deleteById(Long id);
}
