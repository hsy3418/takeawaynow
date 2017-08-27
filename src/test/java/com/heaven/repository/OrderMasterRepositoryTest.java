package com.heaven.repository;

import com.heaven.dataobject.OrderMaster;
import com.heaven.enums.OrderStatusEnum;
import com.heaven.enums.PayStatusEnum;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("111113");
        orderMaster.setBuyerName("Heaven");
        orderMaster.setBuyerPhone("0416817115");
        orderMaster.setBuyerAddress("10 vistion street");
        orderMaster.setBuyerOpenid("abc123");
        orderMaster.setOrderAmount(new BigDecimal(10.0));
        orderMaster.setOrderStatus(OrderStatusEnum.newOrder.getStatusCode());
        orderMaster.setPayStatus(PayStatusEnum.unpaid.getStatusCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne(){
        OrderMaster orderMaster = orderMasterRepository.findOne("111111");
        Assert.assertNotNull(orderMaster);
    }

    @Test
    public void findByBuyerOpenid() throws Exception{
        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid("abc123",request);
        Assert.assertNotNull(result);
    }
}