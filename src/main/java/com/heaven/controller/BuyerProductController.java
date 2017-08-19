package com.heaven.controller;

import com.heaven.Service.CategoryService;
import com.heaven.Service.ProductInfoService;
import com.heaven.VO.ProductInfoVO;
import com.heaven.VO.ProductCategoryVO;
import com.heaven.VO.ResultVO;
import com.heaven.dataobject.ProductCategory;
import com.heaven.dataobject.ProductInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO getProductList(){
        //1.search all of the product that is on the shelves
        List<ProductInfo> productInfoList = productInfoService.findupAll();

        //java 8 lambda, search the category type list based on the products on the shelves
        List<Integer> categoryTypeList =  productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        // search the category based on the category type list
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // the productCategoryVOList
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        //3. combine data
         for(ProductCategory productCategory:productCategoryList){
             ProductCategoryVO productCategoryVO = new ProductCategoryVO();
             productCategoryVO.setCategoryType(productCategory.getCategoryType());
             productCategoryVO.setCategoryName(productCategory.getCategoryName());
             List<ProductInfoVO> productInfoVOList = new ArrayList<>();
             for(ProductInfo productInfo:productInfoList){
                 if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                     ProductInfoVO productInfoVO =  new ProductInfoVO();
                     BeanUtils.copyProperties(productInfo,productInfoVO);
                     productInfoVOList.add(productInfoVO);
                 }
             }
             productCategoryVO.setProductInfoVOList(productInfoVOList);
             productCategoryVOList.add(productCategoryVO);
         }

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        ProductInfoVO productInfoVO = new ProductInfoVO();
        resultVO.setData(productCategoryVOList);
        return resultVO;
    }
}
