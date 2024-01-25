package com.woof.api.productManager.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.ProductCeoImage;
import com.woof.api.productCeo.model.dto.ProductCeoReadRes;
import com.woof.api.productCeo.model.dto.ProductCeoReadRes2;
import com.woof.api.productManager.model.dto.ProductManagerCreateReq;
import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.model.ProductManagerImage;
import com.woof.api.productManager.model.dto.ProductManagerListRes;
import com.woof.api.productManager.model.dto.ProductManagerReadRes;
import com.woof.api.productManager.model.dto.ProductManagerReadRes2;
import com.woof.api.productManager.model.dto.ProductManagerUpdateReq;
import com.woof.api.productManager.repository.ProductManagerImageRepository;
import com.woof.api.productManager.repository.ProductManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductManagerService {
    private final ProductManagerRepository productManagerRepository;
    private final ProductManagerImageRepository productManagerImageRepository;
    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public ProductManager createManager(ProductManagerCreateReq productManagerCreateReq) {

        return productManagerRepository.save(ProductManager.builder()
                .idx(productManagerCreateReq.getIdx())
                .gender(productManagerCreateReq.getGender())
                .phoneNumber(productManagerCreateReq.getPhoneNumber())
                .managerName(productManagerCreateReq.getManagerName())
                .price(productManagerCreateReq.getPrice())
                .contents(productManagerCreateReq.getContents())
                .build());
    }

    @Transactional
    public ProductManagerListRes listManager() {
        List<ProductManager> resultManager = productManagerRepository.findAll();
        List<ProductManagerReadRes> productManagerReadResList = new ArrayList<>();

        for (ProductManager productManager : resultManager) {
            List<ProductManagerImage> productManagerImages = productManager.getProductManagerImages();

            String filenames = "";
            for (ProductManagerImage productManagerImage : productManagerImages) {
                String filename = productManagerImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            ProductManagerReadRes productManagerReadRes = ProductManagerReadRes.builder()
                    .idx(productManager.getIdx())
                    .managerName(productManager.getManagerName())
                    .gender(productManager.getGender())
                    .phoneNumber(productManager.getPhoneNumber())
                    .price(productManager.getPrice())
                    .contents(productManager.getContents())
                    .filename(filenames)
                    .build();

            productManagerReadResList.add(productManagerReadRes);
        }


        return ProductManagerListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(productManagerReadResList)
                .build();
    }

    @Transactional
    public ProductManagerReadRes2 readManager(Long idx) {
        Optional<ProductManager> resultManager = productManagerRepository.findById(idx);

        if (resultManager.isPresent()) {
            ProductManager productManager = resultManager.get();

            List<ProductManagerImage> productManagerImages = productManager.getProductManagerImages();

            String filenames = "";
            for (ProductManagerImage productManagerImage : productManagerImages) {
                String filename = productManagerImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            ProductManagerReadRes productManagerReadRes = ProductManagerReadRes.builder()
                    .idx(productManager.getIdx())
                    .managerName(productManager.getManagerName())
                    .gender(productManager.getGender())
                    .phoneNumber(productManager.getPhoneNumber())
                    .price(productManager.getPrice())
                    .contents(productManager.getContents())
                    .filename(filenames)
                    .build();

            return ProductManagerReadRes2.builder()
                    .code(1000)
                    .message("요청 성공.")
                    .success(true)
                    .isSuccess(true)
                    .result(productManagerReadRes)
                    .build();
        }
        return null;
    }

    public void updateManager(ProductManagerUpdateReq productManagerUpdateReq) {
        Optional<ProductManager> result = productManagerRepository.findById(productManagerUpdateReq.getIdx());
        if (result.isPresent()) {
            ProductManager productManager = result.get();
            productManager.setManagerName(productManagerUpdateReq.getManagerName());
            productManager.setGender(productManagerUpdateReq.getGender());
            productManager.setPhoneNumber(productManagerUpdateReq.getPhoneNumber());
            productManager.setPrice(productManagerUpdateReq.getPrice());
            productManager.setContents(productManagerUpdateReq.getContents());

            productManagerRepository.save(productManager);
        }
    }

    @Transactional
    public void deleteManager(Long idx) {
        List<ProductManagerImage> all = productManagerImageRepository.findAllByProductManagerIdx(idx);
        List<ProductManagerImage> aa = new ArrayList<>();
        for (ProductManagerImage productManagerImage : all) {
            ProductManagerImage result = ProductManagerImage.builder()
                    .idx(productManagerImage.getIdx())
                    .build();
            aa.add(result);
        }

        for (ProductManagerImage productManagerImage : aa) {
            productManagerImageRepository.delete(productManagerImage);
        }

        productManagerRepository.delete(ProductManager.builder().idx(idx).build());
    }


    public String makeFolderManager() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        return folderPath;
    }


    public String uploadFileManager(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String folderPath = makeFolderManager();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = folderPath + File.separator + uuid + "_" + originalName;
        InputStream input = null;
        try {
            input = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());


            s3.putObject(bucket, saveFileName.replace(File.separator, "/"), input, metadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return s3.getUrl(bucket, saveFileName.replace(File.separator, "/")).toString();
    }

    public void saveFileManager(Long idx, String uploadPath) {
        productManagerImageRepository.save(ProductManagerImage.builder()
                .productManager(ProductManager.builder().idx(idx).build())
                .filename(uploadPath)
                .build());
    }

}
