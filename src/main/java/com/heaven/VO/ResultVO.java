package com.heaven.VO;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * the out layer of returning api
 * Created by siyuanhu on 19/8/17.
 */
@Data
public class ResultVO<T> {

    //error code
    private Integer code;

    // error message
    private String msg;


    //the content
    private T data;
}

