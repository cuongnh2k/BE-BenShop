package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
public class CategoryProduceDto extends BaseProduceDto<Long> {

    private String name;

    private Long parentId;

    private List<ProductCategoryProduceDto> productCategories;
}
