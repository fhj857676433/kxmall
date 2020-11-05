package com.kxmall.market.admin.api.api.activity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.ActivityCounponDO;
import com.kxmall.market.data.domain.ActivityDO;
import com.kxmall.market.data.dto.ActivityDTO;
import com.kxmall.market.data.dto.EnumsDTO;
import com.kxmall.market.data.enums.ActivityType;
import com.kxmall.market.data.mapper.ActivityCouponMapper;
import com.kxmall.market.data.mapper.ActivityMapper;
import com.kxmall.market.data.model.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 活动管理服务层
 *
 * @author kaixin
 * @date 2020/03/01
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    /**
     * 活动缓存
     */
    private static final String CA_ACTIVITY_PREFIX = "CA_ACTIVITY_";
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private CacheComponent cacheComponent;
    @Resource
    private ActivityCouponMapper activityCouponMapper;

    /**
     * 创建活动管理
     *
     * @param activityDTO 创建活动对象
     * @param adminId     管理员id
     * @return ok
     * @throws ServiceException 统一捕获错误
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ActivityDTO activityDTO, Long adminId) throws ServiceException {

        //参数校验
        if (CollectionUtils.isEmpty(activityDTO.getActivityCounponDO())) {
            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_COUPON_LIST_EMPTY);
        }
        if (activityDTO.getId() != null) {
            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_CREATE_HAS_ID);
        }
        if (activityDTO.getActivityEndTime().compareTo(activityDTO.getActivityStartTime()) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_START_MUST_LESS_THAN_END);
        }
        //查一下是否同种类型是否存在正常的
        Wrapper<ActivityDO> wrapper = new EntityWrapper<>();
        wrapper.eq("status", 1);
        wrapper.eq("activity_type", activityDTO.getActivityType());
        Integer count = activityMapper.selectCount(wrapper);
        if (count != 0) {
            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_NOT_EXIST_UNIQUE);
        }
        //主表新增
        ActivityDO activityDO = new ActivityDO();
        BeanUtils.copyProperties(activityDTO, activityDO);
        Date now = new Date();
        activityDO.setGmtUpdate(now);
        activityDO.setGmtCreate(now);
        activityDO.setUserId(adminId);
        activityMapper.insert(activityDO);
        //从表新增
        for (ActivityCounponDO activityCounponDO : activityDTO.getActivityCounponDO()) {
            activityCounponDO.setActivityId(activityDO.getId());
            activityCounponDO.setGmtUpdate(now);
            activityCounponDO.setGmtCreate(now);
            activityCouponMapper.insert(activityCounponDO);
        }
        //清除缓存
        cacheComponent.delPrefixKey(CA_ACTIVITY_PREFIX);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(ActivityDTO activityDTO, Long adminId) throws ServiceException {
        //参数校验
        if (activityDTO.getId() == null) {
            throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        if (CollectionUtils.isEmpty(activityDTO.getActivityCounponDO())) {
            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_COUPON_LIST_EMPTY);
        }
        if (activityDTO.getActivityStartTime().compareTo(activityDTO.getActivityEndTime()) >= 0) {
            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_START_MUST_LESS_THAN_END);
        }

        //主表更新
        Date now = new Date();
        ActivityDO activityDO = new ActivityDO();
        BeanUtils.copyProperties(activityDTO, activityDO);
        activityDO.setGmtUpdate(now);
        ActivityDTO dto = activityMapper.selectOneById(activityDTO.getId());
        if (2 == dto.getStatus()) {
            activityDO.setStatus(0);
        }
        if (activityDO.getActivityStartTime().after(now) || activityDO.getActivityEndTime().before(now)) {
            //活动时间不在范围内，更新为到期
            activityDO.setStatus(2);
        }
        activityMapper.updateById(activityDO);

        //从表更新
        List<Long> activityCouponId = new LinkedList<>();
        for (ActivityCounponDO activityCounponDO : activityDTO.getActivityCounponDO()) {
            activityCounponDO.setGmtUpdate(now);
            if (ObjectUtils.isEmpty(activityCounponDO.getId())) {
                activityCounponDO.setGmtCreate(now);
                activityCouponMapper.insert(activityCounponDO);
            } else {
                activityCouponMapper.updateById(activityCounponDO);
            }
            activityCouponId.add(activityCounponDO.getId());
        }
        //删除多余的优惠券
        activityCouponMapper.delete(new EntityWrapper<ActivityCounponDO>().eq("activity_id", activityDO.getId()).notIn("id", activityCouponId));
        //清除缓存
        cacheComponent.delPrefixKey(CA_ACTIVITY_PREFIX);
        return "ok";
    }

    /**
     * 查询活动 by id
     *
     * @param adminId 管理员id
     * @param id      主键id
     * @return 活动对象
     * @throws ServiceException
     */
    @Override
    public ActivityDTO queryActivityById(Long adminId, Long id) throws ServiceException {
        ActivityDTO activityDTO = new ActivityDTO();
        ActivityDO activityDO = new ActivityDO();
        activityDO.setId(id);
        activityDO = activityMapper.selectOne(activityDO);
        BeanUtils.copyProperties(activityDO, activityDTO);
        //查询从表
        EntityWrapper<ActivityCounponDO> wrapper = new EntityWrapper<>();
        wrapper.eq("activity_id", id);
        List<ActivityCounponDO> counponDoList = activityCouponMapper.selectList(wrapper);
        activityDTO.setActivityCounponDO(counponDoList);
        return activityDTO;
    }


    @Override
    public Page<ActivityDTO> list(Integer page, Integer limit, String title, Integer activityType, Integer status, Long activityStartTime, Long activityEndTime, Long adminId) throws ServiceException {
        Wrapper<ActivityDO> wrapper = new EntityWrapper<>();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!ObjectUtils.isEmpty(activityType)) {
            wrapper.eq("activity_type", activityType);
        }
        if (!ObjectUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        if (activityStartTime != null && activityEndTime != null) {
            Date timeEnd = new Date(activityEndTime);
            wrapper.ge("activity_end_time", timeEnd);
        }
        if (activityEndTime != null) {
            Date timeEnd = new Date(activityEndTime);
            wrapper.ge("activity_end_time", timeEnd);
        }

        List<ActivityDTO> activitydtolist = activityMapper.selectActivityPage(new RowBounds((page - 1) * limit, limit), title, activityType, status, activityStartTime,activityEndTime);
        Integer count = activityMapper.selectCount(wrapper);
        return new Page<>(activitydtolist, page, limit, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateStatus(Long id, Integer status, Long adminId) throws ServiceException {
        String returnStr = "ok";
        ActivityDTO selectOne = activityMapper.selectOneById(id);
        ActivityDO activityDO = new ActivityDO();
        activityDO.setId(selectOne.getId());
        Date now = new Date();
        switch (status) {
            //正常 ==》 失效
            case 0:
                if (1 == selectOne.getStatus()) {
                    //先判断是否在期限内，不在期限内直接更新为到期
                    if (selectOne.getActivityStartTime().before(now) && selectOne.getActivityEndTime().after(now)) {
                        activityDO.setStatus(0);
                    } else {
                        activityDO.setStatus(2);
                        returnStr = "激活失败，活动日期不在范围内。自动更新为失效状态！";
                    }
                    activityMapper.updateById(activityDO);
                    return returnStr;
                }
                break;
            //正常 ==》 失效
            case 1:
                if (0 == selectOne.getStatus()) {
                    //先判断是否在期限内，不在期限内直接更新为到期
                    if (selectOne.getActivityStartTime().before(now) && selectOne.getActivityEndTime().after(now)) {
                        //查一下是否同种类型是否存在正常的
                        Wrapper<ActivityDO> wrapper = new EntityWrapper<>();
                        wrapper.eq("status", 1);
                        wrapper.eq("activity_type", selectOne.getActivityType());
                        Integer count = activityMapper.selectCount(wrapper);
                        if (count != 0) {
                            throw new AdminServiceException(ExceptionDefinition.ACTIVITY_NOT_EXIST_UNIQUE);
                        }
                        activityDO.setStatus(1);
                    } else {
                        activityDO.setStatus(2);
                        returnStr = "激活失败，活动日期不在范围内。自动更新为失效状态！";
                    }
                    activityMapper.updateById(activityDO);
                    return returnStr;
                }
                break;
            //到期
            case 2:
                break;
            default: //不处理
        }
        //清除缓存
        cacheComponent.delPrefixKey(CA_ACTIVITY_PREFIX);
        return "ok";
    }

    @Override
    public List<EnumsDTO> getActivityTypeEnums() throws ServiceException {
        List<EnumsDTO> activity = new ArrayList<>();
        for (ActivityType value : ActivityType.values()) {
            EnumsDTO enumsDTO = new EnumsDTO();
            enumsDTO.setCode(value.getCode());
            enumsDTO.setMsg(value.getMsg());
            activity.add(enumsDTO);
        }
        return activity;
    }

    @Override
    public String generateLinkes(Integer activityType) throws ServiceException {

        switch (ActivityType.getBycode(activityType)) {
            case NEW_REGISTRATION:
                return "目前暂无链接生成！";
            case INVITE_NEW_PEOPLE:
                return "/pages/Invitation/Invitation";
            default:
                return "目前暂无链接生成！";
        }
    }
}
