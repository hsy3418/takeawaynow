package com.heaven.dto;

import lombok.Data;

/**
 * Created by siyuanhu on 20/8/17.
 */
@Data
public class CartDTO{

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
