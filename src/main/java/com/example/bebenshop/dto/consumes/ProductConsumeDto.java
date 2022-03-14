package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.enums.ProductStatusEnum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductConsumeDto implements Serializable {

    private String name;

    private BigDecimal price;

    private Integer discount;

    private String style;

    private String gender;

    private String origin;

    private String material;

    private String productionMethod;

    private String size;

    private String accessory;

    private String washingMethod;

    private String description;

    private String orderDetails;

    private String productImages;

    private String categories;

    private String productComments;

    private MultipartFile[] fileImages;

    public ProductEntity toProductEntity() {
        return ProductEntity.builder()
                .name(name)
                .price(price)
                .discount(discount)
                .status(ProductStatusEnum.STOCKING)
                .style(style)
                .gender(gender)
                .origin(origin)
                .material(material)
                .productionMethod(productionMethod)
                .size(size)
                .accessory(accessory)
                .washingMethod(washingMethod)
                .description(description)
                .build();
    }

}
