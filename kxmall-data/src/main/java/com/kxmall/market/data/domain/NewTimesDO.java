package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_new_times")
public class NewTimesDO extends SuperDO{

    //仓库id
    @TableField("storage_id")
    private Integer storageId;
    //时报状态
    @TableField("is_stop")
    private Integer isStop;
    //时报内容
    private String content;
    //编辑人
    @TableField("gmt_editor")
    private String gmtEditor;

}
