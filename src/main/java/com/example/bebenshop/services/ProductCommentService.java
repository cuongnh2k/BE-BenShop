package com.example.bebenshop.services;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;
import org.springframework.data.domain.Pageable;

public interface ProductCommentService {

    ProductCommentProduceDto createProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id);

    ProductCommentProduceDto editProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id);

    void deleteProductComment(Long id);

    BaseListProduceDto<ProductCommentProduceDto> getCommentByProductId(Long id, Pageable pageable);
}
