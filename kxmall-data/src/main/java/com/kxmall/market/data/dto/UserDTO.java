package com.kxmall.market.data.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户对象
 *
 * @author admin
 * @date 2019/7/1
 */
@Data
public class UserDTO extends SuperDTO {

    private String phone;

    private Integer loginType;

    private String openId;

    private String sessionKey;

    private String nickname;

    private String avatarUrl;

    private Integer level;

    private Date birthday;

    private Integer gender;

    private Date gmtLastLogin;

    private String lastLoginIp;

    private Integer status;

    /**
     * 登录成功，包装此参数
     */
    private String accessToken;

    /**
     * 是否是老用户
     */
    private Boolean oldMan;

}
