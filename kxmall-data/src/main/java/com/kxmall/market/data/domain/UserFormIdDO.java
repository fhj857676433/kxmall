package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 *
 * Description: 小程序提交上来的formId，用来创建模板消息
 * User: admin
 * Date: 2019/10/29
 * Time: 23:18
 */
@Data
@TableName("kxmall_user_form_id")
public class UserFormIdDO extends SuperDO {

    @TableField("user_id")
    private Long userId;

    private String openid;

    @TableField("form_id")
    private String formId;


}
