package com.heaven.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by siyuanhu on 19/8/17.
 */
@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    /**0normal 1off*/
    private Integer productStatus;

    /** connection to productCategory **/
    private Integer categoryType;


}
