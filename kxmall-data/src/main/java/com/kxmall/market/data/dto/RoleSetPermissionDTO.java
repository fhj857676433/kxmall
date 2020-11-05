package com.kxmall.market.data.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by admin on 2019/4/12.
 * 角色授权上报数据DTO
 */
@Data
public class RoleSetPermissionDTO {

    private Long roleId;

    private List<String> permissions;

}
