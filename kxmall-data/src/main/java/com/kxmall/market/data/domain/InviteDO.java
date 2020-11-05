package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

@Data
@TableName("kxmall_invite")
public class InviteDO extends SuperDO {

    @DtoDescription(description = "邀请人id")
    @TableField("invite_id")
    private Long inviteId;

    @DtoDescription(description = "被邀请人id")
    @TableField("beinvite_id")
    private Long beinviteId;

    @DtoDescription(description = "被邀请人ip地址")
    @TableField("beinvite_ip")
    private String beinviteIp;

    @DtoDescription(description = "表示被邀请人是否正常0不正常1正常")
    private Integer status;

}
