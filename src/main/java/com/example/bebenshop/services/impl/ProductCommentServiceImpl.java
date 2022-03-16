package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;
import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.dto.produces.UserProduceDto;
import com.example.bebenshop.entities.ProductCommentEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.ProductCommentMapper;
import com.example.bebenshop.mapper.ProductMapper;
import com.example.bebenshop.mapper.UserMapper;
import com.example.bebenshop.repository.ProductCommentrepsitory;
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
    private final ProductCommentrepsitory mProductCommentrepsitory;
    private final ProductCommentMapper mProductCommentMapper;
    private final ProductRepository mProductRepository;
    private final UserService mUserService;
    private final ProductMapper mProductMapper;
    private final UserMapper mUserMapper;

    @Override
    public ProductCommentProduceDto createProductComment(ProductCommentConsumeDto productCommentConsumeDto, Long id) {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(id);g
        if (productEntity == null) {
            throw new BadRequestException("Id " + id + " not doest exits");
        }
        ProductCommentEntity productCommentEntity = productCommentConsumeDto.toProductCommentEntity();
        if (productCommentEntity.getParentId() != null && !mProductCommentrepsitory.existsById(productCommentEntity.getParentId())) {
            throw new BadRequestException(" parentId  " + productCommentEntity.getParentId() + " not does exits");
        }
        if (productCommentEntity.getParentId() == null) {
            productCommentEntity.setParentId(0L);
        }
        UserEntity userEntity = mUserService.getCurrentUser();
        productCommentEntity.setProduct(productEntity);
        productCommentEntity.setUser(userEntity);
        return mProductCommentMapper.toProductCommentProduceDto(mProductCommentrepsitory.save(productCommentEntity));
    }
}
