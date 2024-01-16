package com.woof.api.productCeo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.woof.api.member.model.entity.Member;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.ProductCeoImage;
import com.woof.api.productCeo.model.dto.ProductCeoCreateReq;
import com.woof.api.productCeo.model.dto.ProductCeoListRes;
import com.woof.api.productCeo.model.dto.ProductCeoReadRes;
import com.woof.api.productCeo.model.dto.ProductCeoReadRes2;
import com.woof.api.productCeo.model.dto.ProductCeoUpdateReq;
import com.woof.api.productCeo.repository.ProductCeoImageRepository;
import com.woof.api.productCeo.repository.ProductCeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class ProductCeoService {
    private final ProductCeoRepository productCeoRepository;
    private final ProductCeoImageRepository productCeoImageRepository;
    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public ProductCeo createCeo(
//            Member member,
                             ProductCeoCreateReq productCeoCreateReq) {

        return productCeoRepository.save(ProductCeo.builder()
                .storeName(productCeoCreateReq.getStoreName())
//                .brandIdx(member)
                .productName(productCeoCreateReq.getProductName())
                .phoneNumber(productCeoCreateReq.getPhoneNumber())
                .price(productCeoCreateReq.getPrice())
                .contents(productCeoCreateReq.getContents())
                .build());
    }

    public ProductCeoListRes listCeo() {
        List<ProductCeo> result = productCeoRepository.findAll();

        List<ProductCeoReadRes> productCeoReadResList = new ArrayList<>();

        for (ProductCeo productCeo : result) {
            List<ProductCeoImage> productCeoImages = productCeo.getProductCeoImages();

            String filenames = "";
            for (ProductCeoImage productCeoImage : productCeoImages) {
                String filename = productCeoImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            ProductCeoReadRes productCeoReadRes = ProductCeoReadRes.builder()
                    .idx(productCeo.getIdx())
                    .productName(productCeo.getProductName())
                    .storeName(productCeo.getStoreName())
                    .phoneNumber(productCeo.getPhoneNumber())
                    .price(productCeo.getPrice())
                    .contents(productCeo.getContents())
//                    .brandIdx(productManager.getBrandIdx().getIdx())
                    .filename(filenames)
                    .build();

            productCeoReadResList.add(productCeoReadRes);
        }


        return ProductCeoListRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(productCeoReadResList)
                .build();
    }

    public ProductCeoReadRes2 readCeo(Long idx) {
        Optional<ProductCeo> resultCeo = productCeoRepository.findById(idx);

        if (resultCeo.isPresent()) {
            ProductCeo productCeo = resultCeo.get();

            List<ProductCeoImage> productCeoImages = productCeo.getProductCeoImages();

            String filenames = "";
            for (ProductCeoImage productCeoImage : productCeoImages) {
                String filename = productCeoImage.getFilename();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            ProductCeoReadRes productCeoReadRes = ProductCeoReadRes.builder()
                    .idx(productCeo.getIdx())
                    .productName(productCeo.getStoreName())
                    .storeName(productCeo.getStoreName())
                    .phoneNumber(productCeo.getPhoneNumber())
                    .price(productCeo.getPrice())
                    .contents(productCeo.getContents())
//                    .brandIdx(productManager.getBrandIdx().getIdx())
                    .filename(filenames)
                    .build();

            return ProductCeoReadRes2.builder()
                    .code(1000)
                    .message("요청 성공.")
                    .success(true)
                    .isSuccess(true)
                    .result(productCeoReadRes)
                    .build();
        }

        return null;


    }

    public void updateCeo(ProductCeoUpdateReq productCeoUpdateReq) {
        Optional<ProductCeo> result = productCeoRepository.findById(productCeoUpdateReq.getIdx());
        if (result.isPresent()) {
            ProductCeo productCeo = result.get();
            productCeo.setStoreName(productCeoUpdateReq.getStoreName());
            productCeo.setProductName(productCeoUpdateReq.getProductName());
            productCeo.setPrice(productCeoUpdateReq.getPrice());
            productCeo.setContents(productCeoUpdateReq.getContents());

            productCeoRepository.save(productCeo);
        }
    }

    public void deleteCeo(Long idx) {
        productCeoRepository.delete(ProductCeo.builder().idx(idx).build());
    }


    public String makeFolderCeo() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        return folderPath;
    }


    public String uploadFileCeo(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String folderPath = makeFolderCeo();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = folderPath + File.separator + uuid + "_" + originalName;
//        File saveFile = new File(uploadPath, saveFileName);
        InputStream input = null;
        try {
//            file.transferTo(saveFile);
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

    public void saveFileCeo(Long idx, String uploadPath) {
        productCeoImageRepository.save(ProductCeoImage.builder()
                .productCeo(ProductCeo.builder().idx(idx).build())
                .filename(uploadPath)
                .build());
    }

}
