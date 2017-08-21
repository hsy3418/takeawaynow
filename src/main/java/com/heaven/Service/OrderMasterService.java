package com.heaven.Service;

import com.heaven.dataobject.OrderMaster;
import com.heaven.dataobject.ProductCategory;
import com.heaven.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Service
public interface OrderMasterService {

    OrderDTO findOne(String orderId);

    List<OrderDTO> findAll();

    Page<OrderDTO> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    OrderDTO create(OrderDTO orderDTO);

    //cancel the order
    OrderDTO cancel(OrderDTO orderDTO);

    //finished the order
    OrderDTO finish(OrderDTO orderDTO);

    //pay for the order
    OrderDTO paid(OrderDTO orderDTO);
}
