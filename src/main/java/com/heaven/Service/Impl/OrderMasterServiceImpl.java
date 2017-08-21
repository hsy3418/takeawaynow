package com.heaven.Service.Impl;

import com.heaven.Service.OrderMasterService;
import com.heaven.Service.ProductInfoService;
import com.heaven.dataobject.OrderDetail;
import com.heaven.dataobject.OrderMaster;
import com.heaven.dataobject.ProductInfo;
import com.heaven.dto.CartDTO;
import com.heaven.dto.OrderDTO;
import com.heaven.enums.ResultEnum;
import com.heaven.exception.SellException;
import com.heaven.repository.OrderDetailRepository;
import com.heaven.repository.OrderMasterRepository;
import com.heaven.repository.ProductCategoryRepository;
import com.heaven.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public Page<OrderDTO> findByBuyerOpenid(String buyerOpenid, Pageable pageable) {
      Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
      return null;
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
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
