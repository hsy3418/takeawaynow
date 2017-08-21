package com.heaven.dto;

import com.heaven.dataobject.OrderDetail;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by siyuanhu on 20/8/17.
 */
@Data
public class OrderDTO {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    List<OrderDetail> orderDetailList;
}
