package com.heaven.exception;

import com.heaven.enums.ResultEnum;
import org.junit.runner.RunWith;

/**
 * Created by siyuanhu on 20/8/17.
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(ResultEnum resultEnum,String message) {
        super(message);
        this.code = resultEnum.getCode();
    }
}
