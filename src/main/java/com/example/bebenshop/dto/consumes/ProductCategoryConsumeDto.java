package com.example.bebenshop.dto.consumes;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductCategoryConsumeDto implements Serializable {

    private Long productId;

    private Long categoryId;
}
