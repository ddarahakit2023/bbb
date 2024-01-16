package com.woof.api.productManager.controller;

import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.model.dto.ProductManagerCreateReq;
import com.woof.api.productManager.model.dto.ProductManagerCreateRes;
import com.woof.api.productManager.model.dto.ProductManagerCreateResult;
import com.woof.api.productManager.model.dto.ProductManagerUpdateReq;
import com.woof.api.productManager.service.ProductManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productManager")
@CrossOrigin("*")
public class ProductManagerController {
    ProductManagerService productManagerService;

    public ProductManagerController(ProductManagerService productManagerService) {
        this.productManagerService = productManagerService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createManager")
    public ResponseEntity createManager(@RequestPart ProductManagerCreateReq postProductReq,
                                        @RequestPart MultipartFile[] uploadFiles) {
        ProductManager productManager = productManagerService.createManager(postProductReq);

        for (MultipartFile uploadFile:uploadFiles) {
            String uploadPath = productManagerService.uploadFileManager(uploadFile);
            productManagerService.saveFileManager(productManager.getIdx(), uploadPath);
        }

        ProductManagerCreateRes response = ProductManagerCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(ProductManagerCreateResult.builder().idx(productManager.getIdx()).build())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listManager")
    public ResponseEntity listManager() {

        return ResponseEntity.ok().body(productManagerService.listManager());
    }
    @GetMapping("/{idx}")
    public ResponseEntity readManager(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productManagerService.readManager(idx));

    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updateManager")
    public ResponseEntity updateManager(@RequestBody ProductManagerUpdateReq productManagerUpdateReq) {
        productManagerService.updateManager(productManagerUpdateReq);

        return ResponseEntity.ok().body("수정");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteManager")
    public ResponseEntity deleteManager(Long idx) {
        productManagerService.deleteManager(idx);
        return ResponseEntity.ok().body("삭제");

    }
}
