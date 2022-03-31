package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductCommentProduceDto extends BaseProduceDto<Long> {

    private String content;

    private Long parentId;

    private ProductProduceDto product;

    private UserProduceDto user;

    private List<ProductCommentProduce1Dto> productComment1;

    private String createdBy;
}
