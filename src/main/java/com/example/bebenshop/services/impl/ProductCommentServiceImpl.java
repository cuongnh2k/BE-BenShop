package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;
import com.example.bebenshop.entities.ProductCommentEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.ProductCommentMapper;
import com.example.bebenshop.repository.ProductCommentRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.ProductCommentService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommentServiceImpl implements ProductCommentService {
    private final ProductCommentRepository mProductCommentRepository;
    private final ProductCommentMapper mProductCommentMapper;
    private final ProductRepository mProductRepository;
    private final UserService mUserService;

    @Override
    public ProductCommentProduceDto createProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id) {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(id);
        if (productEntity == null) {
            throw new BadRequestException("Product does not exist");
        }
        ProductCommentEntity productCommentEntity = productCommentConsumeDto.toProductCommentEntity();
        if (productCommentEntity.getParentId() != null && !mProductCommentRepository.existsById(productCommentEntity.getParentId())) {
            throw new BadRequestException("ParentId product comment does not exist");
        }
        if (productCommentEntity.getParentId() == null) {
            productCommentEntity.setParentId(0L);
        }
        UserEntity userEntity = mUserService.getCurrentUser();
        productCommentEntity.setProduct(productEntity);
        productCommentEntity.setUser(userEntity);
        return mProductCommentMapper.toProductCommentProduceDto(mProductCommentRepository.save(productCommentEntity));
    }

    @Override
    public ProductCommentProduceDto editProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id) {
        ProductCommentEntity productCommentEntity = mProductCommentRepository.findById(id).orElse(null);
        if (productCommentEntity == null) {
            throw new BadRequestException("Product comment does not exist");
        }
        if (productCommentEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        productCommentEntity.setContent(productCommentConsumeDto.toProductCommentEntity().getContent());
        return mProductCommentMapper.toProductCommentProduceDto(mProductCommentRepository.save(productCommentEntity));
    }

    @Override
    public void deleteProductComment(Long id) {
        ProductCommentEntity productCommentEntity = mProductCommentRepository.findById(id).orElse(null);
        if (productCommentEntity == null) {
            throw new BadRequestException("Product comment does not exist");
        }
        if (productCommentEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        mProductCommentRepository.deleteProductComment(id);
    }
}
