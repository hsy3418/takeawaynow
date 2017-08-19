package com.heaven.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * the product that includes the category
 * Created by siyuanhu on 19/8/17.
 */
@Data
public class ProductCategoryVO {
    /**
     * the name of the category
     */
    @JsonProperty("name")
    private String categoryName;

    /**
     * the product type
     */
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
