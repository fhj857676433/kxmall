package com.kxmall.market.app.api.api.activity;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.biz.service.activity.ActivityBizService;
import com.kxmall.market.biz.service.activity.ShareCodeUtil;
import com.kxmall.market.core.exception.BizServiceException;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.core.exception.ServiceExceptionDefinition;
import com.kxmall.market.data.domain.UserCouponDO;
import com.kxmall.market.data.dto.InviteDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.enums.ActivityType;
import com.kxmall.market.data.mapper.InviteMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.mapper.UserCouponMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP端活动接口
 *
 * @author kaixin
 */
@Service
public class AppActivityServiceImpl implements AppActivityService {

    @Resource
    private InviteMapper inviteMapper;
    @Resource
    private UserCouponMapper userCouponMapper;
    @Resource
    private SpuMapper spuMapper;
    @Resource
    private ActivityBizService activityBizService;

    /**
     * 邀请抢购信息
     *
     * @param userId 用户id
     * @return 该用户邀请人数、抢购券情况
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> inviteBuyingInfo(Long userId) throws ServiceException {
        Map<String, Object> map = new HashMap<>(3);
        map.put("invite_num", inviteMapper.selectInviteByCount(userId));
        //查出优惠券数量
        Wrapper<UserCouponDO> couponDoWrapper = new EntityWrapper<>();
        couponDoWrapper.eq("user_id", userId);
        couponDoWrapper.eq("order_id", 0);
        couponDoWrapper.eq("activity_type", ActivityType.INVITE_NEW_PEOPLE.getCode());
        map.put("coupen_num", userCouponMapper.selectCount(couponDoWrapper));
        map.put("p", this.shareCode(userId));
        return map;
    }


    /**
     * 获取邀请列表
     *
     * @param userId 用户id
     * @return 邀请对象集合
     * @throws ServiceException
     */
    @Override
    public List<InviteDTO> invitationList(Long userId, Integer pageNo, Integer pageSize) throws ServiceException {
        List<InviteDTO> dtoList = inviteMapper.selectInviteByList(new RowBounds((pageNo - 1) * pageSize, pageSize), userId);
        dtoList.removeIf(item -> ObjectUtils.isEmpty(item.getUserDO()));
        dtoList.removeIf(item -> ObjectUtils.isEmpty(item.getUserDO().getPhone()));
        dtoList.forEach(inviteDTO -> inviteDTO.getUserDO().setPhone(inviteDTO.getUserDO().getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")));
        return dtoList;
    }

    /**
     * 获取活动商品列表
     *
     * @param storageId 仓库id
     * @param pageNo    页码
     * @param pageSize  分页数
     * @return 商品集合
     * @throws ServiceException
     */
    @Override
    public List<SpuDTO> activityGoodsList(Integer activityType, Long storageId, Integer pageNo, Integer pageSize) throws ServiceException {
        if (ObjectUtils.isEmpty(storageId)) {
            return spuMapper.selectActivityGoodsList(new RowBounds((pageNo - 1) * pageSize, pageSize), storageId, activityType);
        }
        return spuMapper.selectActivityGoodsListByStorageId(new RowBounds((pageNo - 1) * pageSize, pageSize), storageId, activityType);
    }

    /**
     * 获取当前用户的邀请码
     *
     * @param userId 用户
     * @return 邀请码
     * @throws ServiceException
     */
    @Override
    public String shareCode(Long userId) throws ServiceException {
        if (ObjectUtils.isEmpty(userId)) {
            throw new BizServiceException(new ServiceExceptionDefinition(10002, "用户信息为空，无法生成邀请码！"));
        }
        return ShareCodeUtil.toSerialCode(userId);
    }
}
