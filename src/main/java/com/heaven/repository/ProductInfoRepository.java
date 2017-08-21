package com.heaven.repository;

import com.heaven.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by siyuanhu on 19/8/17.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /**find by status */

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
