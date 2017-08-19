package com.heaven.Service;

import com.heaven.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by siyuanhu on 19/8/17.
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
