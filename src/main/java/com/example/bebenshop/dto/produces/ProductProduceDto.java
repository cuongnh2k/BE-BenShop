package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import com.example.bebenshop.entities.CategoryEntity;
import com.example.bebenshop.entities.OrderDetailEntity;
import com.example.bebenshop.entities.ProductImageEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductProduceDto extends BaseProduceDto<Long> {
    private String name;


    private String description;

    private BigDecimal price;

    private Integer total;

    private Integer discount;

    private String size;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderDetailProduceDto> orderDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductImageProduceDto> productImages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CategoryProduceDto> categories;
}
