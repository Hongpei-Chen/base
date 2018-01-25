package com.jeff.base.inaction.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author jeff
 * <p>Date 2018/1/24</p>
 */
@Data
@ToString
@AllArgsConstructor
public class Transaction {

    private Trader trader;
    private int year;
    private int value;

}
