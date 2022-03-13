package com.example.bebenshop.dto.produces;

import com.example.bebenshop.bases.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CategoryProduce2Dto extends BaseProduceDto<Long> {

    private String name;

    private Long parentId;

    private List<CategoryProduce3Dto> categories3;
}
