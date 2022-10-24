package com.example.bekiashop.dto.consumes;

import com.example.bekiashop.entities.ProductImageEntity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageConsumeDto implements Serializable {

    private String path;

    private Long productId;

    public ProductImageEntity toProductImageEntity(){
        return ProductImageEntity.builder()
                .path(path)
                .build();

    }
}
