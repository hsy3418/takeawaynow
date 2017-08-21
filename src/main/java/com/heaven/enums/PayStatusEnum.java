package com.heaven.enums;

import lombok.Getter;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Getter
public enum PayStatusEnum {

    unpaid(0,"unpaid"),
    paid(1,"paid");

    private Integer statusCode;

    private String statusMessage;

    PayStatusEnum(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
