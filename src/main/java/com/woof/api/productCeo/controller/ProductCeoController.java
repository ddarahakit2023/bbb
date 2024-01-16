package com.woof.api.productCeo.controller;

//import com.woof.api.member.model.Member;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.dto.ProductCeoCreateReq;
import com.woof.api.productCeo.model.dto.ProductCeoCreateRes;
import com.woof.api.productCeo.model.dto.ProductCeoCreateResult;
import com.woof.api.productCeo.model.dto.ProductCeoUpdateReq;
import com.woof.api.productCeo.service.ProductCeoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productCeo")
@CrossOrigin("*")
public class ProductCeoController {
    ProductCeoService productCeoService;

    public ProductCeoController(ProductCeoService productCeoService) {
        this.productCeoService = productCeoService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCeo")
    public ResponseEntity createCeo(
//            @AuthenticationPrincipal Member member,
                                 @RequestPart ProductCeoCreateReq postProductReq,
                                 @RequestPart MultipartFile[] uploadFiles) {
        ProductCeo productCeo = productCeoService.createCeo(
//                member,
                postProductReq);

        for (MultipartFile uploadFile:uploadFiles) {
            String uploadPath = productCeoService.uploadFileCeo(uploadFile);
            productCeoService.saveFileCeo(productCeo.getIdx(), uploadPath);
        }

        ProductCeoCreateRes response = ProductCeoCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(ProductCeoCreateResult.builder().idx(productCeo.getIdx()).build())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listCeo")
    public ResponseEntity listCeo() {

        return ResponseEntity.ok().body(productCeoService.listCeo());
    }
    @GetMapping("/{idx}")
    public ResponseEntity readCeo(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productCeoService.readCeo(idx));

    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updateCeo")
    public ResponseEntity updateCeo(ProductCeoUpdateReq productCeoUpdateReq) {
        productCeoService.updateCeo(productCeoUpdateReq);

        return ResponseEntity.ok().body("수정");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCeo")
    public ResponseEntity deleteCeo(Long idx) {
        productCeoService.deleteCeo(idx);
        return ResponseEntity.ok().body("삭제");

    }
}
