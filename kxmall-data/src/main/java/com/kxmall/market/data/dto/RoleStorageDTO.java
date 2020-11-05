package com.kxmall.market.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/08 14:25
 **/
@Data
public class RoleStorageDTO implements Serializable {


    private Long roleId;

    private List<Long> storageIdList;

}
