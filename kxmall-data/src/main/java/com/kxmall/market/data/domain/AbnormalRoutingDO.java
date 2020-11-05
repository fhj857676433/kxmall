package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @description: 异常消息路由补充消息表
 * @author: fy
 * @date: 2020/03/11 17:11
 **/
@Data
@TableName("uniamall_abnormal_routing")
public class AbnormalRoutingDO extends SuperDO {

    /**模块名称*/
    @TableField("module_name")
    private String moduleName;

    /**类的全路径*/
    @TableField("full_class_name")
    private String fullClassName;

    /**执行的方法名称*/
    @TableField("method_name")
    private String methodName;

    /**形参字节码类型，多个以逗号分隔*/
    @TableField("args_class_type")
    private String argsClassType;

    /**形参方法参数值，多个以逗号分隔	*/
    @TableField("args_params_value")
    private String argsParamsValue;

    /**执行次数*/
    @TableField("exec_num")
    private Integer execNum;

    /**执行状态[0: 不执行，1：执行]*/
    @TableField("status")
    private Integer status;

    /**原始异常消息	*/
    @TableField("error_msg")
    private String errorMsg;

    /**遇错误是否继续执行[0 : 不进行 ，1：继续]*/
    @TableField("error_continue")
    private Integer errorContinue;


}
