package com.heaven.Service.Impl;

import com.heaven.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());


    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> result = categoryService.findAll();
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(Arrays.asList(2));
        Assert.assertNotEquals(0,1);
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("main",5);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);

    }

}