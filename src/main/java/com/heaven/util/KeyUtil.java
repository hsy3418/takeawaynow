package com.heaven.util;

import java.util.Random;

/**
 * Created by siyuanhu on 20/8/17.
 */
public class KeyUtil {

    /**generate  unique primary key
     * time + randam number
     * @return
     */
    public static synchronized String genUniqueKey(){

        Random random = new Random();



       Integer number =  random.nextInt(900000)+100000;

       return  System.currentTimeMillis()+String.valueOf(number);
    }
}
