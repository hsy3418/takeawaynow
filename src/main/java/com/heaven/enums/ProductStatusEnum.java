package com.heaven.enums;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Getter
public enum ProductStatusEnum {

    UP(0,"at stock"),
        Down(1,"not at stock");
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
