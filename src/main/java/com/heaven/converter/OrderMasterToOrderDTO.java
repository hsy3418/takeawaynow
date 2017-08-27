package com.heaven.converter;

import com.heaven.dataobject.OrderMaster;
import com.heaven.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by siyuanhu on 26/8/17.
 */
public class OrderMasterToOrderDTO {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
      return   orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());

    }
}
