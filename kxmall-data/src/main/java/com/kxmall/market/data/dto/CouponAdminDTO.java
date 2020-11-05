package com.kxmall.market.data.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户对象
 * <p>
 *
 * Description:
 * User: admin
 * Date: 2019-07-13
 * Time: 下午10:24
 *
 * @author kaixin
 */
@Data
public class CouponAdminDTO extends SuperDTO {

    private String title;

    private Integer type;

    private String description;

    private Integer total;

    private Integer surplus;

    private Integer limit;

    private Integer discount;

    private Integer min;

    private Integer status;

    private String categoryTitle;

    private Long categoryId;

    private Integer days;

    private Date gmtStart;

    private Date gmtEnd;



}
