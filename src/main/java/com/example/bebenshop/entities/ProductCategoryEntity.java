package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductCategoryEntity extends BaseEntity {

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(columnDefinition = "product_id")
    private ProductEntity product;

    @ManyToOne(targetEntity = CategoryEntity.class)
    @JoinColumn(columnDefinition = "category_id")
    private CategoryEntity category;
}
