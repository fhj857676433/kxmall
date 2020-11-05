package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.domain.SpuDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2019/7/2.
 */
public interface SpuMapper extends BaseMapper<SpuDO> {

    /**
     * 仅可传入叶子类目
     * @param categoryId
     * @return
     */
    public List<SpuDO> getSpuTitleByCategoryId(Long categoryId);

    /**
     * 增加Spu累计销量
     * @param spuId
     * @param delta
     * @return
     */
    public Integer incSales(@Param("spuId") Long spuId,@Param("delta") Integer delta);

    public List<SpuDO> getSpuTitleAll();

    /**
     *根据主键查询商品
     * @param id
     * @return
     */
    SpuDO selectSpuById(@Param("id") Long id);

    /**
     * 查询全部商品ID
     * @param
     * @return
     */
    List<Long> selectAllId();


    /**
     * 查询商品对象分页
     *
     * @param rowBounds   分页对象
     * @param title       标题
     * @param categoryId  类目id
     * @param childrenIds 类目子id
     * @param storageId   仓库id
     * @param orderBy     排序
     * @param isAsc       升序降序
     * @return 商品对象集合
     */
    List<SpuDTO> selectPageByStorage(@Param("rowBounds") RowBounds rowBounds, @Param("title") String title, @Param("categoryId") Long categoryId, @Param("childrenIds") LinkedList<Long> childrenIds, @Param("storageId") Long storageId, @Param("orderBy") String orderBy, @Param("isAsc") Boolean isAsc);

    /**
     * 查询商品对象总数
     *
     * @param title       标题
     * @param categoryId  类目id
     * @param childrenIds 类目子id
     * @param storageId   仓库id
     * @param orderBy     排序
     * @param isAsc       升序降序
     * @return 商品对象集合
     */
    Integer selectCountByStorage(@Param("title") String title, @Param("categoryId") Long categoryId, @Param("childrenIds") LinkedList<Long> childrenIds, @Param("storageId") Long storageId, @Param("orderBy") String orderBy, @Param("isAsc") Boolean isAsc);

    /**
     * 获取活动商品列表
     * @param rowBounds 分页对象
     * @param storageId 仓库id
     * @param activityType 活动类型
     * @return 活动商品id
     */
    List<SpuDTO> selectActivityGoodsList(@Param("rowBounds") RowBounds rowBounds, @Param("storageId") Long storageId, @Param("activityType") Integer activityType);

    /**
     * 获取活动商品详情
     * @param activityId 活动id
     * @param couponId 优惠券id
     * @param spuId 商品id
     * @return 商品详情对象
     */
    SpuDO selectByActivityGood(@Param("activityId") Long activityId, @Param("couponId") Long couponId, @Param("spuId") Long spuId);

    /**
     * 获取活动商品详情 By 仓库
     * @param rowBounds 分页对象
     * @param storageId  仓库Id
     * @param activityType 活动类型
     * @return 活动商品集合
     */
    List<SpuDTO> selectActivityGoodsListByStorageId(@Param("rowBounds") RowBounds rowBounds, @Param("storageId") Long storageId, @Param("activityType") Integer activityType);
}
