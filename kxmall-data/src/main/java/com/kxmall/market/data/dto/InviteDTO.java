package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.SuperDO;
import com.kxmall.market.data.domain.UserDO;
import lombok.Data;

/**
 * 邀请列表
 *
 * @author kaixin
 */
@Data
public class InviteDTO extends SuperDO {

    @DtoDescription(description = "邀请人id")
    private Long inviteId;

    @DtoDescription(description = "被邀请人id")
    private Long beinviteId;

    @DtoDescription(description = "被邀请人ip地址")
    private String beinviteIp;

    @DtoDescription(description = "表示被邀请人是否正常0不正常1正常")
    private Integer status;

    @DtoDescription(description = "用户对象")
    private UserDO userDO;

}
