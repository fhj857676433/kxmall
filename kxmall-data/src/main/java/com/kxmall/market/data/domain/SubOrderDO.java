package com.kxmall.market.data.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SubOrderDO extends OrderDO{

    private String userName;

    private List<Integer> staList = new ArrayList<>();

    private Date gmtStart;

    private Date gmtEnd;

}
