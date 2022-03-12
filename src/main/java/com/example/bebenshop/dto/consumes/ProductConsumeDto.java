package com.example.bebenshop.dto.consumes;


import com.example.bebenshop.entities.ProductEntity;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductConsumeDto implements Serializable {
    private String description;
    private BigDecimal price;
    private Integer total;
    private Integer discount;
    private String style;
    private String size;

    public ProductEntity toProductEntity() {
        return ProductEntity.builder()
                .description(description)
                .price(price)
                .total(total)
                .discount(discount)
                .size(size)
                .build();
    }

}
