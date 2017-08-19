package com.heaven.Service;

import com.heaven.dataobject.ProductCategory;
import com.heaven.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * Created by siyuanhu on 19/8/17.
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);
    /**
     * find all of the products in on shelf
     * @return
     */
    List<ProductInfo> findupAll();

    ProductInfo save(ProductInfo productInfo);

    //add Stock

    // reduct stock
}
