package com.heaven.Service.Impl;

import com.heaven.dataobject.ProductInfo;
import com.heaven.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0,2);
         Page<ProductInfo> result = productInfoService.findAll(request);
         Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void findupAll() throws Exception {
        List<ProductInfo> result = productInfoService.findupAll();
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("cake");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("very good");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
        productInfo.setCategoryType(3);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }

}