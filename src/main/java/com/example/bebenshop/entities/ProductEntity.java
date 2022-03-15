package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductEntity extends BaseEntity {

    private String name;

    private BigDecimal price;

    private Integer discount;

    private String status;

    private String style;

    @Column(length = 20)
    private String gender;

    private String origin;

    private String material;

    private String productionMethod;

    private String size;

    private String accessory;

    private String washingMethod;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(targetEntity = OrderDetailEntity.class, mappedBy = "product")
    private Collection<OrderDetailEntity> orderDetails;

    @OneToMany(targetEntity = ProductImageEntity.class, mappedBy = "product")
    private Collection<ProductImageEntity> productImages;

    @ManyToMany
    private Collection<CategoryEntity> categories;

    @OneToMany(targetEntity = ProductCommentEntity.class, mappedBy = "product")
    private Collection<ProductCommentEntity> productComments;
}
