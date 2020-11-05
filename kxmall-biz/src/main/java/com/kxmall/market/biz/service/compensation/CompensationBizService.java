package com.kxmall.market.biz.service.compensation;

import com.alibaba.fastjson.JSONObject;
import com.kxmall.market.core.Const;
import com.kxmall.market.data.domain.AbnormalRoutingDO;
import com.kxmall.market.data.enums.AbnormalRoutingContinueType;
import com.kxmall.market.data.enums.AbnormalRoutingStatusType;
import com.kxmall.market.data.mapper.AbnormalRoutingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * @description: 异常消息路由补偿实现
 * @author: fy
 * @date: 2020/03/11 17:16
 **/
@Service
public class CompensationBizService {

    @Autowired
    private AbnormalRoutingMapper abnormalRoutingMapper;

    public void compensationOperate(AbnormalRoutingDO abnormalRoutingDO) {

        if (abnormalRoutingDO != null) {
            try {
                String fullClassName = abnormalRoutingDO.getFullClassName();
                String menthodName = abnormalRoutingDO.getMethodName();
                String argsClassType = abnormalRoutingDO.getArgsClassType();
                String argsParamsValue = abnormalRoutingDO.getArgsParamsValue();
                String[] argsParamsArray = null;
                String[] argsClassTypeArray = null;
                Object[] execParmasArgsArray = null;
                Class<?>[] execArgsClzssArray = null;
                if (!StringUtils.isEmpty(argsClassType)) {
                    argsClassTypeArray = argsClassType.split(",");
                }
                if (!StringUtils.isEmpty(argsParamsValue)) {
                    argsParamsArray = argsParamsValue.replace("\"", "").split(",");
                }
                if (argsClassTypeArray != null && argsClassTypeArray.length > 0) {
                    execParmasArgsArray = new Object[argsClassTypeArray.length];
                    execArgsClzssArray = new Class[argsClassTypeArray.length];
                    // 构建入参参数
                    buildArgsClassesAndParams(argsParamsArray, argsClassTypeArray, execParmasArgsArray, execArgsClzssArray);
                }
                // 反射调用目标方法
                Class clz = Class.forName(fullClassName);
                Method method = clz.getMethod(menthodName, execArgsClzssArray);
                method.setAccessible(true);
                method.invoke(clz.newInstance(), execParmasArgsArray);
                // 更新错误路由信息
                Integer execNum = abnormalRoutingDO.getExecNum();
                abnormalRoutingDO.setStatus(AbnormalRoutingStatusType.NON_EXECUTION.getCode());
                abnormalRoutingDO.setExecNum((execNum == null) ? 1 : ++execNum);
                abnormalRoutingDO.setGmtUpdate(new Date());
                abnormalRoutingMapper.updateById(abnormalRoutingDO);
            } catch (Exception ex) {
                // 异常情况判断是否要再次执行
                Integer execNum = abnormalRoutingDO.getExecNum();
                if (AbnormalRoutingContinueType.NO_CONTINUE.getCode() == abnormalRoutingDO.getErrorContinue()) {
                    abnormalRoutingDO.setStatus(AbnormalRoutingStatusType.NON_EXECUTION.getCode());
                }
                abnormalRoutingDO.setExecNum((execNum == null) ? 1 : ++execNum);
                abnormalRoutingDO.setGmtUpdate(new Date());
                abnormalRoutingMapper.updateById(abnormalRoutingDO);
            }
        }
    }

    private static void buildArgsClassesAndParams(String[] paramArray, String[] argsClassTypeArray,
                                                  Object[] parmasArgs, Class<?>[] execArgsClzssArray) throws Exception {
        boolean paramsIsNotNullFlag = (paramArray != null && paramArray.length > 0);
        for (int i = 0; i < argsClassTypeArray.length; i++) {
            Class<?> type = Class.forName(argsClassTypeArray[i]);
            execArgsClzssArray[i] = type;
            if (paramsIsNotNullFlag && !StringUtils.isEmpty(paramArray[i])) {
                // 判断是否为空，空值
                if (judgeParamValue(paramArray[i])) {
                    parmasArgs[i] = null;
                    continue;
                }
                if (String.class == type) {
                    parmasArgs[i] = buildParamValue(paramArray[i]);
                } else if (Const.IGNORE_PARAM_LIST.contains(type)) {
                    // 基本类型
                    Constructor<?> constructor = type.getConstructor(String.class);
                    parmasArgs[i] = constructor.newInstance(paramArray[i]);
                } else if (type.isArray()) {
                    //若是数组
                    Class<?> itemType = type.getComponentType();
                    Object realType[] = (Object[]) Array.newInstance(itemType, paramArray.length);
                    for (int j = 0; j < paramArray.length; j++) {
                        if (Const.IGNORE_PARAM_LIST.contains(itemType)) {
                            Constructor<?> constructor = itemType.getConstructor(String.class);
                            realType[j] = constructor.newInstance(paramArray[j]);
                        } else {
                            realType[j] = JSONObject.parseObject(paramArray[j], itemType);
                        }
                    }
                    parmasArgs[i] = realType;

                } else {
                    //Json解析
                    parmasArgs[i] = JSONObject.parseObject(paramArray[0], type);
                }
            } else {
                parmasArgs[i] = null;
            }
        }
    }

    public static String buildParamValue(String str) {
        if (!judgeParamValue(str)) {
            return str;
        }
        return null;
    }

    public static boolean judgeParamValue(String str) {
        return (StringUtils.isEmpty(str) || "null".equals(str));
    }
}
