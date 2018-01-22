package com.jeff.base.inaction.bean;

import lombok.Data;

/**
 * @author jeff
 * <p>Date 2018/1/11 16:37</p>
 */
@Data
public class Apple {

    private String color;
    private Integer weight;

    public Apple(){}

    public Apple(Integer weight) {
        this.weight = weight;
    }

    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }
}
