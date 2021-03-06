package com.heaven.enums;

import lombok.Getter;

/**
 * Created by siyuanhu on 20/8/17.
 */
@Getter
public enum ResultEnum  {

    PRODUCT_NOT_EXIST(10,"product not exists"),

    PRODUCT_STOCK_ERROR(11,"stock is not correct"),

    ORDER_NOT_EXIST(12,"order not existed"),

    ORDERDETAIL_NOT_EXIST(13,"order detail not exist"),

    ORDER_STATUS_ERROR(14,"the status is not correct"),

    ORDER_UPDATE_FAIL(15,"fail to update order"),
    ORDER_DETAIL_EMPTY(16,"there is no order detail"),


    PARAM_ERROR(1,"parameters not corret"),

    CART_EMPTY(18,"the cart is empty"),

    PAY_STATUS_ERROR(17,"there is error in pay status");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
