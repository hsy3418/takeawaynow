package com.heaven.controller;

import com.heaven.Service.OrderMasterService;
import com.heaven.VO.ResultVO;
import com.heaven.converter.OrderForm2OrderDTOConverter;
import com.heaven.dto.OrderDTO;
import com.heaven.enums.ResultEnum;
import com.heaven.exception.SellException;
import com.heaven.form.OrderForm;
import com.heaven.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {


    @Autowired
    private OrderMasterService orderMasterService;
    //create order

    @PostMapping("/create")
    public ResultVO<Map<String,String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[create order] parameter is not correct, orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[create the order] the cart can't be null");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderMasterService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    // view the list of order

    // cancle order

    // view the content of order


}
