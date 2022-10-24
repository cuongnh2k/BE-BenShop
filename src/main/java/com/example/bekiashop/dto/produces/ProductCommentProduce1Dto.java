package com.example.bekiashop.dto.produces;

import com.example.bekiashop.bases.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductCommentProduce1Dto extends BaseProduceDto<Long> {

    private String content;

    private Long parentId;

    private ProductProduceDto product;

    private UserProduceDto user;

    private String createdBy;
}
