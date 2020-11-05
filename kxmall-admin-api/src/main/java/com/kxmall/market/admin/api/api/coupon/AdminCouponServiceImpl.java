package com.kxmall.market.admin.api.api.coupon;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.core.exception.ServiceExceptionDefinition;
import com.kxmall.market.data.domain.ActivityCounponDO;
import com.kxmall.market.data.domain.CouponDO;
import com.kxmall.market.data.domain.CouponSkuDO;
import com.kxmall.market.data.domain.UserCouponDO;
import com.kxmall.market.data.dto.CouponDTO;
import com.kxmall.market.data.dto.UserCouponDTO;
import com.kxmall.market.data.mapper.ActivityCouponMapper;
import com.kxmall.market.data.mapper.CouponMapper;
import com.kxmall.market.data.mapper.CouponSkuMapper;
import com.kxmall.market.data.mapper.UserCouponMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-12
 * Time: 下午11:26
 *
 * @author kaixin
 */
@Service
public class AdminCouponServiceImpl implements AdminCouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private CouponSkuMapper couponSkuMapper;

    @Resource
    private ActivityCouponMapper activityCouponMapper;

    @Resource
    private UserCouponMapper userCouponMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addCoupon(Long adminId, CouponDTO couponDTO) throws ServiceException {
        if (couponDTO.getId() != null) {
            throw new AdminServiceException(ExceptionDefinition.COUPON_CREATE_HAS_ID);
        }
        CouponDO couponDO = new CouponDO();
        Date now = new Date();
        couponDTO.setGmtCreate(now);
        couponDTO.setGmtUpdate(now);
        BeanUtils.copyProperties(couponDTO, couponDO);
        couponMapper.insert(couponDO);
        if (CollectionUtils.isEmpty(couponDTO.getCouponSkuDoList())) {
            return "ok";
        }
        //从表新增
        for (CouponSkuDO couponSkuDO : couponDTO.getCouponSkuDoList()) {
            couponSkuDO.setCouponId(couponDO.getId());
            couponSkuDO.setGmtUpdate(now);
            couponSkuDO.setGmtCreate(now);
            couponSkuMapper.insert(couponSkuDO);
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCoupon(Long adminId, Long id) throws ServiceException {
        //查询是否被引用
        Wrapper<ActivityCounponDO> doEntityWrapper = new EntityWrapper<>();
        doEntityWrapper.eq("coupon_id", id);
        Integer count = activityCouponMapper.selectCount(doEntityWrapper);
        if (0 != count) {
            throw new AdminServiceException(new ServiceExceptionDefinition(50001, "该优惠券被引用中，无法删除"));
        }
        //删除主表
        EntityWrapper<CouponDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id", id);
        if (couponMapper.delete(entityWrapper) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        //删除优惠券从表
        EntityWrapper<CouponSkuDO> entitySkuWrapper = new EntityWrapper<>();
        entityWrapper.eq("coupon_id", id);
        couponSkuMapper.delete(entitySkuWrapper);

        //删除用户获取优惠券表
        EntityWrapper<UserCouponDO> wrapper = new EntityWrapper<>();
        wrapper.eq("coupon_id", id);
        userCouponMapper.delete(wrapper);
        return true;
    }

    @Override
    public CouponDTO queryCouponById(Long adminId, Long id) throws ServiceException {
        CouponDTO couponDTO = new CouponDTO();
        CouponDO couponDO = new CouponDO();
        couponDO.setId(id);
        couponDO = couponMapper.selectOne(couponDO);
        BeanUtils.copyProperties(couponDO, couponDTO);
        //查询从表
        EntityWrapper<CouponSkuDO> wrapper = new EntityWrapper<>();
        wrapper.eq("coupon_id", id);
        List<CouponSkuDO> doList = couponSkuMapper.selectList(wrapper);
        couponDTO.setCouponSkuDoList(doList);
        return couponDTO;
    }

    @Override
    public List<CouponDTO> queryCouponByIds(Long adminId, String idsJson) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        LinkedList<Long> idsSearch = new LinkedList<>(ids);
        Wrapper<CouponDO> wrapper = new EntityWrapper<>();
        wrapper.in("id", idsSearch);
        List<CouponDO> couponDos = couponMapper.selectList(wrapper);
        List<CouponDTO> couponDtoList = new ArrayList<>();
        CouponDTO couponDTO;
        for (CouponDO couponDo : couponDos) {
            couponDTO = new CouponDTO();
            BeanUtils.copyProperties(couponDo, couponDTO);
            couponDtoList.add(couponDTO);
        }
        return couponDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateCoupon(Long adminId, CouponDTO couponDTO) throws ServiceException {
        if (couponDTO.getId() == null) {
            throw new AdminServiceException(ExceptionDefinition.COUPON_UPDATE_NO_ID);
        }
        //主表更新
        CouponDO couponDO = new CouponDO();
        Date now = new Date();
        couponDTO.setGmtUpdate(now);
        BeanUtils.copyProperties(couponDTO, couponDO);
        couponMapper.updateById(couponDO);
        //从表更新
        List<Long> couponSkuId = new LinkedList<>();
        if (CollectionUtils.isEmpty(couponDTO.getCouponSkuDoList())) {
            //删除多余优惠券指定商品
            couponSkuMapper.delete(new EntityWrapper<CouponSkuDO>().eq("coupon_id", couponDTO.getId()));
            return "ok";
        }
        for (CouponSkuDO couponSku : couponDTO.getCouponSkuDoList()) {
            couponSku.setGmtUpdate(now);
            couponSku.setCouponId(couponDTO.getId());
            if (ObjectUtils.isEmpty(couponSku.getId())) {
                couponSku.setGmtCreate(now);
                couponSkuMapper.insert(couponSku);
            } else {
                couponSkuMapper.updateById(couponSku);
            }
            couponSkuId.add(couponSku.getId());
        }
        //删除多余优惠券指定商品
        couponSkuMapper.delete(new EntityWrapper<CouponSkuDO>().eq("coupon_id", couponDTO.getId()).notIn("id", couponSkuId));
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCouponStatus(Long adminId, Long id, Integer status) throws ServiceException {
        CouponDO couponDO = new CouponDO();
        couponDO.setId(id);
        couponDO.setStatus(status);
        couponDO.setGmtUpdate(new Date());
        return couponMapper.updateById(couponDO) > 0;
    }

    @Override
    public Page<CouponDTO> queryCouponByTitle(Long adminId, String title, Integer type, Integer status, Integer pageNo, Integer limit) throws ServiceException {
        EntityWrapper<CouponDO> wrapper = new EntityWrapper<>();
        Date now = new Date();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
            wrapper.like("id", title);
        }
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            if (status >= 0 && status < 2) {
                wrapper.eq("status", status);
                wrapper.andNew().gt("gmt_end", now).or().isNotNull("days");
            } else if (status < 0) {
                wrapper.lt("gmt_end", now);
            } else {
                throw new AdminServiceException(ExceptionDefinition.COUPON_CHECK_DATA_FAILED);
            }
        }
        Integer count = couponMapper.selectCount(wrapper);
        List<CouponDTO> couponList = couponMapper.getAdminCouponList(title, type, status, now, (pageNo - 1) * limit, limit);
        return new Page<>(couponList, pageNo, limit, count);
    }

    @Override
    public Page<UserCouponDTO> queryUserCouponByList(Long adminId, String name, String activityName, Integer activityType, Integer pageNo, Integer limit) throws ServiceException {
        List<UserCouponDTO> couponadminDtoList = userCouponMapper.selectUserCouponByPage(name, activityName, activityType, new RowBounds((pageNo - 1) * limit, limit));
        Integer count = userCouponMapper.selectUserCouponByCount(name, activityName, activityType);
        return new Page<>(couponadminDtoList, pageNo, limit, count);
    }
}
