package com.heaven.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heaven.dataobject.OrderDetail;
import com.heaven.dto.OrderDTO;
import com.heaven.enums.ResultEnum;
import com.heaven.exception.SellException;
import com.heaven.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import javax.security.sasl.SaslException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siyuanhu on 27/8/17.
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){


        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
           orderDetails =  gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.error("对象转换错误，string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

}
