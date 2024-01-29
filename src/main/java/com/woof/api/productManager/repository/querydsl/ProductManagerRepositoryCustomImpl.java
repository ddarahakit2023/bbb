package com.woof.api.productManager.repository.querydsl;

import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.model.QProductManager;
import com.woof.api.productManager.model.QProductManagerImage;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

public class ProductManagerRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductManagerRepositoryCustom {


    public ProductManagerRepositoryCustomImpl() {
        super(ProductManager.class);
    }

    @Override
    public List<ProductManager> findList() {


        QProductManager productManager = new QProductManager("productManager");
        QProductManagerImage productManagerImage = new QProductManagerImage("productManagerImage");


        List<ProductManager> result = from(productManager)
                .leftJoin(productManager.productManagerImages, productManagerImage).fetchJoin()
                .distinct().fetch();



        return result;
    }
}
