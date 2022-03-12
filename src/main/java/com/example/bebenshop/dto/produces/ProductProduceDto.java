package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.enums.ProductStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductProduceDto extends BaseProduceDto<Long> {

    private String name;

    private BigDecimal price;

    private Integer discount;

    private ProductStatusEnum status;

    private String style;

    private String gender;

    private String origin;

    private String material;

    private String productionMethod;

    private String size;

    private String accessory;

    private String washingMethod;

    private String description;

    private List<OrderDetailProduceDto> orderDetails;

    private List<ProductImageProduceDto> productImages;

    private List<ProductCategoryProduceDto> productCategories;

    private List<ProductCommentProduceDto> productComments;
}
