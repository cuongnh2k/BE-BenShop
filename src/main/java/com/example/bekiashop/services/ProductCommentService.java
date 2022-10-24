package com.example.bekiashop.services;

import com.example.bekiashop.bases.BaseListProduceDto;
import com.example.bekiashop.dto.consumes.ProductCommentConsumeDto;
import com.example.bekiashop.dto.produces.ProductCommentProduceDto;
import org.springframework.data.domain.Pageable;

public interface ProductCommentService {

    ProductCommentProduceDto createProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id);

    ProductCommentProduceDto editProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id);

    void deleteProductComment(Long id);

    BaseListProduceDto<ProductCommentProduceDto> getCommentByProductId(Long id, Pageable pageable);
}
