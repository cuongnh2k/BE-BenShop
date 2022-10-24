package com.example.bekiashop.dto.produces;

import com.example.bekiashop.bases.BaseProduceDto;
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
public class CategoryProduce1Dto extends BaseProduceDto<Long> {

    private String name;

    private Long parentId;

    private List<CategoryProduce2Dto> categories2;
}
