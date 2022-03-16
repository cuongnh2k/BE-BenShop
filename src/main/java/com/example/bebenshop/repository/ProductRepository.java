package com.example.bebenshop.repository;

import com.example.bebenshop.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByIdAndDeletedFlagFalse(Long id);

    //    ALTER TABLE product_entity ADD FULLTEXT(`name`,`description`)
    @Query(nativeQuery = true
            , value = "SELECT p.* FROM product_entity p\n" +
            "INNER JOIN product_entity_categories pc ON p.id=pc.product_entity_id\n" +
            "INNER JOIN category_entity c ON pc.categories_id=c.id\n" +
            "WHERE p.deleted_flag IS FALSE\n" +
            "AND IF('xyz' != ?1, MATCH(p.`name`, p.`description`) AGAINST(?1), 1)\n" +
            "AND IF(-1 != ?2, c.id = ?2, 1)\n" +
            "AND IF(-1 != ?3, p.price >= ?3, 1)\n" +
            "AND IF(-1 != ?4, p.price <= ?4, 1)\n" +
            "GROUP BY p.id")
    Page<ProductEntity> searchByTitleOrDescription(
            String search
            , Long categoryId
            , BigDecimal priceMin
            , BigDecimal priceMax
            , Pageable pageable);
}
