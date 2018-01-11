package com.jeff.base.inaction;

import com.jeff.base.inaction.bean.Apple;
import org.junit.Test;

/**
 * @author jeff
 * <p>Date 2018/1/11 17:22</p>
 */
public class CommonTest {

    @Test
    public void testLombok(){
        Apple apple = new Apple();
        apple.setColor("reg");
        System.out.println(apple.getColor());
    }
}
