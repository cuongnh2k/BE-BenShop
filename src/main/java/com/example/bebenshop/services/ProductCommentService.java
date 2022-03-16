package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;

public interface ProductCommentService {
    ProductCommentProduceDto CreateProductComment(ProductCommentConsumeDto productCommentConsumeDto,Long id);

}
