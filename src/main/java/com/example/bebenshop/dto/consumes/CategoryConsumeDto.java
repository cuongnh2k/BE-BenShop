package com.example.bebenshop.dto.consumes;

import com.example.bebenshop.entities.CategoryEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryConsumeDto implements Serializable {

    private String name;

    private Long parentId;

    private String productCategories;

    public CategoryEntity toCategoryEntity() {
        return CategoryEntity.builder()
                .name(name)
                .parentId(parentId)
                .build();
    }
}
