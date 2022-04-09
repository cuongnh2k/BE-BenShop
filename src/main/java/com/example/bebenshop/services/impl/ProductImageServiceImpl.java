package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.produces.ProductProduceDto;
import com.example.bebenshop.entities.ProductEntity;
import com.example.bebenshop.entities.ProductImageEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.ProductImageMapper;
import com.example.bebenshop.mapper.ProductMapper;
import com.example.bebenshop.repository.ProductImageRepository;
import com.example.bebenshop.repository.ProductRepository;
import com.example.bebenshop.services.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository mProductImageRepository;
    private final ProductRepository mProductRepository;
    private final ProductMapper mProductMapper;
    private final ProductImageMapper mProductImageMapper;

    @Value("${root.directory}")
    String ROOT_DIRECTORY;

    @Value("${subfolder.product.image}")
    String SUBFOLDER_PRODUCT_IMAGE;

    @Value("${domain}")
    String DOMAIN;

    @Override
    public ProductProduceDto addProductImage(Long id, MultipartFile multipartFile) throws IOException {
        ProductEntity productEntity = mProductRepository.findByIdAndDeletedFlagFalse(id);
        if (productEntity == null) {
            throw new BadRequestException("Product image does not exist");
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        {
            if (fileName.contains(".")) {
                String[] arr = multipartFile.getOriginalFilename().split("\\.");
                if (!arr[1].equalsIgnoreCase("JPG") && !arr[1].equalsIgnoreCase("PNG")) {
                    throw new BadRequestException("Image must be in jpg or png format");
                }
            } else {
                throw new BadRequestException("Empty image");
            }
        }
        String uploadDir = ROOT_DIRECTORY + SUBFOLDER_PRODUCT_IMAGE + "/" + id;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            if (mProductImageRepository.existsByPath(DOMAIN + SUBFOLDER_PRODUCT_IMAGE + "/" + id + "/" + multipartFile.getOriginalFilename())) {
                throw new BadRequestException("Product image already exists");
            }
            mProductImageRepository.save(ProductImageEntity.builder()
                    .product(productEntity)
                    .path(DOMAIN + SUBFOLDER_PRODUCT_IMAGE + "/" + id + "/" + multipartFile.getOriginalFilename())
                    .build());

        } catch (Exception ioe) {
            throw new BadRequestException("Empty image");
        }

        ProductEntity productEntity1 = mProductRepository.findByIdAndDeletedFlagFalse(id);
        ProductProduceDto productProduceDto = mProductMapper.toProductProduceDto(productEntity1);
        productProduceDto.setProductImages(productEntity1.getProductImages().stream()
                .map(mProductImageMapper::toProductImageProduceDto).collect(Collectors.toList()));
        return productProduceDto;
    }

    @Override
    public void editProductImage(Long id, MultipartFile multipartFile) throws IOException {
        ProductImageEntity productImageEntity = mProductImageRepository.findById(id).orElse(null);
        {
            if (productImageEntity == null) {
                throw new BadRequestException("Product image does not exist");
            }
            String[] arr = productImageEntity.getPath().split("/");
            String path = ROOT_DIRECTORY;
            for (int i = 3; i < arr.length; i++) {
                path += "/" + arr[i];
            }
            try {
                File file = new File(path);
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            {
                if (fileName.contains(".")) {
                    String[] arr = multipartFile.getOriginalFilename().split("\\.");
                    if (!arr[1].equalsIgnoreCase("JPG") && !arr[1].equalsIgnoreCase("PNG")) {
                        throw new BadRequestException("Image must be in jpg or png format");
                    }
                } else {
                    throw new BadRequestException("Empty image");
                }
            }
            String uploadDir = ROOT_DIRECTORY + SUBFOLDER_PRODUCT_IMAGE + "/" + id;
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                if (mProductImageRepository.existsByPath(DOMAIN + SUBFOLDER_PRODUCT_IMAGE + "/" + id + "/" + multipartFile.getOriginalFilename())) {
                    throw new BadRequestException("Product image already exists");
                }
                productImageEntity.setPath(DOMAIN + SUBFOLDER_PRODUCT_IMAGE + "/" + id + "/" + multipartFile.getOriginalFilename());
                mProductImageRepository.save(productImageEntity);
            } catch (Exception ioe) {
                throw new BadRequestException("Empty image");
            }
        }
    }

    @Override
    public void deleteProductImage(Long id) {
        ProductImageEntity productImageEntity = mProductImageRepository.findById(id).orElse(null);
        if (productImageEntity == null) {
            throw new BadRequestException("Product image does not exist");
        }
        String[] arr = productImageEntity.getPath().split("/");
        String path = ROOT_DIRECTORY;
        for (int i = 3; i < arr.length; i++) {
            path += "/" + arr[i];
        }
        try {
            File file = new File(path);
            file.delete();
            mProductImageRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
