package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.domain.StockDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.LinkedList;
import java.util.List;

/**
 * 库存Mapper
 *
 * @author kaixin
 * @date 2020/2/18
 */
public interface StockMapper extends BaseMapper<StockDO> {

    /**
     * 出库商品数更新
     *
     * @param storageId
     * @param skuId
     * @param outStockNum
     * @return
     */
    Integer updateSock(@Param("storageId") Long storageId,@Param("skuId") Integer skuId,@Param("outStockNum") Long outStockNum);


    /**
     * 前置仓商品管理列表
     *
     * @param storageId
     * @param categoryId
     * @param name
     * @param status
     * @param offset
     * @param size
     * @return
     */
    List<StockDTO> selectStockListByStorageAndCategory(@Param("storageId") Long storageId, @Param("categoryId") Long categoryId, @Param("name") String name, @Param("status") Integer status, @Param("offset") Integer offset, @Param("size") Integer size, @Param("ids") List<Long> ids, @Param("idsNotIn") List<String> idsNotIn);

    /**
     * 前置仓商品管理列表数量统计
     *
     * @param storageId
     * @param categoryId
     * @param name
     * @param status
     * @return
     */
    Integer selectStockListByStorageAndCategoryCount(@Param("storageId") Long storageId, @Param("categoryId") Long categoryId, @Param("name") String name, @Param("status") Integer status,@Param("idsNotIn") List<String> idsNotIn);


    /**
     * 校验仓库商品管理是否重复
     *
     * @param storageId
     * @return
     */
    Long selectExistByStorageIdAndBarCode(@Param("storageId") Long storageId, @Param("barCode")String barCode);

    /**
     * 获取指定仓库预警列表
     * @param storageId 仓库id
     * @param categoryId   分类id
     * @param childrenIds  分类id集合
     * @param name    名称查询
     * @param type    库存量类型
     * @param minNum   最小值
     * @param maxNum   最大值
     * @param showType  是否显示预警
     * @return  预警列表集合
     */
    List<SpuDTO> warningListByStorage(@Param("rowBounds") RowBounds rowBounds, @Param("storageId") Long storageId, @Param("categoryId") Long categoryId, @Param("childrenIds") LinkedList<Long> childrenIds, @Param("name") String name, @Param("type") Integer type, @Param("minNum") Integer minNum, @Param("maxNum") Integer maxNum, @Param("showType") Boolean showType);


    /**
     * 获取指定仓库预警列表
     * @param storageId 仓库id
     * @param categoryId   分类id
     * @param childrenIds  分类id集合
     * @param name    名称查询
     * @param type    库存量类型
     * @param minNum   最小值
     * @param maxNum   最大值
     * @param showType  是否显示预警
     * @return  总个数
     */
    Integer warningListByStorageCount(@Param("storageId") Long storageId, @Param("categoryId") Long categoryId, @Param("childrenIds") LinkedList<Long> childrenIds, @Param("name") String name, @Param("type") Integer type, @Param("minNum") Integer minNum, @Param("maxNum") Integer maxNum, @Param("showType") Boolean showType);
}
