package com.heaven.repository;

import com.heaven.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by siyuanhu on 19/8/17.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    //find by cateogry type
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
