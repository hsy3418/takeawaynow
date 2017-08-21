package com.heaven.Service.Impl;

import com.heaven.dataobject.OrderMaster;
import com.heaven.enums.OrderStatusEnum;
import com.heaven.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterServiceImplTest {

    @Autowired
    OrderMasterServiceImpl orderMasterService;

    @Test
    public void findOne() throws Exception {
        Assert.assertNotNull(orderMasterService.findOne("111111"));
    }

    @Test
    public void findAll() throws Exception {
        Assert.assertNotEquals(0,orderMasterService.findAll().size());
    }

    @Test
    public void save() throws Exception {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("111112");
        orderMaster.setBuyerName("Heaven");
        orderMaster.setBuyerPhone("0416817115");
        orderMaster.setBuyerAddress("10 vistion street");
        orderMaster.setBuyerOpenid("abc123");
        orderMaster.setOrderAmount(new BigDecimal(10.0));
        orderMaster.setOrderStatus(OrderStatusEnum.newOrder.getStatusCode());
        orderMaster.setPayStatus(PayStatusEnum.unpaid.getStatusCode());
        OrderMaster result = orderMasterService.create(orderMaster);
        Assert.assertNotNull(result);
    }

}