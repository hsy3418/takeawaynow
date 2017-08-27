package com.heaven.Service.Impl;

import com.heaven.dataobject.OrderDetail;
import com.heaven.dataobject.OrderMaster;
import com.heaven.dto.OrderDTO;
import com.heaven.enums.OrderStatusEnum;
import com.heaven.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by siyuanhu on 19/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    OrderMasterServiceImpl orderMasterService;

    @Test
    public void findOne() throws Exception {
        OrderDTO result = orderMasterService.findOne("111111");
        log.info("[订单]result = {}",result);
        Assert.assertEquals("111111",result.getOrderId());
    }

    @Test
    public void findAll() throws Exception {
        Assert.assertNotEquals(0,orderMasterService.findAll().size());
    }

    @Test
    public void findByOpenId() throws Exception {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderDTO> result = orderMasterService.findByBuyerOpenid("abc123",pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());

     }

    @Test
    public void save() throws Exception {
        OrderDTO orderMaster = new OrderDTO();
        orderMaster.setOrderId("111113");
        orderMaster.setBuyerName("Heaven");
        orderMaster.setBuyerPhone("0416817115");
        orderMaster.setBuyerAddress("10 vistion street");
        orderMaster.setBuyerOpenid("abc123");
        ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234533");
        orderDetail.setOrderId("111111");
        orderDetail.setProductId("1111112");
        orderDetail.setProductName("congee");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://xxx");
        orderDetailArrayList.add(orderDetail);
        orderMaster.setOrderDetailList(orderDetailArrayList);
        orderMaster.setOrderAmount(new BigDecimal(10.0));
        orderMaster.setOrderStatus(OrderStatusEnum.newOrder.getStatusCode());
        orderMaster.setPayStatus(PayStatusEnum.unpaid.getStatusCode());
        OrderDTO result = orderMasterService.create(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void cancel() throws Exception{
        OrderDTO orderDTO = orderMasterService.findOne("111111");
        OrderDTO result = orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL_ORDER.getStatusCode(),result.getOrderStatus());

    }

    @Test
    public void finish() throws Exception{
        OrderDTO orderDTO = orderMasterService.findOne("111111");
        OrderDTO result = orderMasterService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.oldOrder.getStatusCode(),result.getOrderStatus());

    }

    @Test
    public void paid() throws Exception{
        OrderDTO orderDTO = orderMasterService.findOne("111111");
        OrderDTO result = orderMasterService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.paid.getStatusCode(),result.getPayStatus());


    }
}