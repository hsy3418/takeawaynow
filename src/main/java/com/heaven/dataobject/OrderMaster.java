package com.heaven.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Entity
@Data
public class OrderMaster {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;


}
