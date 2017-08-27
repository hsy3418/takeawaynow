package com.heaven.Service.Impl;

import com.heaven.Service.OrderMasterService;
import com.heaven.Service.ProductInfoService;
import com.heaven.converter.OrderMasterToOrderDTO;
import com.heaven.dataobject.OrderDetail;
import com.heaven.dataobject.OrderMaster;
import com.heaven.dataobject.ProductInfo;
import com.heaven.dto.CartDTO;
import com.heaven.dto.OrderDTO;
import com.heaven.enums.OrderStatusEnum;
import com.heaven.enums.PayStatusEnum;
import com.heaven.enums.ResultEnum;
import com.heaven.exception.SellException;
import com.heaven.repository.OrderDetailRepository;
import com.heaven.repository.OrderMasterRepository;
import com.heaven.repository.ProductCategoryRepository;
import com.heaven.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    OrderDetailRepository orderDetailRepository;




    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO  orderDTO = new OrderDTO();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(orderDetailList.size()==0){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAll() {
       List<OrderMaster> orderMasters = orderMasterRepository.findAll();
       List<OrderDTO> orderDTOS = new ArrayList<>();
       if(orderMasters.size()==0){
           throw new SellException(ResultEnum.ORDER_NOT_EXIST);

       }else{
           for(OrderMaster orderMaster:orderMasters){
             OrderDTO orderDTO = new OrderDTO();
             BeanUtils.copyProperties(orderMaster,orderDTO);
             List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
             orderDTO.setOrderDetailList(orderDetails);
             orderDTOS.add(orderDTO);
           }
       }
       return orderDTOS;

    }

    @Override
    public Page<OrderDTO> findByBuyerOpenid(String buyerOpenid, Pageable pageable) {
      Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
       List<OrderDTO> OrderDTOList = OrderMasterToOrderDTO.convert(orderMasters.getContent());
        return new PageImpl<OrderDTO>(OrderDTOList,pageable,orderMasters.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.search product(quantity, price)
        for(OrderDetail order:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(order.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.calculate the total price
           orderAmount= order.getProductPrice().
                    multiply(new BigDecimal(order.getProductQuantity())).add(orderAmount);

            //3.put into database(orderMaster orderDetail)
            order.setDetailId(KeyUtil.genUniqueKey());
            order.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,order);
            orderDetailRepository.save(order);




        }


        //create order master
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);



        //4. if success, remove the stock,

        List<CartDTO> cartDTOList = new ArrayList<>();
        orderDTO.getOrderDetailList().stream().map(e->
                new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态，如果已经接单 就不能取消
         if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.newOrder.getStatusCode())){
             log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={},",orderDTO.getOrderId());
             throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
         }


        //修改订单状态

        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL_ORDER.getStatusCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){

            log.error("[取消订单]更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[cancel order] no products,orderDTO={}",orderDTO);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map( e -> new CartDTO(e.getProductId(),e.getProductQuantity())

        ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果已经支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.paid.getStatusCode())){
            //TODO
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {


        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.newOrder.getStatusCode())){
            log.error("[finish order] not corret ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }

        if(!orderDTO.getPayStatus().equals(PayStatusEnum.wait.getStatusCode())){
            log.error("[finish order] not corret ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //change order status
        OrderMaster orderMaster = new OrderMaster();


        orderDTO.setOrderStatus(OrderStatusEnum.oldOrder.getStatusCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
       OrderMaster updateResult =  orderMasterRepository.save(orderMaster);
       if(updateResult==null){
           log.error("[finished order] failed orderMaster={}",orderMaster);
           throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
       }
        return orderDTO;

    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.oldOrder.getStatusCode())){
            log.error("[finish order] not corret ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }

        if(!orderDTO.getPayStatus().equals(PayStatusEnum.wait.getStatusCode())){
            log.error("[finish order] not corret ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //change order status
        OrderMaster orderMaster = new OrderMaster();

        orderDTO.setPayStatus(PayStatusEnum.paid.getStatusCode());
        //change pay status

        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult =  orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("[finished order] failed orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;

    }
}
