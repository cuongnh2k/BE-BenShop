package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;

public interface ProductCommentService {

    ProductCommentProduceDto createProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id);

    ProductCommentProduceDto editProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id);

    void deleteProductComment(Long id);
}
