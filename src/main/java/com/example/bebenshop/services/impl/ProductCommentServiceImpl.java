package com.example.bebenshop.services.impl;

import com.example.bebenshop.bases.BaseListProduceDto;
import com.example.bebenshop.dto.consumes.ProductCommentConsumeDto;
import com.example.bebenshop.dto.produces.ProductCommentProduce1Dto;
import com.example.bebenshop.dto.produces.ProductCommentProduceDto;
import com.example.bebenshop.entities.ProductCommentEntity;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.ProductCommentMapper;
import com.example.bebenshop.mapper.UserMapper;
import com.example.bebenshop.repository.ProductCommentRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.ProductCommentService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommentServiceImpl implements ProductCommentService {
    private final ProductCommentRepository mProductCommentRepository;
    private final ProductCommentMapper mProductCommentMapper;
    private final ProductRepository mProductRepository;
    private final UserService mUserService;
    private final UserMapper mUserMapper;


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
        if (!productCommentEntity.getCreatedBy().equals(mUserService.getUserName())) {
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
        if (!productCommentEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        mProductCommentRepository.deleteProductComment(id);
    }

    @Override
    public BaseListProduceDto<ProductCommentProduceDto> getCommentByProductId(Long id, Pageable pageable) {
        Page<ProductCommentEntity> productCommentEntityPage = mProductCommentRepository.findByProductIdAndParentId(
                id,
                0L
                , pageable);
        List<ProductCommentProduceDto> productCommentProduceDtoList = productCommentEntityPage.getContent().stream().map(o -> {
            ProductCommentProduceDto productCommentProduceDto = mProductCommentMapper.toProductCommentProduceDto(o);
            productCommentProduceDto.setUser(mUserMapper.toUserProduceDto(o.getUser()));
            return productCommentProduceDto;
        }).collect(Collectors.toList());

        List<ProductCommentProduceDto> productCommentProduceDtoList1 = productCommentProduceDtoList.stream()
                .map(o -> {
                    List<ProductCommentEntity> productCommentEntity = mProductCommentRepository.findByParentId(o.getId());
                    List<ProductCommentProduceDto> productCommentProduceDto = productCommentEntity.stream().map(oo -> {
                        ProductCommentProduceDto productCommentProduceDto1 = mProductCommentMapper.toProductCommentProduceDto(oo);
                        productCommentProduceDto1.setUser(mUserMapper.toUserProduceDto(oo.getUser()));
                        return productCommentProduceDto1;
                    }).collect(Collectors.toList());
                    return ProductCommentProduceDto.builder()
                            .id(o.getId())
                            .createdDate(o.getCreatedDate())
                            .updatedDate(o.getUpdatedDate())
                            .content(o.getContent())
                            .parentId(o.getParentId())
                            .user(o.getUser())
                            .productComment1(productCommentProduceDto.stream().map(oo ->
                                    ProductCommentProduce1Dto.builder()
                                            .id(oo.getId())
                                            .createdDate(oo.getCreatedDate())
                                            .updatedDate(oo.getUpdatedDate())
                                            .content(oo.getContent())
                                            .parentId(oo.getParentId())
                                            .user(oo.getUser())
                                            .build()
                            ).collect(Collectors.toList()))
                            .build();
                }).collect(Collectors.toList());
        return BaseListProduceDto.<ProductCommentProduceDto>builder()
                .content(productCommentProduceDtoList1)
                .totalElements(productCommentEntityPage.getTotalElements())
                .totalPages(productCommentEntityPage.getTotalPages())
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .build();
    }
}
