package com.heaven.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by siyuanhu on 27/8/17.
 */
@Data
public class OrderForm {

    /**
     * buyer name
     */
    @NotEmpty(message = "name is requried")
    private String name;


    @NotEmpty(message = "the phone number is requried")
    private String phone;

    @NotEmpty(message = "the address is requried")
    private String address;

    @NotEmpty(message = "openid required")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;




}
