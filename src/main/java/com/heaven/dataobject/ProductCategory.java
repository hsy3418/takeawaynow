package com.heaven.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by siyuanhu on 19/8/17.
 * product_category
 */

@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /** category id.*/
    @Id
    @GeneratedValue
    private Integer categoryId;
    /** category name.*/
    private String categoryName;

    private Integer categoryType;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public ProductCategory() {

    }
}
