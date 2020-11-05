package com.kxmall.market.data.dto.rider;


import com.kxmall.market.data.dto.SuperDTO;
import lombok.Data;

/**
 * @description: 骑手登录成功DTO
 * @author: fy
 * @date: 2020/02/29 18:54
 **/
@Data
public class RiderLoginDTO extends SuperDTO {

    /**
     * 配送员名称
     */
    private String name;

    /**
     * 仓库主键ID
     */
    private Long storageId;

    /**
     * 开始配送时间
     */
    private String deliveryStart;

    /**
     * 结束配送时间
     */
    private String deliveryEnd;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 配送员状态
     */
    private Integer state;

    /**
     * 开工状态
     */
    private Integer workState;


    /**
     * 登录成功，包装此参数
     */
    private String accessToken;
}
