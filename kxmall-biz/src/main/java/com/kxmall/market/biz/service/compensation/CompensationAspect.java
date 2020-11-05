package com.kxmall.market.biz.service.compensation;

import com.kxmall.market.data.domain.AbnormalRoutingDO;
import com.kxmall.market.data.enums.AbnormalRoutingContinueType;
import com.kxmall.market.data.enums.AbnormalRoutingStatusType;
import com.kxmall.market.data.mapper.AbnormalRoutingMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/11 17:42
 **/
@Aspect
@Component
@SuppressWarnings("all")
public class CompensationAspect {

    @Autowired
    private AbnormalRoutingMapper abnormalRoutingMapper;

    @Pointcut("@annotation(com.kxmall.market.biz.service.compensation.Compensation)")
    public void compensationPoint() {
    }

    @AfterThrowing(pointcut = "compensationPoint()", throwing = "e")
    public void recordErrorHandle(JoinPoint point, Exception e) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 Compensation 注解
        Compensation compensationAnn = AnnotationUtils.findAnnotation(method, Compensation.class);
        if (compensationAnn != null && compensationAnn.require()) {
            AbnormalRoutingDO abnormalRoutingDO = new AbnormalRoutingDO();
            abnormalRoutingDO.setModuleName(compensationAnn.moduleName());
            abnormalRoutingDO.setStatus(AbnormalRoutingStatusType.IMPLEMENT.getCode());
            abnormalRoutingDO.setErrorMsg(e.getMessage());
            Date date = new Date();
            abnormalRoutingDO.setGmtCreate(date);
            abnormalRoutingDO.setGmtUpdate(date);
            if (compensationAnn.errorContinue()) {
                abnormalRoutingDO.setErrorContinue(AbnormalRoutingContinueType.CONTINUE.getCode());
            } else {
                abnormalRoutingDO.setErrorContinue(AbnormalRoutingContinueType.NO_CONTINUE.getCode());
            }
            Class<?> declaringClass = signature.getMethod().getDeclaringClass();
            // 目标全类名 与执行方法名称
            abnormalRoutingDO.setFullClassName(declaringClass.getName());
            abnormalRoutingDO.setMethodName(method.getName());
            // 构建拼接形参类型字符串
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes != null && parameterTypes.length > 0) {
                int length = parameterTypes.length;
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(parameterTypes[i]);
                    if (i != (length - 1)) {
                        stringBuilder.append(",");
                    }
                }
                abnormalRoutingDO.setArgsClassType(stringBuilder.toString());
            }
            // 构建拼接形参的入参值字符串
            Object[] args = point.getArgs();
            if (args != null && args.length > 0) {
                int length = args.length;
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(buildParamValue(args[i]));
                    if (i != (length - 1)) {
                        stringBuilder.append(",");
                    }
                }
                abnormalRoutingDO.setArgsParamsValue(stringBuilder.toString());
            }
            abnormalRoutingMapper.insert(abnormalRoutingDO);
        }
    }

    public String buildParamValue(Object object) {
        if (object != null) {
            return String.valueOf(object);
        }
        return null;
    }

}
