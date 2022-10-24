package com.example.bekiashop.repository;

import com.example.bekiashop.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByIdAndDeletedFlagFalse(Long id);

    //    ALTER TABLE product_entity ADD FULLTEXT(`name`,`description`)
    @Query(nativeQuery = true
            , value = "SELECT * FROM product_entity " +
            "WHERE deleted_flag IS FALSE " +
            "AND IF(?1 != -1, MATCH(`name`, `description`) AGAINST(?1), ?1) " +
            "AND IF(?2 != -1, price / 100 * (100 - discount) >= ?2, ?2) " +
            "AND IF(?3 != -1, price / 100 * (100 - discount) <= ?3, ?3) " +
            "AND IF(?4 != -1, id IN (?5), ?4) " +
            "AND IF(?6 != -1, id = ?6, ?6) ")
    Page<ProductEntity> searchByTitleOrDescription(
            String search
            , BigDecimal priceMin
            , BigDecimal priceMax
            , String categoryIds
            , List<Long> categoryIdList
            , Long productId
            , Pageable pageable);

    @Query(nativeQuery = true
            , value = "SELECT p.id FROM product_entity p " +
            "INNER JOIN product_entity_categories pc ON p.id = pc.product_entity_id " +
            "INNER JOIN category_entity c ON pc.categories_id = c.id " +
            "WHERE c.id IN (?1) " +
            "GROUP BY p.id ")
    List<Long> getProductIdByCategoryId(List<Long> categoryIds);

    @Query(nativeQuery = true
            , value = "SELECT * FROM product_entity p1 " +
            "WHERE deleted_flag = 0 AND p1.id IN (SELECT p.id FROM product_entity p " +
            "INNER JOIN product_entity_categories pc2 ON  p.id = pc2.product_entity_id " +
            "WHERE pc2.categories_id IN (SELECT pc1.categories_id FROM product_entity_categories pc1 " +
            "WHERE pc1.product_entity_id = ?1) " +
            "GROUP BY p1.id) ")
    Page<ProductEntity> searchProductByProductId(Long id, Pageable pageable);
}
