package com.example.bekiashop.dto.produces;

import com.example.bekiashop.bases.BaseProduceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductImageProduceDto extends BaseProduceDto<Long> {

    private String path;

    private ProductProduceDto product;
}
