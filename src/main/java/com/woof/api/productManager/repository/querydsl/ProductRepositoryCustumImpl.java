//package com.example.demo.productManager.repository.querydsl;
//
//import com.example.demo.member.model.QMember;
//import com.example.demo.productManager.model.ProductManager;
//import com.example.demo.productManager.model.QProductCeo;
//import com.example.demo.productManager.model.QProductCeoImage;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ProductRepositoryCustumImpl extends QuerydslRepositorySupport implements ProductRepositoryCustum{
//
//    public ProductRepositoryCustumImpl() {
//        super(ProductManager.class);
//    }
//
//    @Override
//    public List<ProductManager> findList(){
//        QProductCeo productManager = new QProductCeo("productManager");
//        QProductCeoImage productCeoImage = new QProductCeoImage("productImage");
//        QMember member = new QMember("member");
//
//        List<ProductManager> result = from(productManager)
//                .leftJoin(productManager.productManagerImages, productCeoImage).fetchJoin()
//                .leftJoin(productManager.brandIdx, member).fetchJoin()
//                .fetch().stream().distinct().collect(Collectors.toList());
//
//        return result;
//    }
//}
