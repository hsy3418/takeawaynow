package com.heaven.enums;

import lombok.Getter;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Getter
public enum OrderStatusEnum {

    newOrder(0,"new order"),
    oldOrder(1,"old order"),
    CANCEL_ORDER(2,"cancel order");

    private Integer statusCode;

    private String statusMessage;

    OrderStatusEnum(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
