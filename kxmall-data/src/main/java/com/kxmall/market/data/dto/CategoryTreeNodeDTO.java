package com.kxmall.market.data.dto;

import lombok.Data;

import java.util.List;

/**
 * 为ElementUi适配的树结构
 * Created by admin on 2019/7/12.
 */
@Data
public class CategoryTreeNodeDTO {

    private String label;

    private Long value;

    private String fullName;

    private Long parent;

    private Integer level;

    private String iconUrl;

    private String picUrl;

    private List<CategoryTreeNodeDTO> children;

}
